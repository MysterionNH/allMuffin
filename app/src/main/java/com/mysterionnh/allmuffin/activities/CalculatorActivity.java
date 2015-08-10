package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.helper.Errors;

public class CalculatorActivity extends BaseActivity {

    private final Context _context = (Context) this;
    private Button mClickedButton;
    private TextView mLastProblemView;
    private TextView mOutputView;
    private String mOutputText;
    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mLastProblemView.setText("");
            mOutputText = mOutputView.getText().toString();
            mClickedButton = (Button) v;

            switch (mClickedButton.getId()) {
                case R.id.reverseButton: {
                    // TODO: Gets handled, but in the worst way possible
                    mOutputText = reverseNum(mOutputText);
                    break;
                }
                case R.id.periodButton: {
                    if (!mOutputText.equals("")) {
                        if (lastCharIsNumeric(mOutputText) && !currentNumIsDecimal(mOutputText)) {
                            mOutputText += ".";
                        } else {
                            Errors.errorToast(_context, getResources().getString(R.string.invalid_entry));
                        }
                    } else {
                        mOutputText = "0.";
                    }
                    break;
                }
                case R.id.timesButton: {
                    putSign(mClickedButton);
                    break;
                }
                case R.id.minusButton: {
                    putSign(mClickedButton);
                    break;
                }
                case R.id.plusButton: {
                    putSign(mClickedButton);
                    break;
                }
                case R.id.divButton: {
                    putSign(mClickedButton);
                    break;
                }
                case R.id.parenthesesButton: {
                    if (!mOutputText.equals("")) {
                        char[] temp = mOutputText.toCharArray();
                        int open = 0;
                        int closed = 0;
                        for (int i = temp.length - 1; 0 <= i; i--) {
                            if (temp[i] == '(') {
                                open++;
                            }
                            if (temp[i] == ')') {
                                closed++;
                            }
                        }

                        if (open > closed) {
                            // The last one is open, so we close it
                            if (lastCharIsNumeric(mOutputText) || temp[temp.length - 1] == ')') {
                                // Only close when last char is a number
                                mOutputText += ")";
                            } else if (temp[temp.length - 1] == '(') {
                                mOutputText += "(";
                            } else {
                                Errors.errorToast(_context,
                                        getResources().getString(R.string.invalid_entry));
                            }
                        } else {
                            // More closed than or equal open ones, so open new one
                            if (mOutputText.charAt(mOutputText.length() - 1) == ')' || lastCharIsNumeric(mOutputText)) {
                                // If the last char is the last one or a number, user likely wants to multiply
                                mOutputText += "*";
                            }
                            mOutputText += "(";
                        }
                    } else {
                        mOutputText += "(";
                    }
                    break;
                }
                default: {
                    if (!mOutputText.equals("")) {
                        char[] temp = mOutputText.toCharArray();
                        switch (temp[temp.length - 1]) {
                            case ')': {
                                mOutputText += "+" + mClickedButton.getText();
                                break;
                            }
                            case '/': {
                                if (mClickedButton.getText().toString().equals("0")) {
                                    Errors.errorToast(_context,
                                            getResources().getString(R.string.invalid_entry));
                                } else {
                                    mOutputText += mClickedButton.getText().toString();
                                }
                                break;
                            }
                            default: {
                                mOutputText += mClickedButton.getText().toString();
                            }
                        }
                    } else {
                        mOutputText += mClickedButton.getText().toString();
                    }
                }
            }
            updateOutput(mOutputText);
        }
    };
    private final View.OnClickListener buttonListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mLastProblemView.setText("");
            boolean isUpdated = false;
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
                        Errors.errorToast(_context,
                                getResources().getString(R.string.warning_output_empty));
                    }
                    break;
                }
                case R.id.equalsButton: {
                    updateOutput(calculate(mOutputView.getText().toString(), true));
                    isUpdated = true;
                }
            }
            if (!isUpdated) {
                updateOutput(outputText);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initializing the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setBGColorAccordingToSettings(_context);

        mLastProblemView = (TextView) findViewById(R.id.lastProblem);
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

    //TODO: Clean and comment
    private String calculate(String problem, boolean updateLastProblem) {
        if (!problem.equals("") || !problem.equals("+") || problem.equals("-") || problem.equals("*") || problem.equals("÷")) {
            double primarySolution = 0;
            double solution;
            int numAndOperatorCount = 0;
            String tempNum = "";
            int PROBLEM_LENGTH = problem.length();

            boolean[] allowReset = new boolean[PROBLEM_LENGTH];
            int[] primaryOperatorLocations = new int[PROBLEM_LENGTH];
            char[] operators = new char[PROBLEM_LENGTH];
            Double[] numbers = new Double[PROBLEM_LENGTH];
            char[] outputArray = problem.toCharArray();
            char[] inParentheses = new char[PROBLEM_LENGTH];

            for (int i = 0; i < PROBLEM_LENGTH; i++) {
                if (lastCharIsNumeric(String.valueOf(outputArray[i])) || outputArray[i] == '.') {
                    tempNum += outputArray[i];
                } else if (outputArray[i] == '(') {
                    if (!tempNum.equals("")) {
                        numbers[numAndOperatorCount] = Double.valueOf(tempNum);
                        numAndOperatorCount++;
                    }
                    for (int n = i + 1; outputArray[n] != ')'; n++) {
                        inParentheses[n - (i + 1)] = outputArray[n];
                    }
                    tempNum = "";
                    for (char c : inParentheses) {
                        if (c != '\u0000') {
                            tempNum += c;
                        }
                    }
                    Errors.logError(_context, tempNum);
                    numbers[numAndOperatorCount] = Double.valueOf(calculate(tempNum, false));
                    numAndOperatorCount++;
                    i += tempNum.length() + 1;
                    tempNum = "";
                } else {
                    if (!tempNum.equals("")) {
                        numbers[numAndOperatorCount] = Double.valueOf(tempNum);
                        tempNum = "";
                        //numAndOperatorCount++;
                    }
                    operators[numAndOperatorCount] = outputArray[i];
                    numAndOperatorCount++;
                }
            }
            if (!tempNum.equals("")) {
                numbers[numAndOperatorCount] = Double.valueOf(tempNum);
            }

            for (int k = 0; k < operators.length; k++) {
                if (operators[k] == '*' || operators[k] == '÷') {
                    primaryOperatorLocations[k] = k;
                } else {
                    primaryOperatorLocations[k] = -1;
                }
            }
            for (int l = 0; l < primaryOperatorLocations.length; l++) {
                if (primaryOperatorLocations[l] >= 0) {
                    switch (operators[primaryOperatorLocations[l]]) {
                        case '*': {
                            primarySolution = numbers[primaryOperatorLocations[l]] * numbers[primaryOperatorLocations[l] + 1];
                            numbers[primaryOperatorLocations[l] + 1] = primarySolution;
                            allowReset[l] = true;
                            break;
                        }
                        case '÷': {
                            if (numbers[primaryOperatorLocations[l] + 1] != '0') {
                                primarySolution = numbers[primaryOperatorLocations[l]] / numbers[primaryOperatorLocations[l] + 1];
                                numbers[primaryOperatorLocations[l] + 1] = primarySolution;
                                allowReset[l] = true;
                            } else {
                                Errors.errorToast(_context,
                                        getResources().getString(R.string.invalid_entry));
                                return problem;
                            }
                            break;
                        }
                    }
                }
            }
            for (int m = 0; m < allowReset.length; m++) {
                if (allowReset[m]) {
                    numbers[primaryOperatorLocations[m]] = 0.0;
                    numbers[primaryOperatorLocations[m] + 1] = 0.0;
                    operators[primaryOperatorLocations[m]] = ' ';
                }
            }
            solution = numbers[0];
            for (int j = 0; j < numbers.length; j++) {
                switch (operators[j]) {
                    case '+': {
                        solution += numbers[j + 1];
                        break;
                    }
                    case '-': {
                        solution -= numbers[j + 1];
                        break;
                    }
                }
            }
            if (updateLastProblem) {
                mLastProblemView.setText(problem + "=");
            }
            return (String.valueOf(solution + primarySolution));
        } else {
            Errors.errorToast(_context, getResources().getString(R.string.invalid_entry));
            return problem;
        }
    }

    private void updateOutput(String outputText) {
        mOutputView.setText(outputText);
        mOutputText = "";
    }

    private void putSign(Button btn) {
        if (!mOutputText.equals("")) {
            if (lastCharIsNumeric(mOutputText) || mOutputText.charAt(mOutputText.length() - 1) == ')') {
                mOutputText += btn.getText().toString();
            } else {
                Errors.errorToast(_context, getResources().getString(R.string.invalid_entry));
            }
        } else {
            mOutputText = "0" + btn.getText().toString();
        }
    }

    private boolean currentNumIsDecimal(String string) {
        char[] text = string.toCharArray();
        for (int i = text.length - 1; i >= 0; i--) {
            if (!lastCharIsNumeric(String.valueOf(text[i]))) {
                return text[i] == '.';
            }
        }
        return false;
    }

    private String reverseNum(String string) {
        char[] text = string.toCharArray();
        for (int i = text.length - 1; i >= 0; i--) {
            if (!lastCharIsNumeric(String.valueOf(text[i]))) {
                String[] tempStrings = new String[2];
                tempStrings[0] = string.substring(0, i - 1);
                tempStrings[1] = string.substring(i);
                tempStrings[1] = "(-(" + tempStrings[1];
                string = tempStrings[0] + tempStrings[1];
                return string;
            }
        }
        return "(-(" + string;
    }

    private boolean lastCharIsNumeric(String string) {
        if (!string.equals("")) {
            try {
                //noinspection ResultOfMethodCallIgnored
                Integer.parseInt(String.valueOf(string.charAt(string.length() - 1)));
                // Only close when last char is a number
                return true;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
