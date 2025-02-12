package org.example.api.Service;

import org.example.api.Model.Car;
import org.example.api.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findAllCars() {
        return carRepository.findAll();
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }
    public String findCarNameById(int id) {
        Optional<Car> car = carRepository.findById(id);
        return car.map(Car::getName).orElse(null);
    }
    public List<Car> addMultipleCars(List<Car> cars) {
        return carRepository.saveAll(cars);
    }
    public Car getRandomCar() {
        List<Car> cars = findAllCars();
        if(cars.isEmpty()) {
            throw new IllegalStateException("No cars found");
        }
        int randomIndex = (int) (Math.random() * cars.size());
        return cars.get(randomIndex);
    }

}
