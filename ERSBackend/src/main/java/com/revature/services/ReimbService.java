package com.revature.services;


import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
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

    //gets all reimbursements to send to controller
    public List<Reimbursement> getAllReimbursements() {
        return reimbursementDAO.findAll();
    }



    //insert new reimb into the DB / get user by ID and make a reimb object with it
    public Reimbursement insertReimb(IncomingReimbDTO reimbDTO){

        Reimbursement newReimb = new Reimbursement(
                0,
                reimbDTO.getDescription(),
                reimbDTO.getAmount(),
                reimbDTO.getStatus(),
                null
        );

        Optional<User> user = userDAO.findById(reimbDTO.getUserId());

        if(user.isEmpty()) {
        } else {
            newReimb.setUser(user.get());
        }

        return reimbursementDAO.save(newReimb);
    }

    @Transactional
    public boolean updateReimbursementStatus(int id, String status){
        if (reimbursementDAO.existsById(id)) { //Check if reimbursement exists
            reimbursementDAO.updateReimbursementStatus(id, status); //Custom DAO method
            return true; // Update successful
        } else {
            return false; // Reimbursement not found
        }
    }

    //get all reimbs for user
    public  List<Reimbursement> getUserReimbursements(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if(userId == null) {
            throw new IllegalStateException("User is not logged in.");
        }
        return reimbursementDAO.findByUser_UserId(userId);
    }

    @Transactional
    public boolean deleteReimbursement(int reimbId) {
        if (reimbursementDAO.existsById(reimbId)) {
            reimbursementDAO.deleteById(reimbId);
            return true; // Successfully deleted
        } else {
            return false; // Not found
        }
    }



}
