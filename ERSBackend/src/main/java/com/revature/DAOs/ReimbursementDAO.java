package com.revature.DAOs;


import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {


    //finds list of reimb by userId
    public List<Reimbursement> findByUser_UserId(int userId);


}
