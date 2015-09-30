package edu.virginia.cs.httpscs4720.barmate;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Good_Recipe extends AppCompatActivity {

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good__recipe);

        setContentView(R.layout.activity_lookup_results);
        listView = (ListView) findViewById(R.id.ingredientstToDisplay);

        Bundle bundle = getIntent().getExtras();
        String drinkName = bundle.getString("name");
        ArrayList<String> listOfIngredients = bundle.getStringArrayList("ingredients");

        String[] resultArray = new String[listOfIngredients.size()];

        for (int i = 0; i < listOfIngredients.size(); i++) {
            resultArray[i] = listOfIngredients.get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, resultArray);

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_good__recipe, menu);
        return true;
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
