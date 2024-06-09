package com.vascomm.configuration;

import com.vascomm.entity.Product;
import com.vascomm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SeedData implements CommandLineRunner {
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {

        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) {
            Product product = new Product();
            product.setId(UUID.randomUUID().toString());
            product.setProductName("HP Samsung");
            product.setPrice(2000000.0F);
            product.setProductStatus(Boolean.TRUE);

            Product product2 = new Product();
            product2.setId(UUID.randomUUID().toString());
            product2.setProductName("HP Vivo");
            product2.setPrice(3000000.0F);
            product2.setProductStatus(Boolean.TRUE);

            Product product3 = new Product();
            product3.setId(UUID.randomUUID().toString());
            product3.setProductName("HP Redme Notes");
            product3.setPrice(4000000.0F);
            product3.setProductStatus(Boolean.TRUE);

            productRepository.save(product);
            productRepository.save(product2);
            productRepository.save(product3);
        }

    }

}
