package org.college.exam.dao.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.college.management.controllers.domain.Audit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "question")
public class Question extends Audit {
    @Id
    @JsonAlias("_id")
    @NotNull
    private String questionId;
    @NotNull
    String questionTitle;
    @NotNull
    String chapter;
    @NotNull
    String groupId;
    @NotNull
    String difficulty;
    @NotNull
    String questionType;
    @NotNull
    List<QuestionOption> questionOptions;
}
