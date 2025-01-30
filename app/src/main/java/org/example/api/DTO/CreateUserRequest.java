package org.example.api.DTO;

public class CreateUserRequest {
    private String email;
    private String username;
    private String password;
    private Boolean gender;
    private int selectedCarId;
    private BagRequest bag;

    // Gettery i settery

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public int getSelectedCarId() {
        return selectedCarId;
    }

    public void setSelectedCarId(int selectedCarId) {
        this.selectedCarId = selectedCarId;
    }

    public BagRequest getBag() {
        return bag;
    }

    public void setBag(BagRequest bag) {
        this.bag = bag;
    }

    public static class BagRequest {
        private int capacity;

        // Gettery i settery
        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
    }

    // Reszta getterów i setterów
}

