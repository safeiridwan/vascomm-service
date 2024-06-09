package com.vascomm.controller.product;

import com.vascomm.controller.product.request.CreateProductRequest;
import com.vascomm.controller.product.request.EditProductRequest;
import com.vascomm.response.ResponseAPI;
import com.vascomm.service.Product.ProductService;
import com.vascomm.util.PageInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("product")
public class ProductController {
    private final ProductService service;

    @PostMapping()
    public ResponseEntity<ResponseAPI> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return service.createProduct(request);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<ResponseAPI> editProduct(
            @PathVariable(name = "product_id") String productId,
            @Valid @RequestBody EditProductRequest request) {
        return service.editProduct(productId, request);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ResponseAPI> detailProduct(@PathVariable(name = "product_id") String productId) {
        return service.detailProduct(productId);
    }

    @GetMapping()
    public ResponseEntity<ResponseAPI> listProduct(PageInput pageInput) {
        return service.listProduct(pageInput);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<ResponseAPI> deleteProduct(@PathVariable(name = "product_id") String productId) {
        return service.deleteProduct(productId);
    }
}
