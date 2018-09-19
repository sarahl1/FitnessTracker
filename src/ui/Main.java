package ui;



public class Main {

    public static void main(String[] args) {

        Item i = new Item(null, 0, null);

        i.makeItems();
        i.addToCategory();

        i.introStatement();
        //TODO i.isValid();
    }
}
