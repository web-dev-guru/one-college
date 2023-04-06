package org.college.exam.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionOption {
    @NotNull
    String questionMsg;
    @NotNull
    Boolean isAnswer;
}
