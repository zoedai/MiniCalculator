package com.example.dai.minicalculator;

import android.content.DialogInterface;
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
    TextView screen, screen2;
    private final String TOGGLESIGN = "+/-";
    private final String DOT = ".";
    private Double opLeft = 0d, opRight, result;
    private final String[] btn_op = { "C", "AC","+","-","*","/", "%","="};
    private int op = -1;
    private boolean clear = false, hasDot = false, neg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.lcd);
        screen2 = (TextView) findViewById(R.id.lcd_2);

        final String[] btn_num = new String[12];


        for (int i = 0; i < 9; i++) {
            btn_num[i] = Integer.toString(i+1);
        }
        btn_num[10] = "0";
        btn_num[9] = TOGGLESIGN;
        btn_num[11] = DOT;

// number stuff

        ArrayAdapter<String> numAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, btn_num);

        GridView btnGrid = (GridView) findViewById(R.id.btn_num);
        btnGrid.setNumColumns(3);
        btnGrid.setAdapter(numAdapter);
        btnGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (btn_num[position].equals(TOGGLESIGN)) {
                    String currText = screen2.getText().toString();
                    if (currText.length() == 0 || Double.parseDouble(currText) == 0d) {
                        return;
                    }
                    neg = !neg;
                    if (neg) {
                        screen2.setText("-" + currText);
                    } else {
                        screen2.setText(currText.substring(1));
                    }
                    return;
                }

                if (clear) {
                    screen2.setText("");
                    clear = false;
                }

                if (btn_num[position].equals(DOT)) {
                    if (hasDot) {
                        return;
                    }
                    hasDot = true;
                }
                screen2.append(btn_num[position]);
            }
        });

        // end of number stuff

        // operator stuff
        ArrayAdapter<String> opAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, btn_op);
        GridView opGrid = (GridView) findViewById(R.id.btn_op);
        opGrid.setNumColumns(2);
        opGrid.setAdapter(opAdapter);

        opGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence currText = screen2.getText();

                if (position == 0) {
                    // C
                    if (currText.length() > 0) {
                        screen2.setText(currText.subSequence(0, currText.length() - 1));
                    }
                } else if (position == 1) {
                    // AC
                    screen2.setText("0");
                    opLeft = 0d;
                    opRight = null;
                } else if (position < 7) {
                    boolean unfinished = op > 0;
                    // do operation
                    if (unfinished) {
                        // unfinished calculation
                        calculate(true);
                    }

                    if (currText.length() > 0) {
                        opLeft = Double.parseDouble(screen2.getText().toString());
                    }

                    op = position;
                    if (!unfinished) {
                        screen.setText(screen2.getText());
                    }
                    screen.append(" " + btn_op[position]);
                    clear = true;
                } else {
                    calculate(false);
                }
            }
        });



    }

    private void calculate(boolean append) {
        if (op < 0) {
            return;
        }

        String currText = screen2.getText().toString();

        // if no right hand operand, use the left hand operand
        opRight = currText.length() > 0 ? Double.parseDouble(currText) : opLeft;


        if (op == 2) {
            result = opLeft + opRight;
        } else if (op == 3) {
            result = opLeft - opRight;
        } else if (op == 4) {
            result = opLeft * opRight;
        } else if (op == 5) {
            result = opLeft == 0 ? 0 : opLeft / opRight;
        } else {
            result = opLeft % opRight;
        }

        if (append && screen.getText().length() < 40) {
            screen.append(" " + opRight);
        } else {
            screen.setText("");
        }

        screen2.setText(result+"");
        op = -1;
        opLeft = 0d;
        opRight = null;
        clear = true;
        hasDot = false;

    }


}
