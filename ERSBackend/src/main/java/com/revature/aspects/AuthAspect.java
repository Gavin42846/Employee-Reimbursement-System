package com.revature.aspects;


import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthAspect {

    @Order(1) //this advice will always run first
    @Before("within(com.revature.controllers.*)" +
            "&& !within(com.revature.controllers.AuthController)")
    public void checkLoggedIn(){


        //get access to the session(or lack thereof)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        //we are getting an object that has the session and then trying to extract that session
        //getSession(false) dont make a new session if one doesn't exist

        //if the session is null, the user isnt logged in, throw an exception
        if(session == null || session.getAttribute("userId") == null) {
            throw new IllegalArgumentException("User must be logged in to do this!");
        }
    }

    @Order(2)
    @Before("@annotation(com.revature.aspects.AdminOnly)") //dont need com.revature.aspects cause it's in same folder
    public void checkAdmin(){
        //get access to the session so we can extract the role attribute
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        //first we will check if session exists
        if(session == null){
            throw new IllegalArgumentException("User must be logged in to do this!");
        }

        String role = session.getAttribute("role").toString();

        //if the users role != "admin" throw an exception
        if(!role.equals("admin")){
            throw new IllegalArgumentException("User must be an admin to do this!");
        }
    }
}
