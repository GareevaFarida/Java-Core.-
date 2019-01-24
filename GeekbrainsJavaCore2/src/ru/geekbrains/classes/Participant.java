package ru.geekbrains.classes;

public interface Participant {

    boolean isOnDistance();

    void run(int distance);

    void jump(int height);

    void swim(int distance);

    //добавлен код Гареева Ф.
    void printInformationAboutMember(boolean isOnDistance);

}
