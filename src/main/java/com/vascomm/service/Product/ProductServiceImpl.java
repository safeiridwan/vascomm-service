package com.vascomm.service.Product;

import com.vascomm.controller.product.request.CreateProductRequest;
import com.vascomm.controller.product.request.EditProductRequest;
import com.vascomm.controller.product.response.DetailProductResponse;
import com.vascomm.entity.Product;
import com.vascomm.entity.User;
import com.vascomm.repository.ProductRepository;
import com.vascomm.repository.UserRepository;
import com.vascomm.response.ResponseAPI;
import com.vascomm.util.AuthenticationFacade;
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

import static com.vascomm.util.constant.Constant.USER_ROLE;
import static com.vascomm.util.constant.ResponseMessage.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    @Override
    public ResponseEntity<ResponseAPI> createProduct(CreateProductRequest request) {
        String userId = authenticationFacade.getUserId();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(403, UNAUTHORIZED_ERROR, null, null), HttpStatus.UNAUTHORIZED);
        }

        if (user.getRole().equals(USER_ROLE)) {
            return new ResponseEntity<>(new ResponseAPI(403, REQUEST_FORBIDDEN_ERROR, null, null), HttpStatus.FORBIDDEN);
        }

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
        String userId = authenticationFacade.getUserId();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(403, UNAUTHORIZED_ERROR, null, null), HttpStatus.UNAUTHORIZED);
        }

        if (user.getRole().equals(USER_ROLE)) {
            return new ResponseEntity<>(new ResponseAPI(403, REQUEST_FORBIDDEN_ERROR, null, null), HttpStatus.FORBIDDEN);
        }

        Product product = productRepository.FindByIdAndProductStatus(productId, Boolean.TRUE);
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
        Product product = productRepository.FindByIdAndProductStatus(productId, Boolean.TRUE);
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
        String userId = authenticationFacade.getUserId();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity<>(new ResponseAPI(403, UNAUTHORIZED_ERROR, null, null), HttpStatus.UNAUTHORIZED);
        }

        if (user.getRole().equals(USER_ROLE)) {
            return new ResponseEntity<>(new ResponseAPI(403, REQUEST_FORBIDDEN_ERROR, null, null), HttpStatus.FORBIDDEN);
        }

        Product product = productRepository.FindByIdAndProductStatus(productId, Boolean.TRUE);
        if (product == null) {
            return new ResponseEntity<>(new ResponseAPI(400, "Product not found", null, null), HttpStatus.BAD_REQUEST);
        }

        product.setProductStatus(Boolean.FALSE);
        productRepository.save(product);
        return new ResponseEntity<>(new ResponseAPI(200, OK, null, null), HttpStatus.OK);
    }
}
