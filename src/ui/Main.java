package ui;

import model.Food;

public class Main {

    public static void main(String[] args) {
        Food f = new Food(null, null, 0);

        f.makeFood();
        f.printFood();
        System.out.println("Please select which item to add.");
        f.selectFood();
        f.options();
        //f.summary();
        f.exit();
    }
}
