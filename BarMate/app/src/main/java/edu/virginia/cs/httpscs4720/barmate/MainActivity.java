package edu.virginia.cs.httpscs4720.barmate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Generate list View from ArrayList
        displayListView();

        checkButtonClick();

    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));
        ingredientList.add(new Ingredient("whiskey"));

        //create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.activity_main, ingredientList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Ingredient item = (Ingredient) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + item,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Ingredient> {

        private ArrayList<Ingredient> ingredientList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Ingredient> ingredientList) {
            super(context, textViewResourceId, ingredientList);
            this.ingredientList = new ArrayList<Ingredient>();
            this.ingredientList.addAll(ingredientList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.ingredient_layout, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Ingredient item = (Ingredient) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        item.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Ingredient item = ingredientList.get(position);
            holder.code.setText(" (" +  item + ")");
            holder.name.setText(item.getName());
            holder.name.setChecked(item.isSelected());
            holder.name.setTag(item);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Ingredient> ingredientList = dataAdapter.ingredientList;
                for(int i=0;i<ingredientList.size();i++){
                    Ingredient item = ingredientList.get(i);
                    if(item.isSelected()){
                        responseText.append("\n" + item.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

}