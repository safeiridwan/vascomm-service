package com.vascomm.util.oauth2;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleUtil {
    private static final Logger logger = LoggerFactory.getLogger(GoogleUtil.class);

    @Value("${app.auth.google.clientId}")
    private String googleClientId;

    public Oauth2GoogleResponse getGooglePayload(String idTokenString) {
        List<String> audiences = new ArrayList<>(2);
        audiences.add(googleClientId);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(audiences)
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Oauth2GoogleResponse res = new Oauth2GoogleResponse();
                res.setFirstName((String) idToken.getPayload().get("given_name"));
                res.setLastName((String) idToken.getPayload().get("family_name"));
                res.setEmail(idToken.getPayload().getEmail());
                res.setProfilePictureUrl((String) idToken.getPayload().get("picture"));
                return res;
            } else {
                return null;
            }
        } catch (GeneralSecurityException | IOException e) {
            logger.error("An error occurred: ", e);
            return null;
        }
    }
}
