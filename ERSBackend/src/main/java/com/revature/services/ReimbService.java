package com.revature.services;


import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReimbService {

    private final UserDAO userDAO;
    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public ReimbService(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
    }



    //insert new reimb into the DB / get user by ID and make a reimb object with it
    public Reimbursement insertReimb(IncomingReimbDTO reimbDTO){


        //TODO: input validation

        Reimbursement newReimb = new Reimbursement(
                0,
                reimbDTO.getDescription(),
                reimbDTO.getAmount(),
                reimbDTO.getStatus(),
                null
        );

        Optional<User> user = userDAO.findById(reimbDTO.getUserId());

        if(user.isEmpty()) {
            //TODO: throw an exception

        } else {
            newReimb.setUser(user.get());
        }

        return reimbursementDAO.save(newReimb);





    }
}
