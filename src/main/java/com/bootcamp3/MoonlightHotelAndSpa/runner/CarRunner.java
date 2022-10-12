//package com.bootcamp3.MoonlightHotelAndSpa.runner;
//
//import com.bootcamp3.MoonlightHotelAndSpa.model.car.Car;
//import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;
//import com.bootcamp3.MoonlightHotelAndSpa.service.CarCategoryService;
//import com.bootcamp3.MoonlightHotelAndSpa.service.CarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.util.HashSet;
//
//@Component
//public class CarRunner implements CommandLineRunner {
//
//    private final CarService carService;
//    private final CarCategoryService carCategoryService;
//
//    @Autowired
//    public CarRunner(CarService carService, CarCategoryService carCategoryService) {
//        this.carService = carService;
//        this.carCategoryService = carCategoryService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        CarCategory van = carCategoryService.findCategoryById(1L);
//        CarCategory sport = carCategoryService.findCategoryById(2L);
//        CarCategory luxury = carCategoryService.findCategoryById(3L);
//
//        Car bmw = new Car();
//        bmw.setBrand("BMW");
//        bmw.setModel("M5");
//        bmw.setImage("somePic.jpg");
//        bmw.setImages(new HashSet<>());
//        bmw.setYear(2022);
//        bmw.setCreated(Instant.now().toString());
//        bmw.setCategory(sport);
//
//        carService.createCar(bmw);
//
//        Car mercedes = new Car();
//        mercedes.setBrand("Mercedes");
//        mercedes.setModel("S500");
//        mercedes.setImage("somePic.jpg");
//        mercedes.setImages(new HashSet<>());
//        mercedes.setYear(2022);
//        mercedes.setCreated(Instant.now().toString());
//        mercedes.setCategory(luxury);
//
//        carService.createCar(mercedes);
//
//        Car vw = new Car();
//        vw.setBrand("VW");
//        vw.setModel("Multivan");
//        vw.setImage("somePic.jpg");
//        vw.setImages(new HashSet<>());
//        vw.setYear(2022);
//        vw.setCreated(Instant.now().toString());
//        vw.setCategory(van);
//
//        carService.createCar(vw);
//    }
//}
