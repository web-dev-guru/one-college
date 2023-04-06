package org.college.exam.controllers;

import lombok.extern.slf4j.Slf4j;
import org.college.management.controllers.domain.ExamDTO;
import org.college.management.controllers.domain.QuestionDTO;
import org.college.exam.service.QuestionService;
import org.college.management.controllers.domain.QuestionOptionDTO;
import org.college.utils.AjaxResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @PostMapping("/api/question")
    public ResponseEntity<?> saveQuestionsViaAjax(@Valid @RequestBody QuestionDTO question , Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);

        }
        QuestionDTO returnQuestion = questionService.save(question);

        if (returnQuestion == null) {
            result.setMsg("no question found!");
        } else {
            result.setMsg("success");
        }
        result.setQuestionResult(returnQuestion);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/chapterexam")
    public List<ExamDTO> getChapterExamViaAjax(){
        String render="abc";
        List<ExamDTO> exams = new ArrayList<ExamDTO>();
        QuestionDTO q1 = QuestionDTO.builder().build();
        q1.setQuestionType("SN");
        q1.setQuestionTitle("what is it");
        QuestionOptionDTO qq1= QuestionOptionDTO.builder().build();
        qq1.setQuestionMsg("qq1");
        qq1.setIsAnswer(false);
        QuestionOptionDTO qq2= QuestionOptionDTO.builder().build();
        qq2.setQuestionMsg("qq2");
        qq2.setIsAnswer(false);
        QuestionOptionDTO qq3= QuestionOptionDTO.builder().build();
        qq3.setQuestionMsg("qq3");
        qq3.setIsAnswer(false);
        QuestionOptionDTO qq4= QuestionOptionDTO.builder().build();
        qq4.setQuestionMsg("qq4");
        qq4.setIsAnswer(true);
        List<QuestionOptionDTO> ql1 = new ArrayList<QuestionOptionDTO>();
        ql1.add(qq1);
        ql1.add(qq2);
        ql1.add(qq3);
        ql1.add(qq4);
        q1.setQuestionOptionDTOs(ql1);
        ExamDTO e1 = ExamDTO.builder().questionTitie(render).questionType("single").build();
        e1.setQuestion(q1);
        exams.add(e1);
        return exams;
    }
}
