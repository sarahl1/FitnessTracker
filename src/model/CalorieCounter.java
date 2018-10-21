package model;

import exceptions.HighTotalException;
import exceptions.LowTotalException;

public interface CalorieCounter {

    void addCal(CalorieCounter c) throws HighTotalException, LowTotalException;

    void removeCal(CalorieCounter f) throws LowTotalException, HighTotalException;

    int getCalories();
}
