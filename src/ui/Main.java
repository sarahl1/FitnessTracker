package ui;

import exceptions.*;
import model.*;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    protected static Scanner scan = new Scanner(System.in);
    private static Item f;
    private static Item e;
    private static Item i;
    private static Nutrition n;



    public static void main(String[] args) throws IOException {
        f = new Food(null, null, 0, false);
        e = new Exercise(null, null, 0);
        i = new Food(null, null, 0, false);
        n = new Nutrition();
        i.makeItems();
        try {
            options();
        } catch (InputException e1) {
            System.out.println(e1.getMessage());
        }
        e.saveToInput(i.getExerciseDone());
        f.saveToInput(i.getFoodEaten());
        e.saveToPrevious(i.getExerciseDone());
        f.saveToPrevious(i.getFoodEaten());
        i.saveToPreviousTotal();
        f.exit();
    }

    //EFFECTS: prints itemOptions and calls corresponding methods
    public static void options() throws NotAnOptionException {
        System.out.println("[1] Add Items \n" +
                "[2] Remove Items \n" +
                "[3] View previous day \n" +
                "[4] Resume previous day \n" +
                "[5] Exit");
        String input = scan.nextLine();
        switch (input) {
            case "1":
                try {
                    i.optionAdd();
                } catch (InputException e1) {
                    System.out.println(e1.getMessage());
                } finally {
                    options();
                }
                break;
            case "2":
                try {
                    i.optionRemove();
                } catch (NotAnItemException e1) {
                    System.out.println(e1.getMessage());
                } finally {
                    options();
                }
                break;
            case "3":
                try{
                    i.optionViewPrevious();
                } catch (IOException e) {
                    System.out.println("Previous log not found");
                } catch (NoPreviousException e1) {
                    System.out.println(e1.getMessage());
                } finally {
                    options();
                }
                break;
            case "4":
                try{
                    i.optionResume();
                } catch (IOException e) {
                    System.out.println("Unable to resume previous log");
                } catch (NoPreviousException e1) {
                    System.out.println(e1.getMessage());
                } finally {
                    options();
                }
                break;
            case "5":
                i.optionExit();
                break;
            default:
                throw new NotAnOptionException("Not an option!");
        }
    }


}


