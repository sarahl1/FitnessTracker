package ui;

import exceptions.*;
import model.Exercise;
import model.Food;
import model.Item;
import model.Nutrition;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    protected static Scanner scan = new Scanner(System.in);
    private static Item f;
    private static Item e;
    private static Item i;
    private static Nutrition n;
    private static MainMenu mm;


    public static void main(String[] args) throws IOException {
        f = new Food(null, null, 0, false); // dummy object used to save input uniquely
        e = new Exercise(null, null, 0);           // dummy object used to save input uniquely
        i = new Food(null, null, 0, false); // dummy object used to invoke save methods
        mm = new MainMenu();
        n = new Nutrition();
        i.makeItems();
        mm.setItemLog(i.il);
        try {
            mm.run(args);
        } catch (RuntimeException e) {
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
                } catch (HighTotalException e1) {
                    System.out.println(e1.getMessage());
                } catch (LowTotalException e1) {
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
                try {
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
                try {
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


