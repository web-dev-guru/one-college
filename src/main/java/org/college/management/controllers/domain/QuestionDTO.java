package org.college.management.controllers.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionDTO {

    String questionId;
    @NotBlank(message = "questionTitle can't empty!")
    String questionTitle;

    @NotBlank(message = "chapter can't empty!")
    String chapter;

    @NotBlank(message = "groupId can't empty!")
    String groupId;

    @NotBlank(message = "difficulty can't empty!")
    String difficulty;

    @NotBlank(message = "questionType can't empty!")
    String questionType;

    List<QuestionOptionDTO> questionOptionDTOs;

}
