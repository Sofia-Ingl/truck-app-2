package ru.liga.truckapp2.mapper;

public interface ShapeArrayMapper {

    String shapeToString(boolean[][] shapeArray);

    boolean[][] stringToShape(String shapeString);
}
