package com.anshmidt.oneline_list_entry_selector_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anshmidt.oneline_list_entry_selector.OneLineListEntrySelector;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneLineListEntrySelector sizeSelector = findViewById(R.id.size_list_entry_selector);
        ArrayList<String> sizesList = new ArrayList<>();
        sizesList.add("Small");
        sizesList.add("Medium");
        sizesList.add("Large");
        sizeSelector.setList(sizesList);
        sizeSelector.setInitialEntryNumber(1);
        sizeSelector.setOnValueChangeListener(new OneLineListEntrySelector.OnValueChangeListener() {
            @Override
            public void onValueChange(OneLineListEntrySelector view, String oldValue, String newValue) {
                Toast.makeText(MainActivity.this, "New value is " + newValue, Toast.LENGTH_SHORT).show();
            }
        });

        OneLineListEntrySelector numbersSelector = findViewById(R.id.numbers_list_entry_selector);
        ArrayList<Integer> numbersList = new ArrayList<>();
        numbersList.add(16);
        numbersList.add(32);
        numbersList.add(64);
        numbersSelector.setList(numbersList);



        OneLineListEntrySelector prioritySelector = findViewById(R.id.priority_list_entry_selector);
        ArrayList<String> prioritiesList = new ArrayList<>();
        prioritiesList.add("Minor");
        prioritiesList.add("Normal");
        prioritiesList.add("Major");
        prioritiesList.add("Critical");
        prioritySelector.setList(prioritiesList);
        prioritySelector.setInitialEntryNumber(prioritiesList.indexOf("Normal"));


    }
}
