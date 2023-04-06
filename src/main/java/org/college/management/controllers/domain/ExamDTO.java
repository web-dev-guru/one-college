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
public class ExamDTO {
    @NotBlank(message = "questionTitle can't empty!")
    String questionTitie;
    @NotBlank(message = "questionType can't empty!")
    String questionType;
    @NotBlank(message = "question can't empty!")
    QuestionDTO question;
}
