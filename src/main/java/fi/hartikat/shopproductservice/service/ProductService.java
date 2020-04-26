package fi.hartikat.shopproductservice.service;

import fi.hartikat.shopproductservice.model.Product;
import fi.hartikat.shopproductservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Page<Product> list(Pageable pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    public Product get(UUID uuid) {
        return productRepository
                .findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(UUID id, Product product) {
        var exists = productRepository.existsById(id);

        if (exists) {
            product.setUuid(id);
            return productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}
