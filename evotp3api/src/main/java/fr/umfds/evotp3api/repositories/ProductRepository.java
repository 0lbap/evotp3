package fr.umfds.evotp3api.repositories;

import fr.umfds.evotp3api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
