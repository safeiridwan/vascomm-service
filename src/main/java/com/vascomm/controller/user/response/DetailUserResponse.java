package com.vascomm.controller.user.response;

import com.vascomm.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailUserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String userStatus;

    public DetailUserResponse() {
    }

    public void generate(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.userStatus = "active";
        if (!user.getUserStatus()) {
            this.userStatus = "inactive";
        }
    }
}
