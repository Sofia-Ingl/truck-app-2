package ru.liga.truckapp2.mapper;

public interface ShapeArrayMapper {

    /**
     * @param shapeArray массив, задающий форму в типе посылок в java
     * @return строка, задающая форму типа посылок в бд
     */
    String shapeToString(boolean[][] shapeArray);

    /**
     * @param shapeString строка, задающая форму типа посылок в бд
     * @return массив, задающий форму в типе посылок в java
     */
    boolean[][] stringToShape(String shapeString);
}
