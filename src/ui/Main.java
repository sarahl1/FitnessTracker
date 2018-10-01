package ui;

import model.Food;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Food f = new Food(null, null, 0);
        f.makeFood();
        f.options();
        f.saveToOutput();
        f.saveToInput();
        f.saveToInputList();
        f.saveToTotal();
        f.exit();
    }



//    public static ArrayList<String> splitOnSpace(String line){
//        String[] splits = line.split(" ");
//        return new ArrayList<>(Arrays.asList(splits));
//    }

}