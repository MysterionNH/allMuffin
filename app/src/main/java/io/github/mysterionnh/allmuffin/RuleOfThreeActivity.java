package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Weird calculator for percentage and rule of three stuff
 */
public class RuleOfThreeActivity extends BaseActivity {

    private Context _context = this;

    // Count of TextViews in Layout, is important!
    private final int INPUT_FIELD_COUNT = 6;

    // Initializes the arrays used here
    private String[] texts  = new String[INPUT_FIELD_COUNT];
    private EditText[] eTexts;
    private boolean[] empty = new boolean[INPUT_FIELD_COUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_rule_of_three);
        setBGColorAccordingToSettings(_context);

        // Sets up the button listener for the submit button
        findViewById(R.id.submitBtn).setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener;

    {
        btnListener = new View.OnClickListener() {

            public void onClick(View v) {
                // Magic Stuff, don't touch
                getTexts();
                areInputsEmpty();
                calculate();
                updateFields(texts, eTexts);
            }
        };
    }

    private void updateFields(String[] texts, TextView[] eTexts) {

        for (int i = 0; i < texts.length; i++) {
            eTexts[i].setText(texts[i]);
        }
    }

    private void getTexts() {
        // Get all TextFields in Activity, put them into an array
        // Assign to global variable for later use @see updateFields()
        eTexts = new EditText[] {(EditText) findViewById(R.id.currentPercent),
                (EditText) findViewById(R.id.currentValue),
                (EditText) findViewById(R.id.valueOne),
                (EditText) findViewById(R.id.valueHundred),
                (EditText) findViewById(R.id.questionedPercent),
                (EditText) findViewById(R.id.questionedValue)};

        // Extract the text out of the TextFields
        for (int i = 0; i < INPUT_FIELD_COUNT; i++) {
            texts[i] = eTexts[i].getText().toString();
        }
    }

    // Looks up if the inputs are empty or not usable
    private void areInputsEmpty() {
        for (int i = 0; i < INPUT_FIELD_COUNT; i++) {
            empty[i] = texts[i].isEmpty();

            // Okay, there is text in it, buuut is it a double?
            if (!empty[i]) {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    Double.parseDouble(texts[i]);
                } catch (Exception e) {
                    // Nope it isn't, so it's empty for our uses
                    empty[i] = true;
                }
            }
        }
        // Okay, done so far, but to avoid a dividing by 0, one last check
        if (!empty[0]) {
            empty[0] = (Double.parseDouble(texts[0]) == 0.0);
        }
    }

    // Actually calculates stuff, if possible
    private void calculate() {
        if (!empty[0] && !empty[1]) {
            // Set value(s)
            texts[2] = String.valueOf(Double.parseDouble(texts[1]) / Double.parseDouble(texts[0]));
            texts[3] = String.valueOf(100 * (Double.parseDouble(texts[1]) / Double.parseDouble(texts[0])));
            lastLine();
        } else if (empty[2] && empty[3]) {
            // Nothing to calculate, stupid user
            error();
        } else if (!empty[2]) {
            // In this case the first two TextViews are useless, hence I "empty" them
            texts[0] = "---";
            texts[1] = "---";

            // Set value(s)
            texts[3] = String.valueOf(100 * Double.parseDouble(texts[2]));
            lastLine();
        } else {
            // In this case the first two TextViews are useless, hence I "empty" them
            texts[0] = "---";
            texts[1] = "---";

            // Set value(s)
            texts[2] = String.valueOf(Double.parseDouble(texts[3]) / 100);
            lastLine();
        }
    }

    // Sets the content of the last line in the Activity, if requested hence the if 'n stuff
    private void lastLine() {
        if (!empty[4]) {
            texts[5] = String.valueOf(Double.parseDouble(texts[2]) * Double.parseDouble(texts[4]));
        } else if (!empty[5]) {
            texts[4] = String.valueOf(Double.parseDouble(texts[5]) / Double.parseDouble(texts[2]));
        }
    }

    //Tells the user that he made an mistake with the inputs
    private void error() {
        NewMessagePopup popup = new NewMessagePopup(this);
        popup.setTitle(getResources().getString(R.string.wrongInputTitle));
        popup.setBody(getResources().getString(R.string.wrongInputMsg));
        popup.setBtnText(getResources().getString(R.string.wrongInputBtnText));
        popup.setColor(Color.RED);
        popup.setAlign(Gravity.FILL_HORIZONTAL);

        popup.show();
    }
}