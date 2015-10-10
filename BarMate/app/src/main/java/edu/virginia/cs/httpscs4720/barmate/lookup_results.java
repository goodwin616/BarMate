package edu.virginia.cs.httpscs4720.barmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class lookup_results extends AppCompatActivity {

    ListView listView;
    ArrayList<Recipe> results = new ArrayList<>();
    ArrayList<String> selections = new ArrayList<>();
    boolean partial;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("The results are in!");


        setContentView(R.layout.activity_lookup_results);
        listView = (ListView) findViewById(R.id.list_results);


        if (savedInstanceState != null) {
            System.out.println("savedInstanceState != null");
            partial = savedInstanceState.getBoolean("partial");
            selections = savedInstanceState.getStringArrayList("selections");
            try {
                results = possibleRecipes(savedInstanceState.getStringArrayList("selections"), partial);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Bundle bundle = getIntent().getExtras();

            if(bundle != null) {
                partial = bundle.getBoolean("partial");

                if (bundle.getStringArrayList("selections") != null && !bundle.getStringArrayList("selections").isEmpty()) {
                    selections = bundle.getStringArrayList("selections");
                    try {
                        results = possibleRecipes(bundle.getStringArrayList("selections"), partial);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // results.add("No matches, try again?");
                }
            }
            else{
                System.out.println("bundle is null");
                finish();
            }
        }
        // Fill array of matching results
        String[] resultArray = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            resultArray[i] = results.get(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, resultArray);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Recipe selectedRecipe = results.get(position);
                if (selectedRecipe.isPartial()) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z=10&q=ABC Liquor Store");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                else {
                    Intent intent = new Intent(lookup_results.this, Good_Recipe.class);
                    intent.putExtra("name", selectedRecipe.getName());
                    ArrayList<String> passIngredients = new ArrayList<>();
                    for (int i = 0; i < selectedRecipe.getIngredients().size(); i++) {
                        passIngredients.add(selectedRecipe.getIngredients().get(i).getName());
                    }
                    intent.putStringArrayListExtra("ingredients", passIngredients);
                    startActivity(intent);
                }
            }

        });

    }


    public ArrayList<Recipe> possibleRecipes(ArrayList<String> ingredients, boolean partial) throws IOException {

        ArrayList<Recipe> recipes = new ArrayList<>();

        //Hashmap to quickly check if ingredient is present
        HashMap<String, String> possibleIngredients = new HashMap<>();
        for (String s : ingredients) {
            possibleIngredients.put(s, s);
        }

        InputStream recipeFile = getResources().openRawResource(R.raw.drinklist);
        BufferedReader reader = new BufferedReader(new InputStreamReader(recipeFile));
        String line = "";
        while ((line = reader.readLine()) != null) {
            String recipeName = line;
            ArrayList<String> itemList = new ArrayList<>();
            line = reader.readLine();
            while (line.compareTo("=") != 0) {
               //System.out.println(line + " is not =");
                itemList.add(line);
                line = reader.readLine();
            }
            //System.out.println(line + "is =");

            ArrayList<Ingredient> ingredientOfRecipe = new ArrayList<>();

            int counter = 2;
            for (String s : itemList) {
                    String[] item = s.split("@", 2);
                    String presentIngredient =  item[1] + " ~ " + item[0];
                    if (!possibleIngredients.containsKey(item[0])) {
                        counter--;
                        ingredientOfRecipe.add(new Ingredient(presentIngredient, false));
                    } else {
                        ingredientOfRecipe.add(new Ingredient(presentIngredient, true));
                    }
            }

            if (counter >= 1) {
                //Good Recipe
                if (counter > 1)
                    recipes.add(new Recipe(recipeName, ingredientOfRecipe, false));
                    //Partial Recipe
                else if (counter == 1 && partial)
                    recipeName = recipeName + " (Missing 1 ingredient!)";
                    recipes.add(new Recipe(recipeName, ingredientOfRecipe, true));
            }

        }
        return recipes;
    }


    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        System.out.println("onSaveInstanceState");
        savedInstanceState.putBoolean("partial", partial);
        savedInstanceState.putStringArrayList("selections", selections);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("onRestoreInstanceState");
        partial = savedInstanceState.getBoolean("partial");
        selections = savedInstanceState.getStringArrayList("selections");
    }




}





