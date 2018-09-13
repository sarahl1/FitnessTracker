package ui;

//import java.util.Scanner;

public class Main {
    //Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        introStatement();
        endStatement();

    }

    public static void introStatement(){
        System.out.println("Welcome to your scheduler. " +
                "Enter 'add' to add a new item. " +
                "Enter 'edit' to edit current items." +
                "Enter 'view' to view your schedule.");
    }


    public static void endStatement(){
        System.out.println("Changes saved.");
    }
}

