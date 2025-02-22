package com.revature.controllers;


import com.revature.aspects.AdminOnly;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //makes class a bean and turns HTTP response bodies into json
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    //return all users to the client
    @GetMapping
    @AdminOnly
    public ResponseEntity<List<OutgoingUserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers()); // returns all users
    }

    @DeleteMapping("/{userId}")
    @AdminOnly
    public  ResponseEntity<String> deleteUser(@PathVariable int userId) {
        boolean isDeleted = userService.deleteUser(userId);
        if(isDeleted) {
            return ResponseEntity.ok("User and their reimbursements deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}
