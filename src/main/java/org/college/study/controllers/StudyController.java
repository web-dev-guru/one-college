package org.college.study.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StudyController {



    @GetMapping("/api/study")
    public String getResourcesViaAjax(){
        String render="mediaplayer";
        return render;
    }
}
