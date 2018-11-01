package model;

import java.util.ArrayList;

public class Nutrition {
    private ArrayList<String> nutriFacts;
    public Nutrition(){
        nutriFacts = new ArrayList<>();
    }

    public ArrayList<String> getNutriFacts(){
        return nutriFacts;
    }

    public void addFact(String s){
        this.nutriFacts.add(s);
    }

    public void setNutriFacts( Item i){
        switch (i.getName()){
            case "Apple":
                i.nutriFacts.addFact("Apples are free of fat, sodium and cholesterol.");
                i.nutriFacts.addFact("A medium-sized apple contains 4 grams of dietary fiber, which is 17% of the daily recommended value.");
                i.setNutriFacts(this);
                break;
            case "Orange":
                i.nutriFacts.addFact("Oranges are a good source of several vitamins and minerals, especially vitamin C, thiamin, folate and potassium. ");
                i.nutriFacts.addFact("An excellent source of vitamin C.");
                i.setNutriFacts(this);
                break;
            case "Celery":
                i.nutriFacts.addFact("Celery is a good source of vitamin B2, copper, vitamin C, vitamin B6, calcium, phosphorus, magnesium and vitamin A.");
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Hamburger":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Cheeseburger":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Pepperoni Pizza":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Hawaiian Pizza":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Veggie Pizza":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Jog":
                i.nutriFacts.addFact("Jogging is fun :3");
                i.setNutriFacts(this);
                break;
            case "Run":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Swim":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Basketball":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Hockey":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Pilates":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            case "Yoga":
                i.nutriFacts.addFact("");
                i.setNutriFacts(this);
                break;
            default:
                i.nutriFacts.addFact("There is currently no information on this item.");
                break;
        }
    }

}
