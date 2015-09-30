package edu.virginia.cs.httpscs4720.barmate;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.TextView;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class lookup_results extends AppCompatActivity {

    ListView listView;

    ArrayList<String> partialRecipes = new ArrayList<>();
    ArrayList<Recipe> results = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("The results are in!");


        setContentView(R.layout.activity_lookup_results);
        listView = (ListView) findViewById(R.id.list_results);

        Bundle bundle = getIntent().getExtras();
        boolean partial = bundle.getBoolean("partial");

        if (bundle.getStringArrayList("selections") != null && !bundle.getStringArrayList("selections").isEmpty()) {
            try {
                results = possibleRecipes(bundle.getStringArrayList("selections"), partial);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // results.add("No matches, try again?");
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






//                if (partialRecipes.contains(listView.getItemAtPosition(position))) {
//                    Uri gmmIntentUri = Uri.parse("geo:0,0?z=10&q=ABC Liquor Store");
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//                    startActivity(mapIntent);
//                }

//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
            }

        });

        TextView gpsLat = (TextView) findViewById(R.id.gpsLocationLatitude);
        TextView gpsLong = (TextView) findViewById(R.id.gpsLocationLongitude);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        gpsLat.setText("Your latitude is " + lastKnownLocation.getLatitude());
        gpsLong.setText("Your longitude is " + lastKnownLocation.getLongitude());


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
        String line = reader.readLine();
        while (line != null) {
            String recipeName = line;
            ArrayList<String> itemList = new ArrayList<>();
            line = reader.readLine();
            while (line.compareTo("=") != 0) {
                itemList.add(line);
                line = reader.readLine();
            }

            ArrayList<Ingredient> ingredientOfRecipe = new ArrayList<>();

            int counter = 2;
            for (String s : itemList) {
                String[] item = s.split("@", 2);
                String presentIngredient = item[1] + " ~ " + item[0];


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
                    recipes.add(new Recipe(recipeName, ingredientOfRecipe, true));
            }

        }
        return recipes;
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





}





