package model;

import exceptions.HighTotalException;
import exceptions.LowTotalException;

public interface CalorieCounter {

    //MODIFIES: this
    //EFFECTS: adds number of calories in the item to total
    void addCal(Item c) throws HighTotalException, LowTotalException;

    //MODIFIES: this
    //EFFECTS: removes number of calories in the item from total
    void removeCal(Item f) throws LowTotalException, HighTotalException;

}
