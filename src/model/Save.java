package model;

import java.io.IOException;

public interface Save {
    void saveToOutput() throws IOException;

    void saveToInput() throws IOException;

    void saveToTotal() throws IOException;

    void saveToInputList() throws IOException;
}
