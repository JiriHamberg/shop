package fi.hartikat.shopproductservice.controller;

import fi.hartikat.shopproductservice.model.Product;
import fi.hartikat.shopproductservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.data.rest.converters.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PageableAsQueryParam
    @GetMapping
    @Operation
    public ResponseEntity<Page<Product>> list(@Parameter(hidden = true) Pageable pageRequest) {
        var products = productService.list(pageRequest);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @Operation
    public ResponseEntity<Product> get(@PathVariable UUID productId) {
        var product = productService.get(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    @Operation
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        var created = productService.create(product);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @Operation
    public ResponseEntity<Product> update(@Valid @RequestBody Product product, @PathVariable UUID productId) {
        var created = productService.update(productId, product);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }


}
