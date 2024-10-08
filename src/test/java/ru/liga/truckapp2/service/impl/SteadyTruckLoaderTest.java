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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(classes = {DefaultTruckLoaderFactory.class, SteadyTruckLoaderService.class})
@ActiveProfiles("test")
class SteadyTruckLoaderTest {

    @Autowired
    private SteadyTruckLoaderService steadyTruckLoader;

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


        List<LoadedTruckView> res = steadyTruckLoader.loadTrucks(
                parcels, trucks
        );

        assertThat(res.size()).isEqualTo(2);

        assertThat(res.get(1).getTruck().getBack()[0]).isEqualTo(new char[]{'3', '3', '3', ' ', ' ', ' '});
        assertThat(res.get(1).getTruck().getBack()[1]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' ', ' '});
        assertThat(res.get(0).getTruck().getBack()[0]).isEqualTo(new char[]{'2', '2', '1', ' ', ' ', ' '});
        assertThat(res.get(0).getTruck().getBack()[1]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' ', ' '});
    }

    @Test
    void loadTrucksStrangeShapes() {

        Truck truck1 = new Truck(3, 3);
        Truck truck2 = new Truck(3, 3);
        List<Truck> trucks = List.of(truck1, truck2);

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

        List<LoadedTruckView> res = steadyTruckLoader.loadTrucks(
                parcels, trucks
        );

        assertThat(res.size()).isEqualTo(2);

        assertThat(res.get(0).getTruck().getBack()[0]).isEqualTo(new char[]{'s', 's', 's'});
        assertThat(res.get(0).getTruck().getBack()[1]).isEqualTo(new char[]{'s', 's', 's'});
        assertThat(res.get(0).getTruck().getBack()[2]).isEqualTo(new char[]{'s', 's', 's'});

        assertThat(res.get(1).getTruck().getBack()[0]).isEqualTo(new char[]{'o', 'o', 'o'});
        assertThat(res.get(1).getTruck().getBack()[1]).isEqualTo(new char[]{'o', ' ', 'o'});
        assertThat(res.get(1).getTruck().getBack()[2]).isEqualTo(new char[]{'o', 'o', 'o'});
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
                steadyTruckLoader.loadTrucks(parcels, trucks)
        ).isInstanceOf(AppException.class);
    }


    @Test
    void loadTrucksAsStableAsPossible() {
        Truck truck1 = new Truck(5, 5);
        Truck truck2 = new Truck(5, 5);
        Truck truck3 = new Truck(5, 5);
        List<Truck> trucks = List.of(truck1, truck2, truck3);

        ParcelType type3 = new ParcelType(
                "3",
                new boolean[][]{{true, true, true}},
                '3'
        );

        ParcelType type5 = new ParcelType(
                "5",
                new boolean[][]{{true, true, true, true, true}},
                '5'
        );

        List<Parcel> parcels = List.of(
                new Parcel(type5),
                new Parcel(type3),
                new Parcel(type3),
                new Parcel(type3),
                new Parcel(type3),
                new Parcel(type3)
        );


        List<LoadedTruckView> res = steadyTruckLoader.loadTrucks(
                parcels, trucks
        );

        assertThat(res.size()).isEqualTo(3);

        assertThat(res.get(1).getTruck().getBack()[0]).isEqualTo(new char[]{'5', '5', '5', '5', '5'});
        assertThat(res.get(1).getTruck().getBack()[1]).isEqualTo(new char[]{'3', '3', '3', ' ', ' '});
        assertThat(res.get(1).getTruck().getBack()[2]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' '});

        assertThat(res.get(2).getTruck().getBack()[0]).isEqualTo(new char[]{'3', '3', '3', ' ', ' '});
        assertThat(res.get(2).getTruck().getBack()[1]).isEqualTo(new char[]{'3', '3', '3', ' ', ' '});
        assertThat(res.get(2).getTruck().getBack()[2]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' '});

        assertThat(res.get(0).getTruck().getBack()[0]).isEqualTo(new char[]{'3', '3', '3', ' ', ' '});
        assertThat(res.get(0).getTruck().getBack()[1]).isEqualTo(new char[]{'3', '3', '3', ' ', ' '});
        assertThat(res.get(0).getTruck().getBack()[2]).isEqualTo(new char[]{' ', ' ', ' ', ' ', ' '});
    }

    @Test
    void getAlgorithmType() {

        assertThat(steadyTruckLoader.getAlgorithmType()).isEqualTo(PackagingAlgorithmType.STEADY);

    }
}