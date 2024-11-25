package fr.umfds.evotp3api.spoon;
import spoon.Launcher;
public class SpoonLauncher {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.getEnvironment().setAutoImports(true);
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/main/java");
        launcher.setSourceOutputDirectory("generated-src");
        launcher.addProcessor(new LoggingInjector());
        launcher.run();
    }
}
