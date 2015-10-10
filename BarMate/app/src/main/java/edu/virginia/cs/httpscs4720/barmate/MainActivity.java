package edu.virginia.cs.httpscs4720.barmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;
    boolean partialCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            displayListView();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getActionBar().hide();
        checkButtonClick();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void displayListView() throws IOException {

        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        InputStream ingredientFile = getResources().openRawResource(R.raw.ingredients);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ingredientFile));
        String line  = "";
        while ((line = reader.readLine()) != null) {
            ingredientList.add(new Ingredient(line));
       }
        dataAdapter = new MyCustomAdapter(this,
                R.layout.activity_main, ingredientList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(dataAdapter);

//        listView.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // When clicked, show a toast with the TextView text
//                Ingredient item = (Ingredient) parent.getItemAtPosition(position);
////                Toast.makeText(getApplicationContext(),
////                        "Clicked on Row: " + item,
////                        Toast.LENGTH_LONG).show();
//            }
//        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Ingredient> {

        private ArrayList<Ingredient> ingredientList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Ingredient> ingredientList) {
            super(context, textViewResourceId, ingredientList);
            this.ingredientList = new ArrayList<>();
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
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.ingredient_layout, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Ingredient item = (Ingredient) cb.getTag();
                        item.setSelected(cb.isChecked());
                    }
                });

                holder.code.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        TextView cb = (TextView) v;
//                        Ingredient item = (Ingredient) cb.getTag();
//                        item.setSelected(cb.isChecked());
                    }
                });

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Ingredient item = ingredientList.get(position);
            holder.code.setText("");
            holder.name.setText(item.getName());
            holder.name.setChecked(item.isSelected());
            holder.name.setTag(item);

            return convertView;

        }

    }

    public void onCheckboxClicked(View view) {
        partialCheck = ((CheckBox) view).isChecked();
    }

    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, lookup_results.class);


                ArrayList<Ingredient> ingredientList = dataAdapter.ingredientList;
                ArrayList<String> selections = new ArrayList<>();


                for (int i = 0; i < ingredientList.size(); i++) {
                    Ingredient item = ingredientList.get(i);
                    if (item.isSelected()) {
                        selections.add(item.getName());
                    }
                }

                intent.putStringArrayListExtra("selections", selections);
                intent.putExtra("partial", partialCheck);


                startActivity(intent);
            }
        });

    }

}