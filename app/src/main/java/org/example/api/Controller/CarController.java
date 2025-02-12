package org.example.api.Controller;

import org.example.api.Model.Car;
import org.example.api.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/findall")
    public List<Car> findAllCars() {
        return carService.findAllCars();
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car newCar = carService.addCar(car);
        return ResponseEntity.ok(newCar);
    }
    // CarController.java
    @GetMapping("/get-car-name")
    public ResponseEntity<?> getCarNameById(@RequestParam int id) {
        String carName = carService.findCarNameById(id);
        if (carName != null) {
            return ResponseEntity.ok(carName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
        }
    }
    @PostMapping("/add-multiple")
    public ResponseEntity<List<Car>> addMultipleCars(@RequestBody List<Car> cars) {
        List<Car> newCars = carService.addMultipleCars(cars);
        return ResponseEntity.ok(newCars);
    }
    @GetMapping("/random")
    public ResponseEntity<Car> getRandomCar() {
        Car randomCar = carService.getRandomCar();
        return ResponseEntity.ok(randomCar);
    }


}
