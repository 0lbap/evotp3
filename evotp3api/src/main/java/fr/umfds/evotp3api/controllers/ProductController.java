package fr.umfds.evotp3api.controllers;

import fr.umfds.evotp3api.models.Product;
import fr.umfds.evotp3api.repositories.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger("USER_PROFILING_LOGGER");

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> list() {
        logger.info("User is retrieving data from the database");
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Product get(@PathVariable Long id) {
        logger.info("User is retrieving data from the database");
        if (productRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found");
        }
        return productRepository.getReferenceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody final Product product) {
        logger.info("User is writing and reading data to the database");
        return productRepository.saveAndFlush(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        logger.info("User is writing data to the database");
        if (productRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        logger.info("User is writing and reading data to the database");
        if (productRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " not found");
        }
        Product existingRating = productRepository.getReferenceById(id);
        BeanUtils.copyProperties(product, existingRating, "id");
        return productRepository.saveAndFlush(existingRating);
    }
}
