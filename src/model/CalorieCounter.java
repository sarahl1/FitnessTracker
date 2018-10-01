package model;

public interface CalorieCounter {

    void addCal(CalorieCounter c);

    int getCalories();

    void removeCal(CalorieCounter f);
}
