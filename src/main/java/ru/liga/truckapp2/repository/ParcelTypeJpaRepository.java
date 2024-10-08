package ru.liga.truckapp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.truckapp2.model.ParcelType;

import java.util.Optional;

@Repository
public interface ParcelTypeJpaRepository extends JpaRepository<ParcelType, Long> {

    Optional<ParcelType> findByName(String name);

    int deleteByName(String name);

    Optional<ParcelType> findByShapeAndSymbol(boolean[][] shape, char symbol);
}