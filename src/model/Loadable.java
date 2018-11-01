package model;

import exceptions.NoPreviousException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public interface Loadable {

    void viewPrevious() throws IOException, NoPreviousException;

    void setTotal() throws IOException, NoPreviousException;

    void setDone(ItemList all, ItemDone done) throws IOException, NoPreviousException;
}
