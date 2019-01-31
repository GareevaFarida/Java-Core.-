import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhoneDictionary {

    private Map<String, ArrayList<String>> spr = new HashMap<>();

    public void add(String family,String number) {

        ArrayList<String> arr_phones;//список телефоннных номеров
        if (spr.containsKey(family)){
            arr_phones = spr.get(family);
        }else arr_phones = new ArrayList<>();
        arr_phones.add(number);

        spr.put(family,arr_phones);
    }

    public void get(String family) {

        if (!spr.containsKey(family)){
            System.out.printf("\nК сожалению, в справочнике нет данных о людях с фамилией %s.",family);
            return;
        }
        ArrayList<String> numbers = spr.get(family);
        System.out.printf("\n %s: ",family);
        for (int i = 0;i<numbers.size();i++) {
            String znak = i==numbers.size()-1?".":",";
            System.out.print(numbers.get(i)+znak);
        }
    }
}
