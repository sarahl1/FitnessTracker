package ui;


import java.util.ArrayList;
import java.util.Scanner;


public class Item {
    public String category;
    public String name;
    public double price;
    private static Scanner scan = new Scanner(System.in);
    public static ArrayList<Item> items;
    public static ArrayList<Item> cart;
    public static ArrayList<Item> produce;
    public static ArrayList<Item> snacks;
    public static ArrayList<String> produce_names;
    public static ArrayList<String> snack_names;
//    private ArrayList<Item> cosmetics;
//    private ArrayList<Item> bread;
//    private ArrayList<Item> clothing;
//    private ArrayList<Item> frozen;
//    private ArrayList<Item> return;

    private static final String PRODUCE_COMMAND = "1";
    private static final String SNACKS_COMMAND = "2";
//    private static final String COSMETICS_COMMAND = "3";
//    private static final String BREAD_AND_PATRIES_COMMAND = "4";
//    private static final String CLOTHING_COMMAND = "5";
//    private static final String FROZEN_FOODS_COMMAND = "6";
//    private static final String RETURN_COMMAND = "7";

    private Item(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public static void main(String[] args) {

        makeItems();
        //add other items eventually
        addToCategory();

        introStatement();
        selectItem();
        checkOut();
    }

    public static void makeItems() {
        items = new ArrayList<>();

        Item pork_chops = new Item("Pork Chops", 14.99, "produce");
        items.add(pork_chops);
        Item milk = new Item("Milk", 4.50, "produce");
        items.add(milk);
        Item ketchup_chips = new Item("Lays Ketchup Chips", 3.85, "snacks");
        items.add(ketchup_chips);
        Item granola_bars = new Item("Nature Valley Oats and Honey Bars (6-pack)", 4.95, "snacks");
        items.add(granola_bars);
    }

    public static void introStatement() {

        System.out.println("Welcome! What are you looking for today?\n" +
                "[1] Produce \n" +
                "[2] Snacks\n" +
                "[3] Cosmetics\n" +
                "[4] Bread and Pastries \n" +
                "[5] Clothing \n" +
                "[6] Frozen Foods \n" +
                "[7] Returning an item \n");
        String num = scan.nextLine();
        if (num.equals(PRODUCE_COMMAND)) {
            produceList();
            System.out.println(produce_names);
        } else if (num.equals(SNACKS_COMMAND)) {
            snacksList();
            System.out.println(snack_names);
        } else {
            System.out.println("Not sure what you said.");
        }
    }


    private static void produceList() {
        produce_names = new ArrayList<String>();
        for (Item i : produce) {
            produce_names.add(getName(i) + " " + getPrice(i));
        }
    }

    private static void snacksList() {
        snack_names = new ArrayList<String>();
        for (Item i : snacks) {
            snack_names.add(getName(i) + " " + getPrice(i));
        }
    }

    public static String getName(Item item){
        return item.name;
    }

    public static double getPrice(Item item){
        return item.price;
    }

    private static void addToCategory() {
        produce = new ArrayList<Item>();
        snacks = new ArrayList<Item>();
        for (Item i : items) {
            if (i.category.equals("produce")) {
                produce.add(i);
            } else {
                snacks.add(i);
            }
        }
    }

    public static void selectItem(){
        System.out.println("Please enter the name of the product you wish to purchase.");
        String n = scan.nextLine();
        addToCart(n);
    }

    public static void addToCart(String item_name){
        cart = new ArrayList<Item>();
        for (Item i : items) {
            if (i.name.equals(item_name)) {
                cart.add(i);
            }
        }
    }

    public static void checkOut(){
        System.out.println("Your total comes to: \n");
        for (Item i : cart) {
            System.out.println(i.price);
        }
        System.out.println("Enter 'pay' to pay for purchase.");
        String pay = scan.nextLine();
        if (pay.equals("pay")) {
            endStatement();
        }
    }


    public static void endStatement() {
        System.out.println("Here is your receipt. Thank you for shopping!");
    }
}