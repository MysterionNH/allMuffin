package com.mysterionnh.allmuffin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends BaseActivity {

    private final Context _context = (Context) this;
    Button mClickedButton;
    TextView mOutputView;
    //TODO: siehste schon
    View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mClickedButton = (Button) v;
            String outputText = mOutputView.getText().toString();
            outputText += mClickedButton.getText();
            mOutputView.setText(outputText);
        }
    };
    View.OnClickListener buttonListener1 = new View.OnClickListener() {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setBGColorAccordingToSettings(_context);

        mOutputView = (TextView) findViewById(R.id.outputView);
        GridLayout inputWrapper = (GridLayout) findViewById(R.id.inputGrid);
        int buttonCount = inputWrapper.getChildCount();
        Button buttons[] = new Button[buttonCount];
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
