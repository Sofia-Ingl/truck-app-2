package ru.liga.truckapp2.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.liga.truckapp2.model.ParcelType;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Component
public class ParcelTypeRowMapper implements RowMapper<ParcelType> {

    private final ShapeArrayMapper shapeArrayMapper;

    @Override
    public ParcelType mapRow(ResultSet rs, int rowNum) throws SQLException {

        String name = rs.getString("name");
        char symbol = rs.getString("symbol").charAt(0);
        boolean[][] shape = shapeArrayMapper.stringToShape(rs.getString("shape"));
        return new ParcelType(
                name,
                shape,
                symbol
        );
    }
}
