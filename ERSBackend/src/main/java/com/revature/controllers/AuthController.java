package com.revature.controllers;


import com.revature.models.DTOs.LoginDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) { this.authService = authService; }

    //inserting a new user via POST req
    @PostMapping("/register")
    public ResponseEntity<OutgoingUserDTO> registerUser(@RequestBody User user){

        //send user data to service, which will send it to the DAO
        OutgoingUserDTO returnedUser = authService.registerUser(user);

        //send user data back to the client in a response
        return ResponseEntity.ok(returnedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<OutgoingUserDTO> login(@RequestBody LoginDTO loginDTO, HttpSession session){

        OutgoingUserDTO loggedInUser = authService.login(loginDTO);

        session.setAttribute("userId", loggedInUser.getUserId());
        session.setAttribute("firstname", loggedInUser.getUserId());
        session.setAttribute("lastname", loggedInUser.getUserId());
        session.setAttribute("username", loggedInUser.getUserId());
        session.setAttribute("role", loggedInUser.getUserId());

        System.out.println("User " + session.getAttribute("username") + " has logged in!");

        return ResponseEntity.ok(loggedInUser);
    }

}
