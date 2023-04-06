package org.college.exam.service;


import org.college.management.controllers.domain.QuestionDTO;
import org.college.exam.dao.QuestionRepository;
import org.college.exam.dao.domain.Question;
import org.college.exam.dao.domain.QuestionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public QuestionDTO save(QuestionDTO question){
        Question q = convertQuestionDTO(question);
        Question returnQ= questionRepository.save(q);
       return  convertQuestion(returnQ);
    }

    private Question convertQuestionDTO(QuestionDTO question){
        List<QuestionOption> questionOptions = new ArrayList<QuestionOption>();
        question.getQuestionOptionDTOs().stream().forEach(item->{
            QuestionOption option = QuestionOption.builder().questionMsg(item.getQuestionMsg()).isAnswer(item.getIsAnswer()).build();
            questionOptions.add(option);
        });
        Question q = Question.builder().questionTitle(question.getQuestionTitle())
                                .chapter(question.getChapter())
                                .difficulty(question.getDifficulty())
                                .groupId(question.getGroupId())
                                .questionType(question.getQuestionType())
                                .questionOptions(questionOptions)
                                .build();
        q.setLastUpdatedTime(LocalDateTime.now());
        q.setInsertTime(LocalDateTime.now());
        q.setOperator("abc");
        return q;

    }

    private QuestionDTO convertQuestion(Question question){
        QuestionDTO qdto = QuestionDTO.builder()
                .questionTitle(question.getQuestionTitle())
                .chapter(question.getChapter())
                .questionId(question.getQuestionId())
                .build();
        return qdto;

    }
}
