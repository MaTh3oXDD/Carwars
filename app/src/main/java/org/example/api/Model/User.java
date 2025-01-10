package org.example.api.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private Boolean gender;
    private int selectedCarId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "user-ownership")
    private List<OwnershipOfCar> ownerships;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bag> bags;

    // Gettery i settery


    public User() {
    }

    public User(int id, String email, String password, String username, List<Bag> bags, Boolean gender, int selectedCarId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.bags = bags;
        this.gender = gender;
        this.selectedCarId = selectedCarId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Bag> getBags() {
        return bags;
    }

    public void setBags(List<Bag> bags) {
        this.bags = bags;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public List<OwnershipOfCar> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(List<OwnershipOfCar> ownerships) {
        this.ownerships = ownerships;
    }

    public int getSelectedCarId() {
        return selectedCarId;
    }

    public void setSelectedCarId(int selectedCarId) {
        this.selectedCarId = selectedCarId;
    }
}
