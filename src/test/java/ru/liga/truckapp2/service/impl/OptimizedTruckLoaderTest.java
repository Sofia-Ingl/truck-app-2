package ru.liga.truckapp2.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.PackagingAlgorithmType;
import ru.liga.truckapp2.model.Parcel;
import ru.liga.truckapp2.model.ParcelType;
import ru.liga.truckapp2.model.Truck;
import ru.liga.truckapp2.model.view.LoadedTruckView;
import ru.liga.truckapp2.service.TruckLoaderFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {DefaultTruckLoaderFactory.class, OptimizedTruckLoaderService.class})
@ActiveProfiles("test")
class OptimizedTruckLoaderTest {

    @Autowired
    private OptimizedTruckLoaderService optimizedTruckLoader;

    @Test
    void loadTrucks() {

        Truck truck1 = new Truck(6, 6);
        Truck truck2 = new Truck(6, 6);
        List<Truck> trucks = List.of(truck1, truck2);

        ParcelType type1 = new ParcelType(
                "1",
                new boolean[][]{{true}},
                '1'
        );

        ParcelType type2 = new ParcelType(
                "2",
                new boolean[][]{{true, true}},
                '2'
        );

        ParcelType type3 = new ParcelType(
                "3",
                new boolean[][]{{true, true, true}},
                '3'
        );

        List<Parcel> parcels = List.of(
                new Parcel(type1), new Parcel(type2), new Parcel(type3)
        );


        List<LoadedTruckView> res = optimizedTruckLoader.loadTrucks(
                parcels, trucks
        );

        assertThat(res.size()).isEqualTo(1);
        assertThat(res.get(0).getTruck().getBack()[0]).isEqualTo(new char[]{'3', '3', '3', '2', '2', '1'});
        assertThat(res.get(0).getTruck().getBack()[1]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' ', ' '});
    }

    @Test
    void loadTrucksStrangeShapes() {

        Truck truck1 = new Truck(6, 6);
        Truck truck2 = new Truck(6, 6);
        List<Truck> trucks = List.of(truck1, truck2);

        ParcelType typeO = new ParcelType(
                "o",
                new boolean[][]{{true, true, true}, {true, false, true}, {true, true, true}},
                'o'
        );

        ParcelType typePoint = new ParcelType(
                "point",
                new boolean[][]{{true}},
                'p'
        );

        ParcelType typeSquare = new ParcelType(
                "square",
                new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}},
                's'
        );

        List<Parcel> parcels = List.of(
                new Parcel(typeO), new Parcel(typeSquare), new Parcel(typePoint)
        );

        List<LoadedTruckView> res = optimizedTruckLoader.loadTrucks(
                parcels, trucks
        );

        assertThat(res.size()).isEqualTo(1);
        assertThat(res.get(0).getTruck().getBack()[0]).isEqualTo(new char[]{'s', 's', 's', 'o', 'o', 'o'});
        assertThat(res.get(0).getTruck().getBack()[1]).isEqualTo(new char[]{'s', 's', 's', 'o', 'p', 'o'});
        assertThat(res.get(0).getTruck().getBack()[2]).isEqualTo(new char[]{'s', 's', 's', 'o', 'o', 'o'});
        assertThat(res.get(0).getTruck().getBack()[3]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' ', ' '});
    }

    @Test
    void loadTrucksSpaceNotEnough() {
        Truck truck1 = new Truck(5, 3);
        List<Truck> trucks = List.of(truck1);

        ParcelType typeO = new ParcelType(
                "o",
                new boolean[][]{{true, true, true}, {true, false, true}, {true, true, true}},
                'o'
        );

        ParcelType typeSquare = new ParcelType(
                "square",
                new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}},
                's'
        );

        List<Parcel> parcels = List.of(
                new Parcel(typeO), new Parcel(typeSquare)
        );

        assertThatThrownBy(() ->
                optimizedTruckLoader.loadTrucks(parcels, trucks)
        ).isInstanceOf(AppException.class);
    }

    @Test
    void getAlgorithmType() {

        assertThat(optimizedTruckLoader.getAlgorithmType()).isEqualTo(PackagingAlgorithmType.OPTIMIZED);

    }
}