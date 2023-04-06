package org.college.management.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.college.management.controllers.domain.UserDTO;
import org.college.utils.ApplicationKey;
import org.college.utils.ApplicationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class CollegeController {
    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "logout";
    }

    @GetMapping("/main")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isEmpty(sessionUser)){
            return new ModelAndView("redirect:/logout");
        }
        UserDTO user = UserDTO.builder().email(sessionUser.getEmail()).role(sessionUser.getRole()).displayLoginDate(ApplicationUtils.getCurrentDate()).build();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("main");
        return modelAndView;
    }
    @GetMapping("/question")
    public String question() {

        return "question";
    }

    @GetMapping("/exam")
    public String exam() {

        return "exam";
    }

    @GetMapping("/mediaplayer")
    public String mediaplayer() {

        return "mediaplayer";
    }


    @GetMapping("/userhome")
    public ModelAndView userhome(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isEmpty(sessionUser)){
            return new ModelAndView("redirect:/logout");
        }
        modelAndView.setViewName("userhome");
        UserDTO user = UserDTO.builder().email(sessionUser.getEmail()).role(sessionUser.getRole()).displayLoginDate(ApplicationUtils.getCurrentDate()).build();
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @GetMapping("/adduser")
    public ModelAndView adduser(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isEmpty(sessionUser)){
            return new ModelAndView("redirect:/logout");
        }
        modelAndView.setViewName("adduser");
        UserDTO user = UserDTO.builder().email(sessionUser.getEmail()).role(sessionUser.getRole()).displayLoginDate(ApplicationUtils.getCurrentDate()).build();
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @GetMapping("/updateuser")
    public ModelAndView searchuser(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession();
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isEmpty(sessionUser)){
            return new ModelAndView("redirect:/logout");
        }
        modelAndView.setViewName("updateuser");
        UserDTO user = UserDTO.builder().email(sessionUser.getEmail()).role(sessionUser.getRole()).displayLoginDate(ApplicationUtils.getCurrentDate()).build();
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
