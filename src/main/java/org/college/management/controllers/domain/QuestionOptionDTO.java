package org.college.management.controllers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionOptionDTO {
    @NotBlank(message = "questionMsg can't empty!")
    String questionMsg;
    @NotBlank(message = "isAnswer can't empty!")
    Boolean isAnswer;
}
