package model;

import java.io.IOException;
import java.util.ArrayList;

public interface Saveable {

    void saveToInput(ItemDone list) throws IOException;

    void saveToPreviousTotal() throws IOException;

    void saveToPrevious(ItemDone list) throws IOException;
}
