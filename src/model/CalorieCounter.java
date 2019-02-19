package model;

import exceptions.HighTotalException;
import exceptions.LowTotalException;

public interface CalorieCounter {

    void addCal(Item c) throws HighTotalException, LowTotalException;

    void removeCal(Item f) throws LowTotalException, HighTotalException;

}
