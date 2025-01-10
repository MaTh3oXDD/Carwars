package org.example.api.Controller;

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

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findUserWithCars(@PathVariable int id) {
        User user = userService.findUserById(id);
        if (user != null) {
            List<String> carNames = user.getOwnerships().stream()
                    .map(ownership -> ownership.getCar().getName())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new UserWithCarsResponse(user.getUsername(), carNames));
        } else {
            return ResponseEntity.notFound().build();
        }
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

    private static class UserWithCarsResponse {
        private String username;
        private List<String> carNames;

        public UserWithCarsResponse(String username, List<String> carNames) {
            this.username = username;
            this.carNames = carNames;
        }

        public String getUsername() {
            return username;
        }

        public List<String> getCarNames() {
            return carNames;
        }
    }

}
