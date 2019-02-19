package parsers;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class JSONRead {

    private Reader fr;
    private int id = 100;
    private boolean healthy = false;
    HashMap<Item, ArrayList<String>> result;
    private ItemList allFood;
    private int lastID;



    public JSONRead()  {

        JSONParser parser = new JSONParser();
        try {
            fr = new FileReader("food.json");
            skip(fr);
            JSONObject obj = (JSONObject) parser.parse(fr);
            JSONArray array = (JSONArray) obj.get("Food_Display_Row");
            setAllFood(array);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: Add nutritional facts!!!!!

        private void setAllFood(JSONArray array){
            allFood = new FoodList();
            result = new HashMap<>();
            for (Object o : array) {
                JSONObject item = (JSONObject) o;

                Nutrition nutrition = new Nutrition();
                String name = (String) item.get("Display_Name");
                String id = Integer.toString(this.id);
                String calories = (String) item.get("Calories");//parse
                String saturatedFats = (String) item.get("Saturated_Fats");
                String addedSugar = (String) item.get("Added_Sugars");
                String portionAmount = (String) item.get("Portion_Amount");
                String portionDisplayName = (String) item.get("Portion_Display_Name");
                String grains = (String) item.get("Grains"); //ounces
                String wholeGrains = (String) item.get("Whole_Grains");
                String vegetables = (String) item.get("Vegetables"); //cups
                String fruits = (String) item.get("Fruits"); //cups
                String milk = (String) item.get("Milk"); //cup
                String meats = (String) item.get("Meats"); //ounces
                String soy = (String) item.get("Soy"); //ounces
                String oils = (String) item.get("Oils"); //grams
                String solid_fats = (String) item.get("Solid_Fats"); //grams


                this.id++;
                lastID = this.id;
                if (Double.parseDouble(saturatedFats) <= 5.0 && Double.parseDouble(addedSugar) <= 60.00) {
                    healthy = true;
                }

                Item newItem = new Food(id, name,
                        parseInt(String.valueOf(Math.round(Double.parseDouble(calories)))), healthy);
                newItem.setNutriFacts(nutrition);
                if (!result.containsKey(newItem)){
                    result.put(newItem, null);
                }

                healthy = false;

            }
            this.allFood.setLog(result);
        }

        public ItemList getAllFood(){
            return allFood;
        }


        public void skip(Reader reader) throws IOException
        {
            char[] possibleBOM = new char[1];
            reader.read(possibleBOM);

            if (possibleBOM[0] != '\ufeff')
            {
                reader.reset();
            }
        }

}


