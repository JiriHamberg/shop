package fi.hartikat.shopproductservice.repository;

import fi.hartikat.shopproductservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ProductRepository extends JpaRepository<Product, UUID> {
}
