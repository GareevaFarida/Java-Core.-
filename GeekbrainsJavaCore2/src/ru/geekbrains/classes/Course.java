package ru.geekbrains.classes;

import ru.geekbrains.classes.obstacles.Cross;
import ru.geekbrains.classes.obstacles.Obstacle;
import ru.geekbrains.classes.obstacles.Wall;
import ru.geekbrains.classes.obstacles.Water;

public class Course {
    private Obstacle[] obstacles;

    //конструктор с заполненной полосой препятствий
    public Course(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    //препятствия по умолчанию
    public Course() {
        obstacles = new Obstacle[]{
                new Cross(7),
                new Wall(4),
                new Water(10)
        };
    }

    public void doIt(Team team) {
        if (team == null) {
            System.out.println("Не задана команда для прохода препятствий");
            return;
        }
        Participant[] members = team.getMembers();
        if (members.length == 0) {
            System.out.println("Команда " + team.getName() + " не содержит участников");
            return;
        }
        for (Participant participant : members) {
            if (!participant.isOnDistance()) {
                break;
            }
            for (Obstacle obstacle : obstacles) {
                obstacle.doIt(participant);
            }
        }
    }
}
