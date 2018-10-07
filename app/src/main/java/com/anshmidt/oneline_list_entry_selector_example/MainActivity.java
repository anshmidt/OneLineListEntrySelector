package com.anshmidt.oneline_list_entry_selector_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anshmidt.oneline_list_entry_selector.OneLineListEntrySelector;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneLineListEntrySelector oneLineListEntrySelector = (OneLineListEntrySelector) findViewById(R.id.list_entry_selector);


        ArrayList<String> sizesList = new ArrayList<>();
        sizesList.add("Small");
        sizesList.add("Medium");
        sizesList.add("Large");

        oneLineListEntrySelector.setList(sizesList);

    }
}
