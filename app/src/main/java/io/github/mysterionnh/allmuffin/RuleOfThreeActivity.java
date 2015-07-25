package io.github.mysterionnh.allmuffin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class RuleOfThreeActivity extends BaseActivity {

    // Count of TextViews in Layout, is important!
    private final int INPUT_FIELD_COUNT = 6;
    String texts[]  = new String[INPUT_FIELD_COUNT];
    EditText eTexts[] = new EditText[INPUT_FIELD_COUNT];
    boolean empty[] = new boolean[INPUT_FIELD_COUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_rule_of_three);

        // Sets up the button listener for the submit button
        Button btn1 = (Button)findViewById(R.id.submitBtn);
        btn1.setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener;

    {
        btnListener = new View.OnClickListener() {

            public void onClick(View v) {
            magicStuff();
            }
        };
    }

    public void updateFields(String[] texts, TextView[] eTexts) {

        for (int i = 0; i < texts.length; i++) {
            eTexts[i].setText(texts[i]);
        }
    }

    public void getTexts() {
        // TODO: Try to get rid of uselessPlaceholder[]
        // Get all TextFields in Activity, put them into an array
        EditText uselessPlaceHolder[] = {(EditText) findViewById(R.id.currentPercent),
                (EditText) findViewById(R.id.currentValue),
                (EditText) findViewById(R.id.valueOne),
                (EditText) findViewById(R.id.valueHundred),
                (EditText) findViewById(R.id.questionedPercent),
                (EditText) findViewById(R.id.questionedValue)};
        // Assign to global variable for later use @see updateFields()
        eTexts = uselessPlaceHolder;

        // Extract the text out of the TextFields
        for (int i = 0; i < INPUT_FIELD_COUNT; i++) {
            texts[i] = eTexts[i].getText().toString();
        }
    }

    public void areInputsEmpty() {
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

    public void calculate() {
        // TODO: Clean this up, maybe a switch?

        if (!empty[0] && !empty[1]) {

            //Calculating the value at one percent by dividing the current value by current percent
            texts[2] = String.valueOf(Double.parseDouble(texts[1]) / Double.parseDouble(texts[0]));

            //Calculating the value at one hundred percent by dividing the current value by current percent and multiplying with one hundred #bestWay #nope :D
            texts[3] = String.valueOf(100 * (Double.parseDouble(texts[1]) / Double.parseDouble(texts[0])));

            if (!empty[4]) {
                texts[5] = String.valueOf(Double.parseDouble(texts[2]) * Double.parseDouble(texts[4]));
            } else if (!empty[5]) {
                texts[4] = String.valueOf(Double.parseDouble(texts[5]) / Double.parseDouble(texts[2]));
            }
        } else if (empty[2] && empty[3]) {
            // Nothing to calculate, stupid user
            error();

        } else if (!empty[2]) {

            // In this case the first two TextViews are useless, hence I "empty" them
            texts[0] = "---";
            texts[1] = "---";

            texts[3] = String.valueOf(100 * Double.parseDouble(texts[2]));

            // When the last line (calc line) is used, set the not given variable
            if (!empty[4]) {
                texts[5] = String.valueOf(Double.parseDouble(texts[2]) * Double.parseDouble(texts[4]));
            } else if (!empty[5]) {
                texts[4] = String.valueOf(Double.parseDouble(texts[5]) / Double.parseDouble(texts[2]));
            }
        } else {

            // In this case the first two TextViews are useless, hence I "empty" them
            texts[0] = "---";
            texts[1] = "---";

            texts[2] = String.valueOf(Double.parseDouble(texts[3]) / 100);

            // When the last line (calc line) is used, set the not given variable
            if (!empty[4]) {
                texts[5] = String.valueOf(Double.parseDouble(texts[2]) * Double.parseDouble(texts[4]));
            } else if (!empty[5]) {
                texts[4] = String.valueOf(Double.parseDouble(texts[5]) / Double.parseDouble(texts[2]));
            }
        }
    }

    public void magicStuff() {
        getTexts();
        areInputsEmpty();
        calculate();
        updateFields(texts, eTexts);
    }

    public void error() {
        //Tells the user that he made an mistake
        openPopup(getResources().getString(R.string.wrongInputTitle),
                getResources().getString(R.string.wrongInputMsg),
                getResources().getString(R.string.wrongInputBtnText),
                Color.RED,
                Gravity.FILL_HORIZONTAL,
                DEFAULT_TEXT_SIZE);
    }
}