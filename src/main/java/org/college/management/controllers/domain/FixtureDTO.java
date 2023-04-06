package org.college.management.controllers.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FixtureDTO {
    @JsonProperty
    String id;
    @JsonProperty
    String time;
    @JsonProperty
    Boolean enabled;
    @JsonProperty
    Long name;
}
