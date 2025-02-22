package com.revature.controllers;


import com.revature.models.DTOs.IncomingReimbDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin
public class ReimbController {

    private final ReimbService reimbService;

    @Autowired
    public ReimbController(ReimbService reimbService) { this.reimbService = reimbService; }

    //method that inserts reimbursement into the DB
    @PostMapping
    public ResponseEntity<Reimbursement> insertReimb(@RequestBody IncomingReimbDTO reimbDTO){
        //send the DTO to the service and return reimb object that comes back
        return ResponseEntity.accepted().body(reimbService.insertReimb(reimbDTO));
    }
}
