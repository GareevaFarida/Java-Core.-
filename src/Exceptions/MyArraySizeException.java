package Exceptions;

public class MyArraySizeException extends Exception {
    public MyArraySizeException() {
    }

    public MyArraySizeException(String message) {
        super(message);
    }

    public MyArraySizeException(int RowsCount,int StandartRowsCount){
        super("В метод передан массив с несоответствующей размерностью массива. Количество строк в массиве: " + RowsCount + " вместо " + StandartRowsCount);
    }

    public  MyArraySizeException(int RowIndex,int RowLength,int StandartRowLength) {
        super("В метод передан массив с несоответствующей размерностью массива. Количество элементов в строке с индексом: " + RowIndex + " составляет " + RowLength + " вместо " + StandartRowLength);
    }
}