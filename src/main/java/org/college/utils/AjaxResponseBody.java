package org.college.utils;

import lombok.Data;
import org.college.management.controllers.domain.QuestionDTO;
import org.college.management.controllers.domain.UserDTO;

import java.util.List;

@Data
public class AjaxResponseBody {
    String msg;
    List<UserDTO> result;
    UserDTO userResult;
    QuestionDTO questionResult;
}
