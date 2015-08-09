package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mysterionnh.allmuffin.R;

public class CalculatorActivity extends BaseActivity {

    private final Context _context = (Context) this;
    private Button mClickedButton;
    private TextView mOutputView;
    //TODO: siehste schon
    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String outputText = mOutputView.getText().toString();
            mClickedButton = (Button) v;

            switch (mClickedButton.getId()) {
                case R.id.parenthesesButton: {
                    //if (!outputText.equals("")) {
                    //outputText.contains("(");
                    //}
                    break;
                }
                default: {
                    outputText += mClickedButton.getText();
                    mOutputView.setText(outputText);
                }
            }
        }
    };
    private final View.OnClickListener buttonListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String outputText = mOutputView.getText().toString();
            mClickedButton = (Button) v;

            switch (mClickedButton.getId()) {
                case R.id.clrButton: {
                    outputText = "";
                    break;
                }
                case R.id.delButton: {
                    if (outputText.length() != 0) {
                        char output[] = outputText.toCharArray();
                        output[outputText.length() - 1] = ' ';
                        outputText = "";
                        for (char c : output) {
                            outputText += c;
                        }
                        if (!outputText.equals(" ")) {
                            String temp[] = outputText.split(" ");
                            outputText = temp[0];
                        } else {
                            outputText = "";
                        }
                    } else {
                        Toast toast = Toast.makeText(_context,
                                R.string.outputEmptyWarning,
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                    break;
                }
                case R.id.equalsButton: {
                    //TODO: Call calcMethod
                }
            }
            updateOutput(outputText);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initializing the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setBGColorAccordingToSettings(_context);

        // Get the TextView were everything gets displayed
        mOutputView = (TextView) findViewById(R.id.outputView);
        // Gets the grid in which all buttons are, then extracts all buttons, and sort them (not-/displayable)
        // Add onClickListener according to sorting
        GridLayout inputWrapper = (GridLayout) findViewById(R.id.inputGrid);
        int buttonCount = inputWrapper.getChildCount();
        Button[] buttons = new Button[buttonCount];
        boolean isDisplayable;

        for (int i = 0; i < buttonCount; i++) {
            buttons[i] = (Button) inputWrapper.getChildAt(i);
            switch (buttons[i].getId()) {
                case R.id.delButton: {
                    isDisplayable = false;
                    break;
                }
                case R.id.clrButton: {
                    isDisplayable = false;
                    break;
                }
                case R.id.equalsButton: {
                    isDisplayable = false;
                    break;
                }
                default: {
                    isDisplayable = true;
                    break;
                }
            }
            if (isDisplayable) {
                buttons[i].setOnClickListener(buttonListener);
            } else {
                buttons[i].setOnClickListener(buttonListener1);
            }
        }
    }

    private void updateOutput(String outputText) {
        mOutputView.setText(outputText);
    }
}
