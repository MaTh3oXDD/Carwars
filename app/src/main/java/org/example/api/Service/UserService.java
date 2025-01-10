package org.example.api.Service;

import org.example.api.Model.User;
import org.example.api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
    public Integer findSelectedCarIdByUsername(String username) { Optional<User> user = userRepository.findByUsername(username); return user.map(User::getSelectedCarId).orElse(null); }
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

}
