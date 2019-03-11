package model;

import java.util.ArrayList;

public class Nutrition {
    private ArrayList<String> nutriFacts;

    public Nutrition() {
        nutriFacts = new ArrayList<>();
    }

    public ArrayList<String> getNutriFacts() {
        return nutriFacts;
    }

    public void addFact(String s) {
        this.nutriFacts.add(s);
    }

    public void setNutriFacts(Item i, ArrayList<String> facts) {
        for (String s : facts) {

            i.nutriFacts.addFact(s);

        }

    }

}
