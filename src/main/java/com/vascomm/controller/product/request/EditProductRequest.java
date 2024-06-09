package com.vascomm.controller.product.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProductRequest {
    private String productName;
    private Float price;
}
