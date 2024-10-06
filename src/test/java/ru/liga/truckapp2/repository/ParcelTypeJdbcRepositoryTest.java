package ru.liga.truckapp2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.liga.truckapp2.dto.ParcelTypeCreateDto;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.model.ParcelType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class ParcelTypeJdbcRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ParcelTypeRepository repository;

    private void deleteAllFromParcelTypesTable() {
        String sql = "delete from parcel_types";
        jdbcTemplate.execute(sql);
    }

    @Test
    void findByName() throws Exception {

        deleteAllFromParcelTypesTable();

        String name = "*";
        String shape = "+";
        Character symbol = '*';

        Optional<ParcelType> parcelType = repository.findByName(name);
        assertThat(parcelType).isEmpty();

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name, shape, symbol);

        parcelType = repository.findByName(name);
        assertThat(parcelType).isPresent();

        ParcelType type = parcelType.get();

        assertThat(type.getName()).isEqualTo(name);
        assertThat(type.getShape()).isDeepEqualTo(new boolean[][]{{true}});
        assertThat(type.getSymbol()).isEqualTo(symbol);

    }

    @Test
    void findAll() {

        deleteAllFromParcelTypesTable();

        String name1 = "1";
        String shape1 = "+";
        Character symbol1 = '1';

        String name2 = "2";
        String shape2 = "++";
        Character symbol2 = '2';

        List<ParcelType> parcelTypes = repository.findAll();
        assertThat(parcelTypes).isEmpty();

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name1, shape1, symbol1);

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name2, shape2, symbol2);

        parcelTypes = repository.findAll();
        assertThat(parcelTypes.size()).isEqualTo(2);

        assertThat(parcelTypes.get(0).getName()).isEqualTo(name1);
        assertThat(parcelTypes.get(0).getShape()).isDeepEqualTo(new boolean[][]{{true}});
        assertThat(parcelTypes.get(0).getSymbol()).isEqualTo(symbol1);

        assertThat(parcelTypes.get(1).getName()).isEqualTo(name2);
        assertThat(parcelTypes.get(1).getShape()).isDeepEqualTo(new boolean[][]{{true, true}});
        assertThat(parcelTypes.get(1).getSymbol()).isEqualTo(symbol2);

    }

    @Test
    void save() {

        deleteAllFromParcelTypesTable();
        ParcelTypeCreateDto parcelTypeDto = new ParcelTypeCreateDto(
                "1",
                new boolean[][]{{true}},
                '1'
        );
        ParcelType parcelType = repository.save(parcelTypeDto);
        assertThat(parcelType).isNotNull();
        assertThat(parcelType.getName()).isEqualTo(parcelTypeDto.getName());
        assertThat(parcelType.getShape()).isDeepEqualTo(parcelTypeDto.getShape());
        assertThat(parcelType.getSymbol()).isEqualTo(parcelTypeDto.getSymbol());
    }

    @Test
    void saveWhenNameAlreadyExists() {

        deleteAllFromParcelTypesTable();

        String name = "1";
        String shape = "+";
        Character symbol = '1';

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name, shape, symbol);

        ParcelTypeCreateDto parcelTypeDto = new ParcelTypeCreateDto(
                "1",
                new boolean[][]{{true}},
                '1'
        );
        assertThatThrownBy(
                () -> repository.save(parcelTypeDto)
        ).isInstanceOf(AppException.class);
    }

    @Test
    void deleteByName() {
        deleteAllFromParcelTypesTable();

        String name = "1";
        String shape = "+";
        Character symbol = '1';

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name, shape, symbol);

        boolean result = repository.deleteByName(name);
        assertThat(result).isTrue();
        assertThat(repository.findByName(name)).isEmpty();
    }

    @Test
    void updateByName() {

        deleteAllFromParcelTypesTable();

        String name = "1";
        String shape = "+";
        Character symbol = '1';

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name, shape, symbol);

        ParcelTypeDto parcelTypeDto = new ParcelTypeDto(
                "2",
                new boolean[][]{{true, true}},
                '+'
        );

        ParcelType result = repository.updateByName(name, parcelTypeDto);
        assertThat(result.getName()).isEqualTo(parcelTypeDto.getName());
        assertThat(result.getShape()).isEqualTo(parcelTypeDto.getShape());
        assertThat(result.getSymbol()).isEqualTo(parcelTypeDto.getSymbol());

        assertThat(repository.findByName(name)).isEmpty();
        assertThat(repository.findByName(parcelTypeDto.getName())).isNotEmpty();

    }

    @Test
    void updateByNameWhenNewNameAlreadyExists() {

        deleteAllFromParcelTypesTable();

        String name1 = "1";
        String shape1 = "+";
        Character symbol1 = '1';

        String name2 = "2";
        String shape2 = "++";
        Character symbol2 = '2';

        List<ParcelType> parcelTypes = repository.findAll();
        assertThat(parcelTypes).isEmpty();

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name1, shape1, symbol1);

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name2, shape2, symbol2);

        ParcelTypeDto parcelTypeDto = new ParcelTypeDto(
                "1",
                new boolean[][]{{true, true}},
                '-'
        );

        assertThatThrownBy(
                () -> repository.updateByName(name2, parcelTypeDto)
        ).isInstanceOf(AppException.class);

    }

    @Test
    void findByShapeAndSymbol() {

        deleteAllFromParcelTypesTable();

        String name = "1";
        String shape = "+";
        Character symbol = '1';

        jdbcTemplate.update("""
                insert into parcel_types (name, shape, symbol)
                values (?, ?, ?);
                """, name, shape, symbol);

        Optional<ParcelType> type = repository.findByShapeAndSymbol(
                new boolean[][]{{true}},
                '1'
        );

        assertThat(type).isNotEmpty();

        type = repository.findByShapeAndSymbol(
                new boolean[][]{{true, true}},
                '1'
        );
        assertThat(type).isEmpty();

        type = repository.findByShapeAndSymbol(
                new boolean[][]{{true}},
                '2'
        );
        assertThat(type).isEmpty();

    }
}