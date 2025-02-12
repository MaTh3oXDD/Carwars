package org.example.api.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "users") // Tabela nazywa się "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = true)
    private Boolean gender;

    @Column
    private Integer money;

    @Column(name = "selected_car_id")
    private int selectedCarId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Obsługujemy zarządzaną stronę relacji User -> Bag
    private Bag bags;

    // Relacja ManyToMany z Car
    @ManyToMany
    @JoinTable(
            name = "user_car", // Tabela pośrednia
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private List<Car> cars; // Lista samochodów powiązanych z użytkownikiem

    public int getSelectedCarId() {
        return selectedCarId;
    }

    public void setSelectedCarId(int selectedCarId) {
        this.selectedCarId = selectedCarId;
    }

    // Gettery i settery
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Bag getBags() {
        return bags;
    }

    public void setBags(Bag bags) {
        this.bags = bags;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
