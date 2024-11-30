package fr.umfds.evotp3api.controllers;
import fr.umfds.evotp3api.models.Product;
import fr.umfds.evotp3api.models.User;
import fr.umfds.evotp3api.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger("fr.umfds.evotp3api.profiling");

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> list(@AuthenticationPrincipal
    User userDetails) {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Product get(@PathVariable
    Long id, @AuthenticationPrincipal
    User userDetails) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ("Product with ID " + id) + " not found");
        }
        ThreadContext.put("user_id", String.valueOf(userDetails.getId()));
        ThreadContext.put("action", "get_product");
        ThreadContext.put("product_price", String.valueOf(product.get().getPrice()));
        logger.info("User is viewing a product");
        ThreadContext.clearAll();
        return productRepository.getReferenceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody
    final Product product, @AuthenticationPrincipal
    User userDetails) {
        ThreadContext.put("user_id", String.valueOf(userDetails.getId()));
        ThreadContext.put("action", "create_product");
        logger.info("User is creating a product");
        ThreadContext.clearAll();
        return productRepository.saveAndFlush(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable
    Long id, @AuthenticationPrincipal
    User userDetails) {
        ThreadContext.put("user_id", String.valueOf(userDetails.getId()));
        ThreadContext.put("action", "delete_product");
        logger.info("User is deleting a product");
        ThreadContext.clearAll();
        if (productRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ("Product with ID " + id) + " not found");
        }
        productRepository.deleteById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable
    Long id, @RequestBody
    Product product, @AuthenticationPrincipal
    User userDetails) {
        ThreadContext.put("user_id", String.valueOf(userDetails.getId()));
        ThreadContext.put("action", "update_product");
        logger.info("User is updating a product");
        ThreadContext.clearAll();
        productRepository.deleteById(id);
        if (productRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ("Product with ID " + id) + " not found");
        }
        Product existingProduct = productRepository.getReferenceById(id);
        BeanUtils.copyProperties(product, existingProduct, "id");
        return productRepository.saveAndFlush(existingProduct);
    }
}
