package foodGenPROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class recipeGenGUI extends recipeGen {
    private JButton generateRandomMealButton;
    private JPanel  myPanel;
    private JButton filterByCategoryButton;
    private JButton filterByAreaButton;
    private JButton filterByIngredientsButton;

    public recipeGenGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateRandomMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> rand = new ArrayList<>();
                try {
                    printInstruction("/api/json/v1/1/random.php",rand);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String text = "";
                for(String in : rand){
                    text+=in;
                }
                //JOptionPane.showMessageDialog(null,text);
                JTextArea textArea = new JTextArea(20, 80);
                textArea.setText(text);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(scrollPane, scrollPane, "Enjoy!",JOptionPane.PLAIN_MESSAGE);
            }
        });
        filterByIngredientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> ingredients = new ArrayList<>();
                try {
                    fillArray("https://www.themealdb.com/api/json/v1/1/list.php?i=list", "strIngredient", ingredients);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String selectIngredient = (String) JOptionPane.showInputDialog(null, "Choose an Ingredient", "Filter by Ingredients", JOptionPane.PLAIN_MESSAGE, null, ingredients.toArray(), ingredients.toArray()[0]);

                if (selectIngredient != null) {
                    ArrayList<String> in = new ArrayList<>();
                    try {
                        String url = "/api/json/v1/1/filter.php?i="+selectIngredient;
                        getList(url, in);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    String mealID = (String) JOptionPane.showInputDialog(null, "Choose a Meal", "Filter by Ingredients", JOptionPane.PLAIN_MESSAGE, null, in.toArray(), in.toArray()[0]);
                    if (mealID != null) {
                        mealID = mealID.substring(mealID.indexOf(":") + 1);
                        String url = "/api/json/v1/1/lookup.php?i=" + mealID;
                        ArrayList<String> out = new ArrayList<>();
                        try {
                            printInstruction(url, out);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        String text = "";
                        for (String str : out) {
                            text += str;
                        }
                        JTextArea textArea = new JTextArea(20, 80);
                        textArea.setText(text);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(scrollPane, scrollPane, "Enjoy!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        filterByAreaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> area = new ArrayList<>();
                try {
                    fillArray("https://www.themealdb.com/api/json/v1/1/list.php?a=list", "strArea", area);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String selectArea = (String) JOptionPane.showInputDialog(null, "Choose an Area", "Filter by Areas", JOptionPane.PLAIN_MESSAGE, null, area.toArray(), area.toArray()[0]);

                if (selectArea != null) {
                    ArrayList<String> in = new ArrayList<>();
                    try {
                        String url = "/api/json/v1/1/filter.php?a="+selectArea;
                        getList(url, in);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    String mealID = (String) JOptionPane.showInputDialog(null, "Choose a Meal", "Filter by Areas", JOptionPane.PLAIN_MESSAGE, null, in.toArray(), in.toArray()[0]);
                    if (mealID != null) {
                        mealID = mealID.substring(mealID.indexOf(":") + 1);
                        String url = "/api/json/v1/1/lookup.php?i=" + mealID;
                        ArrayList<String> out = new ArrayList<>();
                        try {
                            printInstruction(url, out);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        String text = "";
                        for (String str : out) {
                            text += str;
                        }
                        JTextArea textArea = new JTextArea(20, 80);
                        textArea.setText(text);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(scrollPane, scrollPane, "Enjoy!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        filterByCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> category = new ArrayList<>();
                try {
                    fillArray("https://www.themealdb.com/api/json/v1/1/list.php?c=list", "strCategory", category);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                String selectCategory = (String) JOptionPane.showInputDialog(null, "Choose an Category", "Filter by Categories", JOptionPane.PLAIN_MESSAGE, null, category.toArray(), category.toArray()[0]);

                if (selectCategory != null) {
                    ArrayList<String> in = new ArrayList<>();
                    try {
                        String url = "/api/json/v1/1/filter.php?c="+selectCategory;
                        getList(url, in);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    String mealID = (String) JOptionPane.showInputDialog(null, "Choose a Meal", "Filter by Category", JOptionPane.PLAIN_MESSAGE, null, in.toArray(), in.toArray()[0]);
                    if (mealID != null) {
                        mealID = mealID.substring(mealID.indexOf(":") + 1);
                        String url = "/api/json/v1/1/lookup.php?i=" + mealID;
                        ArrayList<String> out = new ArrayList<>();
                        try {
                            printInstruction(url, out);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        String text = "";
                        for (String str : out) {
                            text += str;
                        }
                        JTextArea textArea = new JTextArea(20, 80);
                        textArea.setText(text);
                        textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(scrollPane, scrollPane, "Enjoy!", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Recipe Generator");
        frame.setContentPane(new recipeGenGUI().myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
