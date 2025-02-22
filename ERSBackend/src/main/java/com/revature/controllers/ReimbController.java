package com.revature.controllers;


import com.revature.aspects.AdminOnly;
import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin(value = "http://localhost:5173", allowCredentials = "true")
public class ReimbController {

    private final ReimbService reimbService;

    @Autowired
    public ReimbController(ReimbService reimbService) { this.reimbService = reimbService; }

    @GetMapping("/view")
    public ResponseEntity<List<Reimbursement>> getAllReimbursements() {
        return ResponseEntity.ok(reimbService.getAllReimbursements());
    }

    //method that inserts reimbursement into the DB
    @PostMapping
    public ResponseEntity<Reimbursement> insertReimb(@RequestBody IncomingReimbDTO reimbDTO){
        //send the DTO to the service and return reimb object that comes back
        return ResponseEntity.accepted().body(reimbService.insertReimb(reimbDTO));
    }

    //update reimb status with patch request
    @PatchMapping("/{id}/status")
    @AdminOnly
    public ResponseEntity<String> updateStatus(@PathVariable int id, @RequestBody String status) {
        boolean updated = reimbService.updateReimbursementStatus(id, status);

        if(updated) {
            return ResponseEntity.ok("Reimbursement state updated successfully.");
        } else {
            return ResponseEntity.status(404).body("Reimbursement not found.");
        }
    }

    @GetMapping("/my-reimbursements")
    public ResponseEntity<?> getMyReimbursements(HttpSession session) {
        try {
            List<Reimbursement> reimbursements = reimbService.getUserReimbursements(session);
            return ResponseEntity.ok(reimbursements);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body("User is not logged in.");
        }
    }
}
