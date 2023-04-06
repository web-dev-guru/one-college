package org.college.management.service;

import org.college.management.controllers.domain.UserDTO;
import org.college.management.dao.UserRepository;
import org.college.management.dao.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserDTO save(UserDTO user){
        User u = convertUserDTO(user);
        User returnU= userRepository.save(u);
        return  convertUser(returnU);}
    public List<UserDTO> findUserByEmail(String email){
        List<User> users = userRepository.findUserByEmail(email);
        List<UserDTO> userList = new ArrayList<UserDTO>();
        if(!users.isEmpty()){
            users.stream().forEach(user -> userList.add(convertUser(user)) );
        }
        return userList;
    }

    public List<UserDTO> findUserByName(String lastName,String firstName){
        List<User> users=new ArrayList<User>();
        if( !ObjectUtils.isEmpty(lastName) && !ObjectUtils.isEmpty(firstName)){
             users = userRepository.findUserByLastNameFirstName(lastName,firstName);
        }else if(ObjectUtils.isEmpty(lastName) && !ObjectUtils.isEmpty(firstName)){
             users = userRepository.findUserByFirstName(firstName);
        }else if(ObjectUtils.isEmpty(firstName) && !ObjectUtils.isEmpty(lastName)){
             users = userRepository.findUserByLastName(lastName);
        }else{
             users = userRepository.findAll();
        }

        List<UserDTO> userList = new ArrayList<UserDTO>();
        if(!users.isEmpty()){
            users.stream().forEach(user -> userList.add(convertUser(user)) );
        }
        return userList;
    }
    private User convertUserDTO(UserDTO user){

        User u = User.builder().email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .endDate(user.getEndDate())
                .lastName(user.getLastname())
                .firstName(user.getFirstname())
                .startDate(user.getStartDate())
                .status(user.getStatus())
                .build();
        u.setLastUpdatedTime(LocalDateTime.now());
        u.setInsertTime(LocalDateTime.now());
        u.setOperator(user.getOperator());
        return u;

    }

    private UserDTO convertUser(User user){
        UserDTO udto = UserDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .role(user.getRole())
                .password(user.getPassword())
                .status(user.getStatus())
                .startDate(user.getStartDate())
                .endDate(user.getEndDate())
                .build();
        return udto;

    }
}
