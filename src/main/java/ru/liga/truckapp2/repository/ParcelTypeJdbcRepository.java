package ru.liga.truckapp2.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.ParcelTypeRowMapper;
import ru.liga.truckapp2.mapper.ShapeArrayMapper;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ParcelTypeJdbcRepository implements ParcelTypeRepository {

    private final String SQL_FIND_BY_NAME = "select * from parcel_types where name = ?";
    private final String SQL_DELETE_BY_NAME = "delete from parcel_types where name = ?";
    private final String SQL_UPDATE_BY_NAME = "update parcel_types set name = ?, shape = ?, symbol  = ? where name = ?";
    private final String SQL_GET_ALL = "select * from parcel_types";
    private final String SQL_INSERT = "insert into parcel_types(name, shape, symbol) values(?,?,?)";
    private final String SQL_FIND_BY_SHAPE_AND_SYMBOL = "select * from parcel_types where shape = ? and symbol = ?";

    private final ParcelTypeRowMapper parcelTypeRowMapper;
    private final ShapeArrayMapper shapeArrayMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<ParcelType> findByName(String name) {
//        return jdbcTemplate.query(SQL_FIND_BY_NAME, parcelTypeRowMapper, name).stream().findFirst();
        ParcelType parcelType = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, parcelTypeRowMapper, name);
        log.debug("Parcel type got by name '{}': {}", name, parcelType);
        return Optional.ofNullable(parcelType);
    }

    @Override
    public List<ParcelType> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, parcelTypeRowMapper);
    }

    @Override
    public ParcelType save(ParcelTypeDto parcelType) {
        int rowCount = jdbcTemplate.update(
                SQL_INSERT,
                parcelType.getName(),
                shapeArrayMapper.shapeToString(parcelType.getShape()),
                parcelType.getSymbol()
        );
        if (rowCount != 1) {
            throw new AppException("Could not save parcel type " + parcelType);
        }
        return findByName(parcelType.getName())
                .orElseThrow(() -> new AppException("Invalid state when saving parcel type " + parcelType));
    }

    @Override
    public boolean deleteByName(String name) {
        int rowCount = jdbcTemplate.update(SQL_DELETE_BY_NAME, name);
        return rowCount == 1;
    }

    @Override
    public ParcelType updateByName(String name, ParcelTypeDto newData) {

        ParcelType parcelType = findByName(name).orElseThrow(
                () -> new AppException("Could not find parcel type with name " + name)
        );
        String newName = (newData.getName() != null) ? newData.getName() : parcelType.getName();
        String newShape = (newData.getShape() != null) ?
                shapeArrayMapper.shapeToString(newData.getShape())
                :
                shapeArrayMapper.shapeToString(parcelType.getShape());
        char newSymbol = (newData.getSymbol() != null) ? newData.getSymbol() : parcelType.getSymbol();
        int rowCount = jdbcTemplate.update(
                SQL_UPDATE_BY_NAME,
                newName,
                newShape,
                newSymbol,
                name
        );
        if (rowCount != 1) {
            throw new AppException("Could not update parcel type " + parcelType + " with name " + newName
                    + " and shape " + newShape + " and symbol " + newSymbol);
        }
        return findByName(newName)
                .orElseThrow(() -> new AppException("Invalid state when updating parcel type " + parcelType
                        + " with name " + newName + " and shape " + newShape + " and symbol " + newSymbol));
    }

    @Override
    public Optional<ParcelType> findByShapeAndSymbol(boolean[][] shape, char symbol) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                SQL_FIND_BY_SHAPE_AND_SYMBOL,
                parcelTypeRowMapper,
                shapeArrayMapper.shapeToString(shape),
                symbol
        ));
    }


}
