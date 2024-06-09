package com.vascomm.controller.product.response;

import com.vascomm.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailProductResponse {
    private String id;
    private String productName;
    private Float price;

    public DetailProductResponse() {

    }

    public void generate(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
    }
}
