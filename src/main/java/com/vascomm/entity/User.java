package com.vascomm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(	name = "user")
public class User {
    @Id
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "user_status")
    private Boolean userStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private String createdBy;

    @PrePersist
    public void prePersist() {
        if (this.role == null || this.role.isEmpty()) {
            this.role = "user";
        }

        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Date();
    }
}
