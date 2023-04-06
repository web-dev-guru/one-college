package org.college.management.dao.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.college.management.controllers.domain.Audit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "user")
public class User extends Audit {
    @Id
    @JsonAlias("_id")
    @NotNull
    private String userId;
    @NotNull
    String email;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String role;
    @NotNull
    String startDate;
    @NotNull
    String endDate;
    @NotNull
    String password;
    @NotNull
    String status;

}
