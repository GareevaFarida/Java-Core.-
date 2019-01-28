import Enums.Dimentions;
import Exceptions.MyArrayDataException;
import Exceptions.MyArraySizeException;

public class Application {


    public int CheckMassive(String[][] massive) throws MyArraySizeException, MyArrayDataException {

        //Задание 1.
        //контрольные значения измерений получаем из перечисления
        int x = Dimentions.X.getValue();
        int y = Dimentions.Y.getValue();

        if (massive.length != x) {
            throw new MyArraySizeException(massive.length, x);
        }

        //проверяем каждую строку массива на правильность количества элементов
        for (int i = 0; i < x; i++) {
            if (massive[i].length != y) {
                throw new MyArraySizeException(i, massive[i].length, y);
            }
        }
        // System.out.println("Размер переданного массива соответствует контрольным значениям");

        //Задание 2.
        //создаем массив для целочисленных значений, полученных в результате преобразования строки
        int[][] massiveInt = new int[x][y];

        boolean dataError = false;
        String message_error = "";

        int summa = 0;

        //преобразование символов в целые числа
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                try {
                    massiveInt[i][j] = Integer.parseInt(massive[i][j]);
                    if (!dataError) {
                        summa = summa + massiveInt[i][j];
                    }
                } catch (NumberFormatException e) {
                    message_error = message_error + "\n" +
                            "Ячейка [" + i + "][" + j + "] содержит неверные данные: " + massive[i][j] + ". Невозможно преобразовать в числовой формат.";
                    dataError = true;
                }
            }
        }
        if (dataError) {
            throw new MyArrayDataException(message_error);
        }

        return summa;
    }

}

