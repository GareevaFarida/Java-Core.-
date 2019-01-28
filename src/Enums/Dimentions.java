package Enums;

public enum Dimentions {
    X("Первое измерение", 4), Y("Второе измерение", 4);
    private String description;
    private int value;

    Dimentions(String description, int i) {
        this.description = description;
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
