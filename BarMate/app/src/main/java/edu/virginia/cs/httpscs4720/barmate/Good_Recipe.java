package edu.virginia.cs.httpscs4720.barmate;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Good_Recipe extends Activity {

    ListView listView;
    TextView nameOfDrink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good__recipe);
        listView = (ListView) findViewById(R.id.ingredientstToDisplay);
        nameOfDrink = (TextView) findViewById(R.id.drinkName);


        Bundle bundle = getIntent().getExtras();
        String drinkName = bundle.getString("name");
        nameOfDrink.setText(drinkName);

        ArrayList<String> listOfIngredients = bundle.getStringArrayList("ingredients");

        String[] resultArray = new String[listOfIngredients.size()];

        for (int i = 0; i < listOfIngredients.size(); i++) {
            resultArray[i] = listOfIngredients.get(i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, resultArray);

        listView.setAdapter(adapter);
        setButton(bundle.getBoolean("partial"));



    }

    public void setButton(boolean partial) {
        Button myButton = (Button) findViewById(R.id.optionButton);
        if (partial) {
            myButton.setText("Find a nearby ABC Store");
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?z=10&q=ABC Liquor Store");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

        }
        else {
            myButton.setText("Take a photo of your cocktail!");
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Take a photo

                }
            });
//            Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
//            imagesFolder.mkdirs();
//
//            File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
//            Uri uriSavedImage = Uri.fromFile(image);
//
//            imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//            startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
