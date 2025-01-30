package org.example.api.DTO;

public class OrderRequest {
    private int userId; // Przechwycenie user_id z żądania
    private String status; // Status zamówienia przesłany w JSON

    // Gettery i settery
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
