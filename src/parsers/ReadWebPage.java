package parsers;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadWebPage {
    public String inline = "";
    public ReadWebPage() throws MalformedURLException, IOException {


        URL url = new URL("https://www.themealdb.com/api/json/v1/1/random.php");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.connect();


        int responsecode = con.getResponseCode();

        if(responsecode != 200) {
            throw new RuntimeException();
        }else
        {
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                inline +=sc.nextLine();
            }
            sc.close();
        }
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(inline);
            JSONArray array = (JSONArray) obj.get("meals");
            displayMeal(array);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    private void displayMeal(JSONArray array) {
        for (Object o : array) {
            JSONObject item = (JSONObject) o;
            ArrayList<String> recipe = new ArrayList<>();
            String name = (String) item.get("strMeal");
            String type = (String) item.get("strCategory");
            String instructions = (String) item.get("strInstructions");
            recipe.add(name + "[" + type + "]");
            recipe.add(instructions);
            for (int i = 1; i <= 20; i++) {
                String ingredient = (String) item.get("strIngredient" + i);
                String measure = (String) item.get("strMeasure" + i);
                recipe.add(measure + " " + ingredient);
            }
            for (String s : recipe) {
                System.out.println(s);
            }
        }
    }


}
