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

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");

        if (userId == null) {
            return ResponseEntity.status(401).body("User is not logged in");
        }
        return ResponseEntity.ok().body(
                new LoggedInUserDTO(userId, username)
        );
    }
    // DTO to send logged-in user details
    private static class LoggedInUserDTO {
        public Integer userId;
        public String username;

        public LoggedInUserDTO(Integer userId, String username) {
            this.userId = userId;
            this.username = username;
        }
    }

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
        session.setAttribute("firstname", loggedInUser.getFirstname());
        session.setAttribute("lastname", loggedInUser.getLastname());
        session.setAttribute("username", loggedInUser.getUsername());
        session.setAttribute("role", loggedInUser.getRole());

        System.out.println("User " + session.getAttribute("username") + " has logged in!" + session.getAttribute("role"));

        return ResponseEntity.ok(loggedInUser);
    }

}
