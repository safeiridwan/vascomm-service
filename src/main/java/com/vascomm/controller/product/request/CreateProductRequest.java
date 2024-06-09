package com.vascomm.controller.product.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    @NotNull(message = "Product name cannot be blank or null.")
    private String productName;
    @NotNull(message = "Price cannot be blank or null.")
    private Float price;
}
