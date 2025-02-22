package com.revature.services;


import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    //autowire the DAO so we can use it's methods
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //get all users from the DB
    public List<OutgoingUserDTO> getAllUsers(){

        List<User> returnedUsers = userDAO.findAll();

        List<OutgoingUserDTO> userDTOS = new ArrayList<>();

        for (User u : returnedUsers){
            userDTOS.add(new OutgoingUserDTO(u));
        }

        return userDTOS;


    }












}
