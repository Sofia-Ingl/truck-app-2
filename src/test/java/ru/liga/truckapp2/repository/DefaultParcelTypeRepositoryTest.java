package ru.liga.truckapp2.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import ru.liga.truckapp2.dto.ParcelTypeDto;
import ru.liga.truckapp2.exception.AppException;
import ru.liga.truckapp2.mapper.DefaultParcelTypeMapper;
import ru.liga.truckapp2.model.ParcelType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultParcelTypeRepositoryTest {

    @Test
    void findByNameIfPresent() throws IOException {

        String fileName = "src\\test\\resources\\storage\\test-parcels-get.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "1",
                      "shape": [
                        [
                          true
                        ]
                      ],
                      "symbol": "1"
                    },
                    {
                      "name": "2",
                      "shape": [
                        [
                          true,
                          true
                        ]
                      ],
                      "symbol": "2"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        ParcelType type1 = repository.findByName("1").orElse(null);
        assertThat(type1).isNotNull();
        assertThat(type1.getName()).isEqualTo("1");
        boolean[][] shape = {{true}};
        assertThat(type1.getShape()).isDeepEqualTo(shape);
        assertThat(type1.getSymbol()).isEqualTo('1');

    }

    @Test
    void findByNameIfNotPresent() throws IOException {
        String fileName = "src\\test\\resources\\storage\\test-parcels-get.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "1",
                      "shape": [
                        [
                          true
                        ]
                      ],
                      "symbol": "1"
                    },
                    {
                      "name": "2",
                      "shape": [
                        [
                          true,
                          true
                        ]
                      ],
                      "symbol": "2"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        ParcelType type1 = repository.findByName("notPresent").orElse(null);
        assertThat(type1).isNull();

    }

    @Test
    void findAll() throws IOException {

        String fileName = "src\\test\\resources\\storage\\test-parcels-get.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "1",
                      "shape": [
                        [
                          true
                        ]
                      ],
                      "symbol": "1"
                    },
                    {
                      "name": "2",
                      "shape": [
                        [
                          true,
                          true
                        ]
                      ],
                      "symbol": "2"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        List<ParcelType> types = repository.findAll();
        assertThat(types).isNotEmpty();
        assertThat(types.size()).isEqualTo(2);
        assertThat(types.get(0).getName()).isEqualTo("1");
        assertThat(types.get(1).getName()).isEqualTo("2");

    }

    @Test
    void save() throws IOException {

        String fileName = "src\\test\\resources\\storage\\test-parcels-create.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );
        assertThat(repository.findAll()).isEmpty();
        ParcelTypeDto typeDto = new ParcelTypeDto(
                "kitty",
                new boolean[][]{{true, false, true}, {true, true, true}},
                'k'
        );
        ParcelType type = repository.save(typeDto);
        assertThat(type).isNotNull();
        assertThat(type.getName()).isEqualTo(typeDto.getName());
        assertThat(type.getShape()).isDeepEqualTo(typeDto.getShape());
        assertThat(type.getSymbol()).isEqualTo(typeDto.getSymbol());

        assertThat(repository.findAll().size()).isEqualTo(1);

        String storageContentString = Files.readString(path);
        Type typeToken = new TypeToken<List<ParcelType>>() {
        }.getType();
        List<ParcelType> deserialized = new Gson().fromJson(storageContentString, typeToken);

        assertThat(deserialized.size()).isEqualTo(1);
        assertThat(deserialized.get(0).getName()).isEqualTo(typeDto.getName());

    }


    @Test
    void saveIfNameAlreadyPresent() throws IOException {

        String fileName = "src\\test\\resources\\storage\\test-parcels-create.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                  {
                    "name": "1",
                    "shape": [
                      [
                        true
                      ]
                    ],
                    "symbol": "1"
                  }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        ParcelTypeDto typeDto = new ParcelTypeDto(
                "1",
                new boolean[][]{{true, false, true}, {true, true, true}},
                'k'
        );

        assertThatThrownBy(() ->
                repository.save(typeDto)
        ).isInstanceOf(AppException.class);

        assertThat(repository.findAll().size()).isEqualTo(1);
        assertThat(repository.findAll().get(0).getName()).isEqualTo("1");
        assertThat(repository.findAll().get(0).getSymbol()).isEqualTo('1');
        assertThat(repository.findAll().get(0).getShape()).isEqualTo(new boolean[][]{{true}});


    }

    @Test
    void deleteByNameIfPresent() throws IOException {
        String fileName = "src\\test\\resources\\storage\\test-parcels-delete.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                  {
                    "name": "1",
                    "shape": [
                      [
                        true
                      ]
                    ],
                    "symbol": "1"
                  }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        boolean res = repository.deleteByName("1");

        assertThat(res).isTrue();
        assertThat(repository.findAll()).isEmpty();

    }

    @Test
    void deleteByNameIfNotPresent() throws IOException {
        String fileName = "src\\test\\resources\\storage\\test-parcels-delete.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                  {
                    "name": "1",
                    "shape": [
                      [
                        true
                      ]
                    ],
                    "symbol": "1"
                  }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );
        assertThat(repository.findAll().size()).isEqualTo(1);
        assertThat(repository.findAll().get(0).getName()).isEqualTo("1");

        boolean res = repository.deleteByName("wrong name");

        assertThat(res).isFalse();
        assertThat(repository.findAll().size()).isEqualTo(1);
        assertThat(repository.findAll().get(0).getName()).isEqualTo("1");

    }

    @Test
    void updateByName() throws IOException {

        String fileName = "src\\test\\resources\\storage\\test-parcels-update.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "1",
                      "shape": [
                        [
                          true
                        ]
                      ],
                      "symbol": "1"
                    },
                    {
                      "name": "2",
                      "shape": [
                        [
                          true,
                          true
                        ]
                      ],
                      "symbol": "2"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        ParcelTypeDto typeDto = new ParcelTypeDto("1 upd", new boolean[][]{{true,true,true}}, '3');
        ParcelType updated = repository.updateByName("1", typeDto);

        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("1 upd");
        assertThat(updated.getSymbol()).isEqualTo('3');
        assertThat(updated.getShape()).isDeepEqualTo(new boolean[][]{{true,true,true}});

        assertThat(repository.findAll().size()).isEqualTo(2);

    }


    @Test
    void updateByNameIfNameNotPresent() throws IOException {

        String fileName = "src\\test\\resources\\storage\\test-parcels-update.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "2",
                      "shape": [
                        [
                          true,
                          true
                        ]
                      ],
                      "symbol": "2"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        ParcelTypeDto typeDto = new ParcelTypeDto("1 upd", new boolean[][]{{true,true,true}}, '3');

        assertThatThrownBy(() ->
                repository.updateByName("1", typeDto)
        ).isInstanceOf(AppException.class);

        assertThat(repository.findAll().size()).isEqualTo(1);

    }

    @Test
    void findByShapeAndSymbol() throws IOException {
        String fileName = "src\\test\\resources\\storage\\test-parcels-get.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "1",
                      "shape": [
                        [
                          true
                        ]
                      ],
                      "symbol": "1"
                    },
                    {
                      "name": "2",
                      "shape": [
                        [
                          true,
                          true
                        ]
                      ],
                      "symbol": "2"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        boolean[][] shape = {{true,true}};
        char symbol = '2';

        ParcelType type2 = repository.findByShapeAndSymbol(shape, symbol).orElse(null);
        assertThat(type2).isNotNull();
        assertThat(type2.getName()).isEqualTo("2");
        assertThat(type2.getSymbol()).isEqualTo(symbol);
        assertThat(type2.getShape()).isDeepEqualTo(shape);
    }


    @Test
    void findByShapeAndSymbolIfNotPresent() throws IOException {
        String fileName = "D:\\IdeaProjects\\truck-app-2\\src\\test\\resources\\storage\\test-parcels-get.json";
        Path path = Path.of(fileName);
        String preparedParcelTypes = """
                [
                    {
                      "name": "1",
                      "shape": [
                        [
                          true
                        ]
                      ],
                      "symbol": "1"
                    }
                ]
                """;
        Files.writeString(path, preparedParcelTypes);
        DefaultParcelTypeRepository repository = new DefaultParcelTypeRepository(
                new Gson(),
                new DefaultParcelTypeMapper(),
                fileName
        );

        boolean[][] shape = {{true,true}};
        char symbol = '2';

        ParcelType type2 = repository.findByShapeAndSymbol(shape, symbol).orElse(null);
        assertThat(type2).isNull();
    }

}