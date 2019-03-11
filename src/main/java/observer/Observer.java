package observer;

import model.Item;

public interface Observer {
    String update(Item i);
    String updateRemove(Item i);
}
