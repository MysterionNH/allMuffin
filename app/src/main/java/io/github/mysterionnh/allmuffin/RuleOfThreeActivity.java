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
    private final int TEXTVIEW_COUNT = 6;

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

                EditText eTexts[] = {(EditText) findViewById(R.id.currentPercent),
                        (EditText) findViewById(R.id.currentValue),
                        (EditText) findViewById(R.id.valueOne),
                        (EditText) findViewById(R.id.valueHundred),
                        (EditText) findViewById(R.id.questionedPercent),
                        (EditText) findViewById(R.id.questionedValue)};

                String texts[] = new String[TEXTVIEW_COUNT];

                for (int i = 0; i < TEXTVIEW_COUNT; i++) {
                    texts[i] = eTexts[i].getText().toString();
                }

                boolean empty[] = new boolean[texts.length];

                int textCount = 0;

                for (String text : texts) {
                    empty[textCount] = text.isEmpty();

                    //When it isn't empty in a regular way, checks if it is empty in our way 1
                    if (!empty[textCount]) {
                        try {
                            Double.parseDouble(text);
                        } catch (Exception e) {
                            empty[textCount] = true;
                        }
                    }
                    ++textCount;
                }

                //#Easteregg #enterStanleyParableReferenceHere
                if (eight(texts)) {
                    openPopup("8",
                            "",
                            "8",
                            Color.RED,
                            Gravity.CENTER,
                            75);
                    return;
                }

                if (!empty[0] && !empty[1]) {
                    //Catch division by zero before it happens

                    if (texts[0].equals("0")) {
                        error();
                        return;
                    }

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
                    error();
                    return;

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
                updateFields(texts, eTexts);
            }
        };
    }

    public void updateFields(String[] texts, TextView[] eTexts) {

        for (int i = 0; i < texts.length; i++) {
            eTexts[i].setText(texts[i]);
        }
    }

    // Just an simple easter egg
    public boolean eight(String texts[]) {
        boolean ee = true;
        for (int i = 0; i < texts.length && ee; i++) {
            ee = texts[i].equals("8");
        }
        return ee;
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

//TODO : Fix the dividing by 0 errors that are still sitting in there :/
//TODO : Add rounding!!!1!elf!!
//TODO: Fjx the E bug thingie