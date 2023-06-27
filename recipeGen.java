package foodGenPROJECT;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//helper method to retrieve information from API call and add it to a string
class HttpUtil {
    public static String sendGet(String url) throws Exception {
        HttpURLConnection connection = null;
        try {
            URL obj = new URL(url);
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

public class recipeGen {

    //pick up components of a recipe from database "the meal" and insert them to an arraylist
    static void printInstruction(String URL, ArrayList<String> in) throws Exception {
        String apiURL = "https://www.themealdb.com" + URL;
        String response = HttpUtil.sendGet(apiURL);
        JSONObject json = new JSONObject(response);
        JSONArray mealArr = json.getJSONArray("meals");
        JSONObject meals = mealArr.getJSONObject(0);

        in.add("Meal Name: " + meals.getString("strMeal") + "\n\n");
        in.add("Dish Image: " + meals.getString("strMealThumb") + "\n\n");
        in.add("Category: " + meals.getString("strCategory") + "\n\n");
        in.add("Region: " + meals.getString("strArea") + "\n\n");
        in.add("Instruction: \n");
        String instructions = meals.getString("strInstructions").replaceAll("\\.",".\n");
        in.add(instructions + "\n\n");
        in.add("Video Instruction: " + meals.getString("strYoutube") + "\n\n");
        in.add("Ingredients List: \n");
        for (int i = 1; i <= 20; i++) {
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;
            String temp = meals.getString(ingredientKey);
            if (temp.equals("")){
                break;
            }
            if (!meals.isNull(ingredientKey) && !meals.isNull(measureKey)) {
                String ingredient = meals.getString(ingredientKey);
                String measure = meals.getString(measureKey);
                in.add(ingredient + " - " + measure + "\n");
            }
        }
        in.add("\n");
        in.add("Recipe Source: " + meals.getString("strSource") + "\n");
    }

    //locate objects using keyword from the database
    //and add them to the input arraylist in the parameter
    static void fillArray(String apiURL, String keyWord, ArrayList<String> arr) throws Exception {
        String response = HttpUtil.sendGet(apiURL);
        JSONObject json = new JSONObject(response);
        JSONArray mealArr = json.getJSONArray("meals");
        int count = mealArr.length();

        for (int i = 0; i < count; i++) {
            JSONObject object = mealArr.getJSONObject(i);
            String temp = object.getString(keyWord);
            arr.add(temp);
        }
    }

    //get list of meals from URL
    static void getList(String URL, ArrayList<String> in) throws Exception {
        String apiURL = "https://www.themealdb.com" + URL;
        String response = HttpUtil.sendGet(apiURL);
        JSONObject json = new JSONObject(response);
        JSONArray mealArr = json.getJSONArray("meals");
        int count = mealArr.length();

        for (int i = 0; i < count; i++) {
            JSONObject object = mealArr.getJSONObject(i);
            String meal = object.getString("strMeal");
            String id = object.getString("idMeal");
            in.add(meal+" - ID:"+id+"\n");
        }
    }
}