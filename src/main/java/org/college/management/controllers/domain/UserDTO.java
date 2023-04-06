package org.college.management.controllers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    String firstname;
    String userId;
    String lastname;
    String password;
    String email;
    String status;
    String startDate;
    String endDate;

    String role;

    String operator;

    String displayLoginDate;
    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                "lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", email='" + status + '\'' +
                ", email='" + startDate + '\'' +
                ", email='" + endDate + '\'' +
                '}';
    }
}
