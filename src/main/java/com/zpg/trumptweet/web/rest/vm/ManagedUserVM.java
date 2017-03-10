package com.zpg.trumptweet.web.rest.vm;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.validation.constraints.Size;

import com.zpg.trumptweet.service.dto.UserDTO;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    private String token;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(Long id, String login, String password, String firstName, String lastName,
                         String email, boolean activated, String imageUrl, String langKey,
                         String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate,
                        Set<String> authorities, BigDecimal monthlyLimit, BigDecimal tweetLimit, BigDecimal transferThreshold, String token) {

        super(id, login, firstName, lastName, email, activated,  imageUrl, langKey,
            createdBy, createdDate, lastModifiedBy, lastModifiedDate,  authorities, monthlyLimit, tweetLimit, transferThreshold);

        this.password = password;
        this.token = token;
    }

    
    public String getPassword() {
        return password;
    }
    
    public String getToken() {
    	return token;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
