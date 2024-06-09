package com.vascomm.util.oauth2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oauth2GoogleResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePictureUrl;
}
