package com.revature.services;


import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //autowire the DAO so we can use it's methods
    private final UserDAO userDAO;
    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public UserService(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
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

    //delete user from DB
    @Transactional
    public boolean deleteUser(int userId) {
        Optional<User> user = userDAO.findById(userId);

        if(user.isPresent()) {
            //first delete all reimbursements owned by user
            reimbursementDAO.deleteByUser_UserId(userId);
            //now delete user
            userDAO.deleteById(userId);

            //if user is found
            return true;
        }
        //if user is not found
        return false;
    }
}
