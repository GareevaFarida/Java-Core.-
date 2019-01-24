package ru.geekbrains.classes;

import ru.geekbrains.classes.animals.Cat;
import ru.geekbrains.classes.animals.Dog;

public class Team {
    private String name;
    private Participant[] members;

    public Team(String name, Participant[] members) {
        this.name = name;
        this.members = members;
    }

    public Team(String name) {
        this.name = name;
        this.members = new Participant[]{
                new Dog("Бобик",20,10,15),
                new Cat("Тёма",15,15,0),
                new Robot("Электроник",100,50,100),
                new Dog("Барбос",19,8,12)

        };
    }

    public String getName() {
        return name;
    }

    public Participant[] getMembers() {
        return members;
    }

    //метод выводит данные обо всех участниках команды
    public void informationAboutAllMembers(){
        if (members.length ==0){
            System.out.println("Команда "+name+ "не содержит участников.");
            return;
        }

        System.out.println("Участники команды "+name+":");
        for (Participant el:members){
            el.printInformationAboutMember(false);
        }
    }

    //метод выводит данные только об участниках команды, успешно преодолевших полосу препятствий
    public void informationAboutMembersGetFinishSuccessfully(){
        System.out.println("Участники команды "+name+", успешно дошедшие до финиша:");
        int num = 0;
        for (Participant el:members){
            el.printInformationAboutMember(true);
            num++;
        }
        if (num==0){
            System.out.println("К сожалению, никто из членов команды "+name+" не смог преодолеть полосу препятствий :(");
        }
    }
}
