package org.example.api.Service;

import jakarta.transaction.Transactional;
import org.example.api.DTO.CreateUserRequest;
import org.example.api.Model.*;
import org.example.api.Repository.BagItemRepository;
import org.example.api.Repository.BagRepository;
import org.example.api.Repository.CarRepository;
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
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BagItemRepository bagItemRepository;

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
    public Car getSelectedCarName(String username) {
        return userRepository.findByUsername(username)
                .flatMap(user -> carRepository.findById(user.getSelectedCarId())) // Extract `selectedCarId` from `User`

                .orElseThrow(() -> new RuntimeException("No car found for username: " + username));
    }
    public Boolean changeSelectedCar(String username, int carId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        user.setSelectedCarId(carId);
        userRepository.save(user);
        return true;
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public User addCarToUser(int userId, int carId) {
        User user = userRepository.findById(userId).orElse(null);
        Car car = carRepository.findById(carId).orElse(null);

        if (user != null && car != null) {
            user.getCars().add(car); // Dodanie samochodu do listy
            return userRepository.save(user); // Zapis użytkownika z przypisanym samochodem
        }

        return null; // Jeśli użytkownik lub samochód nie istnieją
    }
    public void updateUserMoney(String username, int money) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        if(user.getMoney() == null){
            user.setMoney(money);
        }else{
            int usermoney = user.getMoney()+money;
            user.setMoney(usermoney);
        }

        userRepository.save(user);
    }
    public List<BagItem> getBagsitems(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return user.getBags().getBagItems();
    }
    public User getIdByEmail(String email) {
        return userRepository.findIdByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    @Transactional
    public User createUser(CreateUserRequest request) {
        // Tworzenie użytkownika
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setGender(request.getGender());
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
    public void sellItem(String username, int itemId) {
        // Znajdź użytkownika po nazwie
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Sprawdź, czy użytkownik faktycznie ma ten BagItem
        BagItem bagItem = bagItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found in your bag"));

        if (!bagItem.getBag().getUser().getUsername().equals(username)) {
            throw new RuntimeException("This item does not belong to you!");
        }

        // (Opcjonalna logika dodawania pieniędzy po sprzedaży)
        Item item = bagItem.getItem(); // Znajdź powiązany "Item"
        int sellPrice = 50; // Cena sprzedaży lub wartość domyślna
        user.setMoney(user.getMoney() + sellPrice);

        // Zapisz zaktualizowane saldo użytkownika
        userRepository.save(user);

        // Usuń element z Bag (oraz bazy)
        bagItemRepository.delete(bagItem);
    }

}
