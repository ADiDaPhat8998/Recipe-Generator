import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;


public class recipeGen {

    //pick up component of a recipe from database "the meal" and insert to an arraylist
    static int counter = 0;
    static void printInstruction(String link, ArrayList<String> in) throws IOException {
        counter = 0;
        URL url = new URL(link);
        URLConnection connection = url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        String line = "";
        String csvSplitBy = ",\"";
        ArrayList<String> ingredientArr = new ArrayList<>();
        int counter = 0;
        BufferedReader br = new BufferedReader(input);

        //format data from the database and locate keywords to identify the components of the recipe
        try {
            while ((line = br.readLine()) != null) {
                String[] room = line.split(csvSplitBy);
                for (String out : room){
                    out = out.replace("\\r","");
                    out = out.replace("\\n","");
                    out = out.replace("u2013","'");
                    out = out.replace("u2018","'");
                    out = out.replace("u2019","'");
                    out = out.replace("u00e9","'");
                    out = out.replace("\\","");
                    if(out.contains("strMeal") && !out.contains("strMealThumb")){
                        out = out.substring(10,out.length()-1);
                        in.add("Meal Name: "+out+"\n\n");
                    }
                    else if(out.contains("strMealThumb")){
                        out = out.substring(15,out.length()-1);
                        in.add("Dish Image: "+out+"\n\n");
                    }
                    else if(out.contains("strCategory")){
                        out = out.substring(14,out.length()-1);
                        in.add("Category: "+out+"\n\n");
                    }
                    else if(out.contains("strArea")){
                        out = out.substring(10,out.length()-1);
                        in.add("Region: "+out+"\n\n");
                    }
                    else if(out.contains("strInstructions")){
                        out = out.substring(18,out.length()-1);
                        out = out.replace(".",".\n");
                        in.add("Instruction: \n"+out+"\n");
                    }
                    else if(out.contains("strYoutube")){
                        out = out.substring(13,out.length()-1);
                        in.add("Video Instruction: "+out+"\n\n");
                    }
                    else if(out.contains("strIngredient")) {
                        if (out.substring(out.length() - 2).equals("\"\"")) {
                            continue;
                        }
                        else {
                            out = out.substring(17, out.length() - 1);
                            out = out.replace("\"","");
                            ingredientArr.add(out);
                        }
                    }
                    else if(out.contains("strMeasure")) {
                        if (counter > ingredientArr.size()-1) {
                            continue;
                        } else {
                            out = out.substring(14, out.length() - 1);
                            out = out.replace("\"","");
                            if (counter == 0) {
                                in.add("Ingredients List: \n");
                            }
                            in.add(ingredientArr.get(counter) + " - " + out+"\n");
                            counter++;
                        }

                    }
                    else if(out.contains("strSource")){
                        out = out.substring(12,out.length()-1);
                        in.add("\nRecipe Source: "+out+"\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //locate each comma-separated values (csv) from the database then identify the keywords of interest
    //and add them to the input arraylist in the parameter
    static void fillArray(String url, String keyWord, ArrayList<String> arr, int subStart, int subEnd) throws IOException {
        URL link = new URL(url);
        URLConnection connection = link.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        ArrayList<String> ingredientArr = new ArrayList<>();
        int counter = 0;
        br = new BufferedReader(input);

        try {
            while ((line = br.readLine()) != null) {
                String[] room = line.split(csvSplitBy);
                for (String out : room){
                    out = out.replace("\\r","");
                    out = out.replace("\\n","");
                    out = out.replace("u2013","-");
                    out = out.replace("\\","");
                    if(out.contains(keyWord)){
                        out = out.substring(subStart,out.length()-subEnd);
                        out = out.replace("strArea\":\"","");
                        out = out.replace("ategory\":\"","");
                        out = out.replace("\"}","");
                        arr.add(out);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //filter the recipes by their ingredients then add the recipes to an input arraylist
    static void filterByIngredients(String ingredient, ArrayList<String> in) throws IOException {
        URL url = new URL("https://www.themealdb.com/api/json/v1/1/filter.php?i="+ingredient);
        URLConnection connection = url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        ArrayList<String> ingredientArr = new ArrayList<>();
        br = new BufferedReader(input);
        try {
            while ((line = br.readLine()) != null) {
                String temp = null;
                String[] room = line.split(csvSplitBy);
                for (String out : room){
                    out = out.replace("\\r","");
                    out = out.replace("\\n","");
                    out = out.replace("u2013","-");
                    out = out.replace("\\","");
                    if(out.contains("strMeal") && !out.contains("strMealThumb")){
                        out = out.substring(12,out.length()-1);
                        out = out.replace("strMeal\":\"","");
                        temp = out;
                    }
                    if(out.contains("idMeal")){
                        out = out.substring(10,out.length()-2);
                        out = out.replace("\"}","");
                        out = out.replace("]","");
                        in.add(temp+" - ID:"+out+"\n");
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //filter the recipes by their areas then add the recipes to an input arraylist
    static void filterByArea(String area, ArrayList<String> in) throws IOException {
        URL url = new URL("https://www.themealdb.com/api/json/v1/1/filter.php?a="+area);
        URLConnection connection = url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        ArrayList<String> ingredientArr = new ArrayList<>();
        br = new BufferedReader(input);
        try {
            while ((line = br.readLine()) != null) {
                String temp = null;
                String[] room = line.split(csvSplitBy);
                for (String out : room){
                    out = out.replace("\\r","");
                    out = out.replace("\\n","");
                    out = out.replace("u2013","-");
                    out = out.replace("\\","");
                    if(out.contains("strMeal") && !out.contains("strMealThumb")){
                        out = out.substring(12,out.length()-1);
                        out = out.replace("strMeal\":\"","");
                        temp = out;
                    }
                    if(out.contains("idMeal")){
                        out = out.substring(10,out.length()-2);
                        out = out.replace("\"}","");
                        in.add(temp+" - ID:"+out+"\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //filter the recipes by their category then add the recipes to an input arraylist
    static void filterByCategory(String category, ArrayList<String> in) throws IOException {
        URL url = new URL("https://www.themealdb.com/api/json/v1/1/filter.php?c="+category);
        URLConnection connection = url.openConnection();
        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        ArrayList<String> ingredientArr = new ArrayList<>();
        br = new BufferedReader(input);
        try {
            while ((line = br.readLine()) != null) {
                String temp = null;
                String[] room = line.split(csvSplitBy);
                for (String out : room){
                    out = out.replace("\\r","");
                    out = out.replace("\\n","");
                    out = out.replace("u2013","-");
                    out = out.replace("\\","");
                    if(out.contains("strMeal") && !out.contains("strMealThumb")){
                        out = out.substring(12,out.length()-1);
                        out = out.replace("strMeal\":\"","");
                        temp = out;
                    }
                    if(out.contains("idMeal")){
                        out = out.substring(10,out.length()-2);
                        out = out.replace("\"}","");
                        in.add(temp+" - ID:"+out+"\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}