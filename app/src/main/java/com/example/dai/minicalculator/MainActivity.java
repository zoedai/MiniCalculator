package com.example.dai.minicalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView screen;
    private final String TAG = "----------";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.lcd);
        final String[] btn_num = new String[10];
        String[] btn_op = {"=","+","-","*","/", "%", "C", "AC"};

        for (int i = 0; i < 9; i++) {
            btn_num[i] = Integer.toString(i+1);
        }
        btn_num[9] = "0";



        ArrayAdapter<String> numAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, btn_num);

        GridView btnGrid = (GridView) findViewById(R.id.btn_num);
        btnGrid.setNumColumns(3);
        btnGrid.setAdapter(numAdapter);
        btnGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.i(TAG, ""+position);
                screen.append(btn_num[position]);
            }
        });

        ArrayAdapter<String> opAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, btn_op);
        GridView opGrid = (GridView) findViewById(R.id.btn_op);
        opGrid.setNumColumns(2);
        opGrid.setAdapter(opAdapter);



    }
}
