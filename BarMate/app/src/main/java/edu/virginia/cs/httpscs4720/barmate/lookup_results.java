package edu.virginia.cs.httpscs4720.barmate;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class lookup_results extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> partialRecipes = new ArrayList<>();

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

                if(partialRecipes.contains((String) listView.getItemAtPosition(position))){
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z=10&q=ABC Liquor Store");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }

//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
            }

        });

        TextView gpsLat = (TextView)findViewById(R.id.gpsLocationLatitude);
        TextView gpsLong = (TextView)findViewById(R.id.gpsLocationLongitude);


        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        gpsLat.setText("Your latitude is " + lastKnownLocation.getLatitude());
        gpsLong.setText("Your longitude is " + lastKnownLocation.getLongitude());



    }


    public ArrayList<String> possibleRecipes(ArrayList<String> ingredients, boolean partial) {
        String[] whiskeyandcoke = {"Whiskey", "Coca Cola"};
        String[] jackandsprite =  {"Whiskey", "Sprite", "Lime"};
        String[] names = {"Whiskey and Coke", "Jack and Sprite"};
        ArrayList<String[]> recipes = new ArrayList<>();
        partialRecipes = new ArrayList<>();
        ArrayList<String> possibleRecipes = new ArrayList<>();
        recipes.add(whiskeyandcoke);
        recipes.add(jackandsprite);

        for (int i = 0; i < recipes.size(); i++) {
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





