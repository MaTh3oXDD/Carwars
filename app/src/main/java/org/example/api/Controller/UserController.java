package org.example.api.Controller;

import org.example.api.DTO.CreateUserRequest;
import org.example.api.Model.Car;
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
    @PostMapping("/update-money")
    public ResponseEntity<?> updateUserMoney(@RequestParam String username, @RequestParam int money) {
        userService.updateUserMoney(username, money);
        return ResponseEntity.ok("Zaktualizowano stan konta");
    }
    @GetMapping("/get-username")
    public String getUsernameByEmail(@RequestParam String email) {
        return userService.getUsernameByEmail(email);
    }
    @GetMapping("/get-gender")
    public Boolean getGenderByUsername(@RequestParam String username) {
        return userService.findGenderByUsername(username);
    }
    @GetMapping("/get-selected-car")
    public Car getSelectedCar(@RequestParam String username) {
        return userService.getSelectedCarName(username); // Zwróć cały obiekt Car
    }
    @PostMapping("/change-selected-car")
    public ResponseEntity<?> changeSelectedCar(@RequestParam String username, @RequestParam int carId) {
        userService.changeSelectedCar(username, carId);
        return ResponseEntity.ok("Zmieniono samochód");
    }

    @PostMapping("/createuser")
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PostMapping("/{userId}/add-car/{carId}")
    public ResponseEntity<?> assignCarToUser(@PathVariable int userId, @PathVariable int carId) {
        User updatedUser = userService.addCarToUser(userId, carId);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or car not found");
    }
    @GetMapping("/get-userId")
    public ResponseEntity<?> getIdByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getIdByEmail(email));
    }
    @GetMapping("/get-items")
    public ResponseEntity<?> getItems(@RequestParam String username) {
        User user = userService.findByUsername(username).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(user.getBags().getBagItems());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    @DeleteMapping("/sell-item/{itemId}")
    public ResponseEntity<?> sellItem(
            @RequestHeader("Authorization") String username, // Username jako identyfikator
            @PathVariable int itemId) {
        try {
            userService.sellItem(username, itemId);
            return ResponseEntity.ok("Item sold successfully and removed from your bag!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
