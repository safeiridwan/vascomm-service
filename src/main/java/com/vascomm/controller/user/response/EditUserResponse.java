package com.vascomm.controller.user.response;

import com.vascomm.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    public EditUserResponse() {
    }

    public EditUserResponse generate(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        return this;
    }
}
