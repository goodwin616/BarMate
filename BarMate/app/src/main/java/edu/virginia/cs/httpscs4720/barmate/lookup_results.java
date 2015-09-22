package edu.virginia.cs.httpscs4720.barmate;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class lookup_results extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("The results are in!");


        setContentView(R.layout.activity_lookup_results);
        listView = (ListView) findViewById(R.id.list_results);

        Bundle bundle = getIntent().getExtras();
        boolean partial = bundle.getBoolean("partial");

        ArrayList<String> results = new ArrayList<>();
        if (bundle.getStringArrayList("selections") != null && !bundle.getStringArrayList("selections").isEmpty()) {
            results = possibleRecipes(bundle.getStringArrayList("selections"), partial);
        }
        else {
            results.add("No matches, try again?");

        }

        // Fill array of matching results
        String[] resultArray = new String[results.size()];
        resultArray = results.toArray(resultArray);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, resultArray);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);

//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
            }

        });
    }


    public ArrayList<String> possibleRecipes(ArrayList<String> ingredients, boolean partial) {
        String[] whiskeyandcoke = {"Whiskey", "Coca Cola"};
        String[] jackandsprite =  {"Whiskey", "Sprite", "Lime"};
        String[] names = {"Whiskey and Coke", "Jack and Sprite"};
        ArrayList<String[]> recipes = new ArrayList<>();
        ArrayList<String> partialRecipes = new ArrayList<>();
        ArrayList<String> possibleRecipes = new ArrayList<>();
        recipes.add(whiskeyandcoke);
        recipes.add(jackandsprite);

        for (int i = 0; i < recipes.size(); i++) {
            boolean validRecipe = true;
            String[] recipe = recipes.get(i);
            int counter = 2;

            for (int j = 0; j < recipes.get(i).length; j++) {

                if (ingredients.contains(recipe[j])) {
                }
                else{
                    counter--;
                }

            }

            if(counter > 1){
                String nameToAdd = names[i];
                possibleRecipes.add(nameToAdd);
            }
            else if(counter == 1) {
                String nameToAdd = names[i];
                partialRecipes.add(nameToAdd);
            }

        }

        if(partial){
            possibleRecipes.addAll(partialRecipes);
            return possibleRecipes;
        }
        else{
            return possibleRecipes;
        }

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





