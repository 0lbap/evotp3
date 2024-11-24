package fr.umfds.evotp3api.spoon;

import fr.umfds.evotp3api.models.User;
import org.slf4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.Arrays;
import java.util.List;

public class LoggingInjector extends AbstractProcessor<CtClass<?>> {

    @Override
    public boolean isToBeProcessed(CtClass<?> ctClass) {
        return ctClass.getSimpleName().equals("ProductController");
    }

    @Override
    public void process(CtClass<?> ctClass) {
        addImports(ctClass);
        addLoggerField(ctClass);
        injectLoggingWithThreadContext(ctClass);
    }

    private void addImports(CtClass<?> ctClass) {
        // Add import for ThreadContext
        CtImport threadContextImport = ctClass.getFactory().Core().createImport();
        threadContextImport.setReference(ctClass.getFactory().createReference("org.apache.logging.log4j.ThreadContext"));
        ctClass.getFactory().CompilationUnit().getOrCreate(ctClass).setImports(List.of(threadContextImport));

        // Add import for LoggerFactory
        CtImport loggerFactoryImport = ctClass.getFactory().Core().createImport();
        threadContextImport.setReference(ctClass.getFactory().createReference("org.slf4j.LoggerFactory"));
        ctClass.getFactory().CompilationUnit().getOrCreate(ctClass).getImports().add(loggerFactoryImport);
    }

    private void addLoggerField(CtClass<?> ctClass) {
        // Check if the Logger field already exists
        if (ctClass.getFields().stream().anyMatch(field -> field.getSimpleName().equals("logger"))) {
            return;
        }

        // Create the Logger field
        CtField<?> loggerField = getFactory().createField();
        loggerField.setSimpleName("logger");

        // Set field type to Logger
        CtTypeReference<Logger> loggerType = getFactory().createCtTypeReference(Logger.class);
        loggerField.setType(loggerType);

        // Add modifiers (private final)
        loggerField.addModifier(ModifierKind.PRIVATE);
        loggerField.addModifier(ModifierKind.FINAL);

        // Initialize Logger field with LoggerFactory
        loggerField.setAssignment(getFactory().createCodeSnippetExpression(
                "LoggerFactory.getLogger(\"fr.umfds.evotp3api.profiling\")"));

        // Add the field to the class
        ctClass.addFieldAtTop(loggerField);
    }

    private void injectLoggingWithThreadContext(CtClass<?> ctClass) {
        for (CtMethod<?> method : ctClass.getMethods()) {
            // Ensure the parameter @AuthenticationPrincipal User userDetails exists
            ensureAuthenticationPrincipalParameter(method);

            String methodName = method.getSimpleName();

            // Map method names to action and optional ThreadContext keys
            String message = null;
            String action = null;
            String additionalThreadContext = null;

            switch (methodName) {
                case "list" -> {
                    message = "User is listing all products";
                    action = "list_products";
                }
                case "get" -> {
                    message = "User is viewing a product";
                    action = "get_product";
                    additionalThreadContext = "ThreadContext.put(\"product_price\", String.valueOf(product.get().getPrice()))";
                }
                case "create" -> {
                    message = "User is creating a product";
                    action = "create_product";
                }
                case "delete" -> {
                    message = "User is deleting a product";
                    action = "delete_product";
                }
                case "update" -> {
                    message = "User is updating a product";
                    action = "update_product";
                }
            }

            if (action != null) {
                // Create ThreadContext setup snippet
                StringBuilder threadContextSnippet = new StringBuilder()
                        .append("ThreadContext.put(\"user_id\", String.valueOf(userDetails.getId()));\n")
                        .append("ThreadContext.put(\"action\", \"")
                        .append(action)
                        .append("\")");

                if (additionalThreadContext != null) {
                    threadContextSnippet.append(";\n").append(additionalThreadContext);
                }

                // Create logging statement
                String logStatement = "logger.info(\"" + message + "\")";

                // Create ThreadContext clear snippet
                String threadContextClear = "ThreadContext.clearAll()";

                // Locate where to insert
                if ("get".equals(methodName)) {
                    // For the get method, insert at the end of the method, just before the return statement
                    String finalMessage = message;
                    method.getBody().getElements(e -> e instanceof spoon.reflect.code.CtReturn<?>)
                            .forEach(returnStmt -> {
                                // Cast to CtReturn
                                spoon.reflect.code.CtReturn<?> ctReturn = (spoon.reflect.code.CtReturn<?>) returnStmt;

                                // Inject ThreadContext snippet
                                CtCodeSnippetStatement threadContextSetup = getFactory()
                                        .createCodeSnippetStatement(threadContextSnippet.toString());
                                ctReturn.insertBefore(threadContextSetup);

                                // Inject logger.info()
                                CtCodeSnippetStatement log = getFactory().createCodeSnippetStatement("logger.info(\"" + finalMessage + "\")");
                                ctReturn.insertBefore(log);

                                // Inject ThreadContext.clearAll()
                                CtCodeSnippetStatement clearContext = getFactory().createCodeSnippetStatement("ThreadContext.clearAll()");
                                ctReturn.insertBefore(clearContext);
                            });
                } else {
                    // For other methods, insert at the beginning of the method
                    CtCodeSnippetStatement threadContextSetup = getFactory()
                            .createCodeSnippetStatement(threadContextSnippet.toString());
                    CtCodeSnippetStatement log = getFactory().createCodeSnippetStatement(logStatement);
                    CtCodeSnippetStatement clearContext = getFactory().createCodeSnippetStatement(threadContextClear);

                    method.getBody().insertBegin(clearContext);
                    method.getBody().insertBegin(log);
                    method.getBody().insertBegin(threadContextSetup);
                }
            }
        }
    }

    private void ensureAuthenticationPrincipalParameter(CtMethod<?> method) {
        // Check if userDetails is already a parameter
        boolean hasUserDetails = method.getParameters().stream()
                .anyMatch(param -> "userDetails".equals(param.getSimpleName()));

        if (!hasUserDetails) {
            // Create the parameter
            CtParameter<?> userDetailsParam = getFactory().createParameter();

            // Set the parameter type
            CtTypeReference<?> userType = getFactory().createCtTypeReference(User.class);
            userDetailsParam.setType(userType);

            // Set the parameter name
            userDetailsParam.setSimpleName("userDetails");

            // Add the @AuthenticationPrincipal annotation
            CtAnnotation<?> authenticationPrincipalAnnotation = getFactory()
                    .createAnnotation(getFactory().createCtTypeReference(AuthenticationPrincipal.class));
            userDetailsParam.addAnnotation(authenticationPrincipalAnnotation);

            // Add the parameter to the method
            method.addParameter(userDetailsParam);
        }
    }

}
