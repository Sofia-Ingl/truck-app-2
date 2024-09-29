package ru.liga.truckapp2.service;

import ru.liga.truckapp2.model.Truck;

import java.util.List;

public interface TruckService {

    List<Truck> createTrucks(Integer width, Integer height, Integer quantity);

    List<Truck> createTrucksCustomized(Boolean fromFile, String input);
}
