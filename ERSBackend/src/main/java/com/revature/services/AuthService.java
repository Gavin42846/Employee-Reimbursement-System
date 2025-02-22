package com.revature.services;

//this the service layer for user input validation, data manip/reformatting, user auth

import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.LoginDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserDAO userDAO;

    @Autowired
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    //this will take a user object and send it to the DAO
    //it will also return the inserted user to the controller
    public OutgoingUserDTO registerUser(User user){

        //TODO: input validation

        //save is used to insert data to DB
        User returnedUser = userDAO.save(user);

        //convert User to UserDTO before we send it to the client (DB)
        OutgoingUserDTO outUserDTO = new OutgoingUserDTO(
                returnedUser.getUserId(),
                returnedUser.getFirstname(),
                returnedUser.getLastname(),
                returnedUser.getUsername(),
                returnedUser.getRole()
        );
        return outUserDTO;
    }

    //login DTO takes a LoginDTO and uses those fields to try and get a user from the DAO
    public OutgoingUserDTO login(LoginDTO loginDTO){

        //input validation

        //check if username is null or if it is empty string or space only
        if (loginDTO.getUsername() == null || loginDTO.getUsername().isBlank()){
            throw new IllegalArgumentException("Username cannot be empty!");
        }

        //same for pw
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isBlank()){
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        //try to get a user from the DAO
        User returnedUser = userDAO.findByUsernameAndPassword(
                loginDTO.getUsername(),
                loginDTO.getPassword()).orElse(null); //used to get data from optional

        //if no user is found (if returned user is null) throw ex
        if(returnedUser == null) {
            throw new IllegalArgumentException("Invalid username or password!");
        }

        //if we get here login was successful
        return new OutgoingUserDTO(returnedUser);
    }

}
