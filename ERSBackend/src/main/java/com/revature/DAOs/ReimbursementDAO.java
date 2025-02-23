package com.revature.DAOs;


import com.revature.models.Reimbursement;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {


    //finds list of reimb by userId
    public List<Reimbursement> findByUser_UserId(int userId);

    //deletes a user and their reimbursements
    @Modifying
    @Transactional
    @Query("DELETE FROM Reimbursement r WHERE r.user.userId = :userId")
    void deleteByUser_UserId(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE Reimbursement r SET r.status = :status WHERE r.reimbId = :id")
    void updateReimbursementStatus(@Param("id") int id, @Param("status") String status);

}
