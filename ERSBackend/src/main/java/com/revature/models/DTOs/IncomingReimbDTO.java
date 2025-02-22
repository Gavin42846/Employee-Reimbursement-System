package com.revature.models.DTOs;


import java.math.BigDecimal;

public class IncomingReimbDTO {

    private BigDecimal amount;
    private String description;
    private String status;
    private int userId;

    //bp-------------------------------


    public IncomingReimbDTO() {
    }

    public IncomingReimbDTO(BigDecimal amount, String description, String status, int userId) {
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomingReimbDTO{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}
