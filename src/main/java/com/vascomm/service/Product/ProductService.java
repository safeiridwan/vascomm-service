package com.vascomm.service.Product;

import com.vascomm.controller.product.request.CreateProductRequest;
import com.vascomm.controller.product.request.EditProductRequest;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.PageInput;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<ResponseAPI> createProduct(CreateProductRequest request);
    ResponseEntity<ResponseAPI> editProduct(String productId, EditProductRequest request);
    ResponseEntity<ResponseAPI> detailProduct(String productId);
    ResponseEntity<ResponseAPI> listProduct(PageInput pageInput);
    ResponseEntity<ResponseAPI> deleteProduct(String productId);
}
