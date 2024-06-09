package com.vascomm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import static com.vascomm.util.constant.Constant.USER_ROLE;

@Data
@Entity
@Table(	name = "product")
public class Product {
    @Id
    private String id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Float price;
    @Column(name = "product_status")
    private Boolean productStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private String createdBy;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
