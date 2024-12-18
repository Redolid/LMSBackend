package com.example.LMS.dto;


import lombok.Data;

@Data
public class NotificationRequestDTO {
    private String recipientUsername;
    private String message;
    private String role;
    
 // Getters and Setters
    

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

   
}
