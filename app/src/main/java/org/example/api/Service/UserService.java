package org.example.api.Service;

import jakarta.transaction.Transactional;
import org.example.api.DTO.CreateUserRequest;
import org.example.api.Model.Bag;
import org.example.api.Model.User;
import org.example.api.Repository.BagRepository;
import org.example.api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BagRepository bagRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean validateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).isPresent();
    }

    public Integer findSelectedCarIdByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getSelectedCarId).orElse(null);
    }

    public String getUsernameByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getUsername)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public Boolean findGenderByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getGender)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User createUser(CreateUserRequest request) {
        // Tworzenie użytkownika
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setGender(request.getGender());
        user.setSelectedCarId(request.getSelectedCarId());
        user.setMoney(1000); // Ustawienie domyślnej wartości money na 1000

        // Zapis użytkownika w bazie
        User savedUser = userRepository.save(user);

        // Tworzenie plecaka
        Bag bag = new Bag();
        bag.setCapacity(request.getBag().getCapacity());
        bag.setUser(savedUser); // Przypisanie plecaka do użytkownika

        bagRepository.save(bag);
        return savedUser;
    }
}
