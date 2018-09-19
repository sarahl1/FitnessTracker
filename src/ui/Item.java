package ui;


import java.util.ArrayList;
import java.util.Scanner;


public class Item {
    public String category;
    public String name;
    public double price;
    public double total;
    private Scanner scan = new Scanner(System.in);
    public ArrayList<Item> items;
    public ArrayList<Item> cart;
    public ArrayList<Item> produce;
    public ArrayList<Item> snacks;
    public ArrayList<String> produce_names;
    public ArrayList<String> snack_names;
//    private ArrayList<Item> cosmetics;
//    private ArrayList<Item> bread;
//    private ArrayList<Item> clothing;
//    private ArrayList<Item> frozen;
//    private ArrayList<Item> return;

    private final String PRODUCE_COMMAND = "1";
    private final String SNACKS_COMMAND = "2";
//    private static final String COSMETICS_COMMAND = "3";
//    private static final String BREAD_AND_PATRIES_COMMAND = "4";
//    private static final String CLOTHING_COMMAND = "5";
//    private static final String FROZEN_FOODS_COMMAND = "6";
//    private static final String RETURN_COMMAND = "7";


    public Item(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        cart = new ArrayList<>();
    }


    public void introStatement() {
        System.out.println("What are you looking for today?\n" +
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
            selectItem();
        } else if (num.equals(SNACKS_COMMAND)) {
            snacksList();
            System.out.println(snack_names);
            selectItem();
        } else {
            System.out.println("Not sure what you said.");
            options();
        }
    }

    public void makeItems() {
        items = new ArrayList<>();

        Item pork_chops = new Item("Pork Chops", 14.99, "produce");
        Item milk = new Item("Milk", 4.50, "produce");
        Item ketchup_chips = new Item("Lays Ketchup Chips", 3.85, "snacks");
        Item granola_bars = new Item("Nature Valley Oats and Honey Bars (6-pack)", 4.95, "snacks");


        items.add(pork_chops);
        items.add(milk);
        items.add(ketchup_chips);
        items.add(granola_bars);
    }


    private void produceList() {
        produce_names = new ArrayList<>();
        for (Item i : produce) {
            produce_names.add(getName(i) + " " + getPrice(i));
        }
    }

    private void snacksList() {
        snack_names = new ArrayList<>();
        for (Item i : snacks) {
            snack_names.add(getName(i) + " " + getPrice(i));
        }
    }

    private String getName(Item item) {
        return item.name;
    }

    private double getPrice(Item item) {
        return item.price;
    }

    public void addToCategory() {
        produce = new ArrayList<>();
        snacks = new ArrayList<>();
        for (Item i : items) {
            if (i.category.equals("produce")) {
                produce.add(i);
            } else {
                snacks.add(i);
            }
        }
    }

    public void selectItem() {
        System.out.println("Please enter the name of the product you wish to purchase.");
        String n = scan.nextLine();
        addToCart(n);
    }

    public void addToCart(String item_name) {
        for (Item i : items) {
            if (i.name.equals(item_name)) {
                cart.add(i);
                total = i.price + total;
            }
        }
        options();
    }

    public void options() {
        System.out.println("[1] Continue shopping \n" +
                "[2] View cart \n" +
                "[3] Check out \n" +
                "[4] Exit ");
        String choice = scan.nextLine();
        switch (choice) {
            case "1":
                introStatement();
                break;
            case "2":
                viewCart();
                break;
            case "3":
                checkOut();
                break;
            case "4":
                endStatement();
                break;

        }
    }

    public void viewCart() {
        System.out.println("Cart: \n");
        for (Item i : cart) {
            System.out.println(i.name + ": " + i.price);
        }
        System.out.println("Your total comes to: " + total);
        editCart();
        options();
    }

    private void editCart(){
        System.out.println("Enter R to remove an item, O to view options");
        String remove = scan.nextLine();

        if (remove.equals("R")){
            System.out.println("Enter the name of the item to remove");
            String item = scan.nextLine();
            for (Item i : cart){
                if (i.name.equals(item)){
                    cart.remove(i);
                    total = total - i.price;
                }
            }
        }
    }


    private void checkOut() {
        System.out.println("Enter 'pay' to pay for purchase.");
        String paid = scan.nextLine();
        if (paid.equals("pay")) {
            endStatement();
        }
    }


    public void endStatement() {
        System.out.println("Thank you for shopping!");
    }
}