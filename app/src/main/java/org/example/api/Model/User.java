package org.example.api.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
@Table(name = "users") // Tabela nazywa się "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatyczne generowanie klucza głównego
    private int id; // Klucz główny (Primary Key)

    @Column(nullable = false, unique = true) // E-mail musi być unikalny i nie null
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

    // Relacja OneToMany z klasą Bag - użytkownik może mieć wiele "Bag"
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Rozwiązuje problem cyklicznych referencji w JSON-ie (dotyczy JPA + Jackson)
    private Bag bags;

    // Konstruktor domyślny (wymagany przez Hibernate)
    public User() {
    }

    // Konstruktor z parametrami
    public User(String email, String password, String username, Boolean gender, int selectedCarId) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.gender = gender;
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

    public int getSelectedCarId() {
        return selectedCarId;
    }

    public void setSelectedCarId(int selectedCarId) {
        this.selectedCarId = selectedCarId;
    }

    public Bag getBags() {
        return bags;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public void setBags(Bag bags) {
        this.bags = bags;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
