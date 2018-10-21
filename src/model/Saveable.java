package model;

import java.io.IOException;
import java.util.ArrayList;

public interface Saveable {

    void saveToInput(ArrayList<Item> list) throws IOException;

    void saveToPreviousTotal() throws IOException;

    void saveToPrevious(ArrayList<Item> list) throws IOException;
}
