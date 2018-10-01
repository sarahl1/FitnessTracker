package model;

import java.io.IOException;

public interface Load {
    void viewPrevious() throws IOException;

    void setTotal() throws IOException;

    void setFood_eaten() throws IOException;
}
