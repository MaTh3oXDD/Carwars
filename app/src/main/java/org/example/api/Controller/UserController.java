package org.example.api.Controller;

import org.example.api.DTO.CreateUserRequest;
import org.example.api.Model.User;
import org.example.api.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getbylogin")
    public Optional<User> findByUsername(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/findall")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email już istnieje");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("Użytkownik dodany pomyślnie");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Weryfikacja loginu i hasła
        boolean isValid = userService.validateUser(user.getEmail(), user.getPassword());
        if (isValid) {
            return ResponseEntity.ok("Logowanie zakończone sukcesem!");
        } else {
            return ResponseEntity.status(401).body("Nieprawidłowy e-mail lub hasło.");
        }
    }

    @GetMapping("/get-username")
    public String getUsernameByEmail(@RequestParam String email) {
        return userService.getUsernameByEmail(email);
    }
    @GetMapping("/get-gender/{username}")
    public Boolean getGenderByUsername(@PathVariable String username) {
        return userService.findGenderByUsername(username);
    }

    @GetMapping("/get-selected-car")
    public ResponseEntity<?> getSelectedCarIdByUsername(@RequestParam String username) {
        Integer selectedCarId = userService.findSelectedCarIdByUsername(username);
        if (selectedCarId != null) {
            return ResponseEntity.ok(selectedCarId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

}
