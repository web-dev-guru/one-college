package org.college.management.controllers;

import org.apache.commons.lang3.ObjectUtils;
import org.college.management.controllers.domain.FixtureDTO;
import org.college.management.controllers.domain.UserDTO;
import org.college.management.service.KafkaProducerService;
import org.college.management.service.UserService;
import org.college.utils.AjaxResponseBody;
import org.college.management.controllers.domain.LoginForm;
import org.college.utils.ApplicationKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class UserController {
   /* @Autowired
    UserService userService;*/
   private Logger logger = LoggerFactory.getLogger(this.getClass());
   @Value("${college.default.user}")
   private String superUser;
    @Value("${college.default.password}")
   private String password;
    @Autowired
    UserService userService;

    @Autowired
    private KafkaProducerService kafkaProducer;
    @GetMapping(value = "/user")
    public String hello() {
        log.info("Hello One College");
        return "Hello One College";
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody LoginForm loginForm, Errors errors,HttpServletRequest request) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            result.setMsg("Please enter the userId and password");
            return ResponseEntity.badRequest().body(result);
        }
        HttpSession session = request.getSession();
        log.info("Welcome user login {}",loginForm.getUserId());
        List<UserDTO> users = userService.findUserByEmail(loginForm.getUserId());
        Boolean isValidUser= false;
        Optional<UserDTO> u =Optional.empty();
        if(!users.isEmpty()){
             u = users.stream().filter(ur->ur.getPassword().equals(loginForm.getPassword())).findAny();
            isValidUser=u.isPresent();
        }
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isNotEmpty(sessionUser) && loginForm.getPassword().equals(sessionUser.getPassword())){
            result.setMsg("success");
            result.setUserResult(sessionUser);
            return ResponseEntity.ok(result);
        }
        if(superUser.equalsIgnoreCase(loginForm.getUserId()) && password.equalsIgnoreCase(loginForm.getPassword())){
            UserDTO user = UserDTO.builder().build();
            user.setEmail(loginForm.getUserId());
            user.setEmail("college.admin@gmail.com");
            user.setUserId("admin");
            user.setRole("admin");
            result.setMsg("success");
            result.setUserResult(user);
            session.setAttribute(ApplicationKey.SESSION_USER_KEY,user);
            return ResponseEntity.ok(result);
        }else if(isValidUser){

            session.setAttribute(ApplicationKey.SESSION_USER_KEY,u.get());
            result.setMsg("success");
            result.setUserResult(u.get());
            return ResponseEntity.ok(result);
        }
        else{
            result.setMsg(loginForm.getUserId()+ " was not found or password is not correct! ");
            return ResponseEntity.badRequest().body(result);

        }
    }

    @PostMapping("/api/adduser")
    public ResponseEntity<?> saveUsersViaAjax(@Valid @RequestBody UserDTO user , Errors errors,HttpServletRequest request) {

        AjaxResponseBody result = new AjaxResponseBody();
        HttpSession session = request.getSession();
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isEmpty(sessionUser)){
             return ResponseEntity.badRequest().body(result);
        }
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);

        }

        List<UserDTO> users = userService.findUserByEmail(user.getEmail());

        if( !CollectionUtils.isEmpty(users)){
            result.setMsg("user is exist!");
            return ResponseEntity.ok(result);
        }
        user.setOperator(sessionUser.getUserId());
        UserDTO returnUser = userService.save(user);

        if (returnUser == null) {
            result.setMsg("no user found!");
        } else {
            result.setMsg("success");
        }
        result.setUserResult(returnUser);

        return ResponseEntity.ok(result);
    }


    @PostMapping("/api/searchuser")
    public ResponseEntity<?> searchUsersViaAjax(@Valid @RequestBody UserDTO user , Errors errors,HttpServletRequest request) {

        AjaxResponseBody result = new AjaxResponseBody();
        HttpSession session = request.getSession();
        UserDTO sessionUser =(UserDTO)session.getAttribute(ApplicationKey.SESSION_USER_KEY);
        if(ObjectUtils.isEmpty(sessionUser)){
            return ResponseEntity.badRequest().body(result);
        }
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);

        }

        List<UserDTO> users = userService.findUserByName(user.getLastname(),user.getFirstname());

        if( !CollectionUtils.isEmpty(users)){
            result.setMsg("success");
            result.setResult(users);

        }else{
            result.setMsg("not found");
            result.setResult(new ArrayList<UserDTO>());
        }


        return ResponseEntity.ok(result);
    }

/**
 * http://localhost:8089/college/api/createfixture
 * {
 *     "id":"789",
 *     "time":"2021-11-19T12:07:41.213745-08:00",
 *     "name":121212,
 *     "enabled":true
 * }
 *
 * **/
    @PostMapping("/api/createfixture")
    public ResponseEntity<?> createRawFixture(@Valid @RequestBody FixtureDTO fixture , Errors errors, HttpServletRequest request) {
        AjaxResponseBody result = new AjaxResponseBody();
        try {
            kafkaProducer.produceFixtures(fixture);
        } catch (Exception e) {
            String message = Objects.isNull(fixture) ? "Fixture Exception" : fixture.toString();
            logger.error(message, e);
        }
        return ResponseEntity.ok(result);
    }

}
