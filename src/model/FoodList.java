package model;


public class FoodList extends ItemList{

    @Override
    public void addItem(Item i){
        if (!getLog().containsKey(i)){
            getLog().put(i, i.nutriFacts.getNutriFacts());
            i.setList(this);
        }
    }

    @Override
    public void removeItem(Item i){
        if (getLog().containsKey(i)){
            getLog().remove(i);
            i.setList(null);
        }
    }


}
