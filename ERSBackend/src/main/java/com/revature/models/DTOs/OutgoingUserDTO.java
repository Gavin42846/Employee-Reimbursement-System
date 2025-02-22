package com.revature.models.DTOs;




import com.revature.models.User;




public class OutgoingUserDTO {


    private int userId;
    private String username;
    private String firstname;
    private String lastname;
    private String role;

    //bp----------------------------


    public OutgoingUserDTO() {
    }

    public OutgoingUserDTO(int userId, String username, String firstname, String lastname, String role) {
        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }

    public OutgoingUserDTO(User u) {
        this.userId = u.getUserId();
        this.username = u.getUsername();
        this.firstname = u.getFirstname();
        this.lastname = u.getLastname();
        this.role = u.getRole();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}
