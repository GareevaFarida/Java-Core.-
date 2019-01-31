import java.util.HashMap;

public class Application {

    public String[] mass1 = {
            "Иванов",
            "Петров",
            "Сидоров",
            "Кузнецов",
            "Попов",
            "Орлов",
            "Воробьев",
            "Стриженов",
            "Солдатов",
            "Журавлев",
            "Зайцев",
            "Медведев",
            "Волков",
            "Козлов",
            "Баранов",
            "Иванов",
            "Петров",
            "Кузнецов",
            "Лопатин",
            "Иванов"
    };

    public void Task1() {

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int value;
        for (String el : mass1) {
            //ищем, есть ли уже элемент с таким ключом в коллекции.
            //если найден, значение элемента увеличиваем на 1
            //иначе это первое вхождение, устанавливаем значение в 1
            value = (map.get(el) == null ? 0 : map.get(el)) + 1;
            map.put(el, value);
        }
        System.out.printf("Размер полученного map: %d элемента, размер исходного массива: %d элемента.", map.size(), mass1.length);

        for (HashMap.Entry<String, Integer> o : map.entrySet()) {
            System.out.printf("\nСлово %s встречается %d раз(а)", o.getKey(), o.getValue());
        }
    }

    public void Task2(){

        PhoneDictionary dictionary = new PhoneDictionary();
        dictionary.add("Иванов","8-910-111-11-11");
        dictionary.add("Петров","8-915-222-22-22");
        dictionary.add("Сидоров","8-917-333-33-33");
        dictionary.add("Иванов","8-910-777-11-11");
        dictionary.add("Кузнецов","8-985-444-44-44");
        dictionary.add("Шевченко","8-910-555-55-55");
        dictionary.add("Иванов","8-910-888-11-11");

        dictionary.get("Иванов");
        dictionary.get("Сидоров");
        dictionary.get("Куценко");
    }
}
