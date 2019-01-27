import Enums.Dimentions;
import Exceptions.MyArrayDataException;
import Exceptions.MyArraySizeException;

public class Main {

    public static void main(String[] args) {
        String[][] massive = //new String[4][4];
                {
                        {"1", "1", "1", "1"},
                        {"2", "2", "2", "2"},
                        {"3", "3", "3"},
                        {"4", "4", "4", "4"}
                };
        Application app = new Application();

        int rez;
        try {
            rez = app.CheckMassive(massive);
            System.out.println("Результат сложения элементов массива "+rez);
        } catch (MyArraySizeException e){
            System.out.println("Пожалуйста, передайте массив размерностью ["+ Dimentions.X.getValue()+"]["+Dimentions.Y.getValue()+"]");
            e.printStackTrace();
        }
        catch ( MyArrayDataException e) {
            System.out.println("Пожалуйста, заполняйте массив только теми символами, которые возможно преобразовать в числовой формат.");
            e.printStackTrace();
        }

    }
}
