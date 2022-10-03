//package com.bootcamp3.MoonlightHotelAndSpa.runner;
//
//import com.bootcamp3.MoonlightHotelAndSpa.enumeration.CarTitle;
//import com.bootcamp3.MoonlightHotelAndSpa.model.car.CarCategory;
//import com.bootcamp3.MoonlightHotelAndSpa.repository.CarCategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CarCategoriesRunner implements CommandLineRunner {
//
//    private final CarCategoryRepository carCategoryRepository;
//
//    @Autowired
//    public CarCategoriesRunner(CarCategoryRepository carCategoryRepository) {
//        this.carCategoryRepository = carCategoryRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        CarCategory van = new CarCategory();
//        van.setTitle(CarTitle.VAN);
//        van.setSeats(7);
//        van.setPrice(123.45);
//
//        carCategoryRepository.save(van);
//
//        CarCategory sport = new CarCategory();
//        sport.setTitle(CarTitle.SPORT);
//        sport.setSeats(2);
//        sport.setPrice(456.78);
//
//        carCategoryRepository.save(sport);
//
//        CarCategory luxury = new CarCategory();
//        luxury.setTitle(CarTitle.LUXURY);
//        luxury.setSeats(5);
//        luxury.setPrice(500.00);
//
//        carCategoryRepository.save(luxury);
//    }
//}
