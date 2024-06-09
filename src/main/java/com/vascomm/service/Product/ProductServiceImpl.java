package com.vascomm.service.Product;

import com.vascomm.controller.product.request.CreateProductRequest;
import com.vascomm.controller.product.request.EditProductRequest;
import com.vascomm.controller.product.response.DetailProductResponse;
import com.vascomm.entity.Product;
import com.vascomm.repository.ProductRepository;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.PageInput;
import com.vascomm.util.PageRequestUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.vascomm.util.constant.ResponseMessage.OK;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public ResponseEntity<ResponseAPI> createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setProductStatus(Boolean.TRUE);

        productRepository.save(product);
        return new ResponseEntity<>(new ResponseAPI(200, OK, null, null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> editProduct(String productId, EditProductRequest request) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "Product not found", null, null), HttpStatus.BAD_REQUEST);
        }

        if (request.getProductName() != null && !request.getProductName().isEmpty()) {
            product.setProductName(request.getProductName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        productRepository.save(product);

        DetailProductResponse res = new DetailProductResponse();
        res.generate(product);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> detailProduct(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "Product not found", null, null), HttpStatus.BAD_REQUEST);
        }

        DetailProductResponse res = new DetailProductResponse();
        res.generate(product);

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> listProduct(PageInput pageInput) {
        Pageable pageable = PageRequestUtil.of(pageInput);
        Specification<Product> spec = (root, query, builder) -> {
            String searchLike = "%" + pageInput.getSearch() + "%";
            Predicate namePredicate = builder.like(root.get("productName"), searchLike);
            Predicate statusPredicate = builder.equal(root.get("productStatus"), Boolean.TRUE);
            return builder.and(namePredicate, statusPredicate);
        };

        Page<Product> resultQuery = productRepository.findAll(spec, pageable);
        List<DetailProductResponse> list = resultQuery
                .stream()
                .map(product -> {
                    DetailProductResponse dto = new DetailProductResponse();
                    dto.generate(product);
                    return dto;
                })
                .toList();

        Page<DetailProductResponse> res = new PageImpl<>(list, pageable, resultQuery.getTotalElements());

        return new ResponseEntity<>(new ResponseAPI(200, OK, null, res), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseAPI> deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "Product not found", null, null), HttpStatus.BAD_REQUEST);
        }

        productRepository.delete(product);
        return new ResponseEntity<>(new ResponseAPI(200, OK, null, null), HttpStatus.OK);
    }
}
