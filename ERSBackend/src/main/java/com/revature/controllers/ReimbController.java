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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
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

    @GetMapping("/my-reimbursements")
    public ResponseEntity<?> getMyReimbursements(HttpSession session) {
        try {
            List<Reimbursement> reimbursements = reimbService.getUserReimbursements(session);
            return ResponseEntity.ok(reimbursements);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(401).body("User is not logged in.");
        }
    }

    @PatchMapping("/{reimbId}/status/{newStatus}")
    public ResponseEntity<?> updateReimbursementStatus(@PathVariable int reimbId, @PathVariable String newStatus) {
        boolean updated = reimbService.updateReimbursementStatus(reimbId, newStatus);

        if (updated) {
            return ResponseEntity.ok("Reimbursement " + reimbId + " updated to " + newStatus);
        } else {
            return ResponseEntity.status(404).body("Reimbursement not found");
        }
    }

    @DeleteMapping("/{reimbId}")
    public ResponseEntity<?> deleteReimbursement(@PathVariable int reimbId) {
        boolean deleted = reimbService.deleteReimbursement(reimbId);

        if (deleted) {
            return ResponseEntity.ok("Reimbursement " + reimbId + " deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Reimbursement not found.");
        }
    }



}
