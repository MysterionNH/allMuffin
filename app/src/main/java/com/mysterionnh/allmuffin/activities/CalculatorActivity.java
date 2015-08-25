package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.helper.Errors;

import java.util.Locale;

public class CalculatorActivity extends BaseActivity {

    /**
     * As always, storing context, easier and safer to access
     */
    private final Context mContext = (Context) this;
    private SharedPreferences mSharedPrefs;
    /**
     * The value with we use for rounding, change regarding to settings
     */
    private double mRoundingValue;
    /**
     * The View Button that was clicked
     */
    private Button mClickedButton;
    /**
     * The additional top view that shows the last problem so the solutions isn't alone
     */
    private TextView mLastProblemView;
    /**
     * The view were the problem while typing is shown and the solution after calculation
     */
    private TextView mOutputView;
    /**
     * The text of {@see mOutputView}
     */
    private String mOutputText;
    /**
     * The OnClickListener for all buttons that get displayed somehow
     */
    private boolean mWasShowingSolution;
    /**
     * This stores whether the solution was rounded or not
     */
    private boolean mRounded = false;

    private final View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            updateGlobals(v);

            switch (mClickedButton.getId()) {
                case R.id.reverseButton: {
                    mOutputText = reverseNum(mOutputText);
                    break;
                }
                case R.id.periodButton: {
                    if (!mOutputText.equals("")) {
                        if (lastCharIsNumeric(mOutputText) && !currentNumIsDecimal(mOutputText)) {
                            mOutputText += ".";
                        } else {
                            Errors.errorToast(mContext, getResources().getString(R.string.invalid_entry));
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
                            } else if (temp[temp.length - 1] == '(' || temp[temp.length - 1] == '+'
                                    || temp[temp.length - 1] == '*' || temp[temp.length - 1] == '÷'
                                    || temp[temp.length - 1] == '-') {
                                mOutputText += "(";
                            } else {
                                Errors.errorToast(mContext,
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
                    if (mWasShowingSolution) {
                        clearDisplay();
                    }
                    if (!mOutputText.equals("")) {
                        char[] temp = mOutputText.toCharArray();
                        switch (temp[temp.length - 1]) {
                            case ')': {
                                mOutputText += "+" + mClickedButton.getText();
                                break;
                            }
                            case '/': {
                                if (mClickedButton.getText().toString().equals("0")) {
                                    Errors.errorToast(mContext,
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
    /**
     * The OnClickListener for all buttons that have an action and doesn't get displayed directly
     */
    private final View.OnClickListener buttonListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            updateGlobals(v);
            boolean isUpdated = false;

            switch (mClickedButton.getId()) {
                case R.id.clrButton: {
                    mOutputText = "";
                    break;
                }
                case R.id.delButton: {
                    if (mWasShowingSolution) {
                        clearDisplay();
                        break;
                    }
                    if (mOutputText.length() != 0) {
                        char output[] = mOutputText.toCharArray();
                        output[mOutputText.length() - 1] = ' ';
                        mOutputText = "";
                        for (char c : output) {
                            mOutputText += c;
                        }
                        if (!mOutputText.equals(" ")) {
                            String temp[] = mOutputText.split(" ");
                            mOutputText = temp[0];
                        } else {
                            mOutputText = "";
                        }
                    } else {
                        Errors.errorToast(mContext,
                                getResources().getString(R.string.warning_output_empty));
                    }
                    break;
                }
                case R.id.equalsButton: {
                    updateOutput(formatStringAccordingToLanguage(calculate(mOutputView.getText().toString(), true)));
                    isUpdated = true;
                }
            }
            if (!isUpdated) {
                updateOutput(formatStringAccordingToLanguage(mOutputText));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initializing the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        setBGColorAccordingToSettings(mContext);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        mRoundingValue = Math.pow(10.0, Double.valueOf(mSharedPrefs.getString(mContext.getResources().getString(R.string.pref_key_calc), "2")));

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

    /**
     * Does the calculation of the problem
     *
     * @param problem           The problem to calculate, usually mOutputText
     * @param updateLastProblem true if you lastProblemView to show your problem
     * @return Returns the solution of the problem as string or, if problem was
     * invalid, the problem itself
     */
    private String calculate(String problem, boolean updateLastProblem) {
        if (problemIsValid(problem)) {
            problem = fixProblem(problem);
            /**
             * The solution of the primary problems (e.g. multiplication and division) are stored here
             */
            double primarySolution = 0.0;
            /**
             * The solution of the "normal" problems (e.g. addition and subtraction) are stored here
             */
            double solution;
            /**
             * Used to define the order of numbers and operators, also to make sure every number
             * is used with the correct other number and operator
             */
            int numAndOperatorCount = 0;
            /**
             * When building the numbers out of the char array, before they are finished they get
             * stored here temporally till I'm ready to build them
             */
            String tempNum = "";
            /**
             * Used for all arrays as max length, none of them can get longer than the original
             * problem
             */
            int PROBLEM_LENGTH = problem.length();
            /**
             * Some variables need to be reset, in here is stored which are allowed when
             */
            boolean[] allowReset = new boolean[PROBLEM_LENGTH];
            /**
             * The index of the primary operators inside the char[] out of the problem
             */
            int[] primaryOperatorLocations = new int[PROBLEM_LENGTH];
            /**
             * All operators in the order they are going to be used
             */
            char[] operators = new char[PROBLEM_LENGTH];
            /**
             * All numbers in the oder they are going to be used
             */
            Double[] numbers = new Double[PROBLEM_LENGTH];
            /**
             * The original problem but as char[]
             */
            char[] outputArray = problem.toCharArray();
            /**
             * Everything in between parentheses, so this can be calculated beforehand
             */
            char[] inParentheses = new char[PROBLEM_LENGTH];

            for (int i = 0; i < PROBLEM_LENGTH; i++) {
                // Here the numbers get build
                if (lastCharIsNumeric(String.valueOf(outputArray[i])) || outputArray[i] == '.' || outputArray[i] == '.') {
                    tempNum += outputArray[i];
                } else if (outputArray[i] == '(') {
                    if (!tempNum.equals("")) {
                        numbers[numAndOperatorCount] = Double.valueOf(tempNum);
                        numAndOperatorCount++;
                    }
                    int openedParentheses = 0;
                    int closedParentheses = 0;
                    for (int p = 0; p < problem.length(); p++) {
                        if (problem.charAt(p) == '(') {
                            openedParentheses++;
                        }
                    }
                    for (int n = i + 1; openedParentheses > closedParentheses; n++) {
                        if (outputArray[n] == ')') {
                            closedParentheses++;
                        }
                        inParentheses[n - (i + 1)] = outputArray[n];
                    }
                    tempNum = "";
                    for (char c : inParentheses) {
                        if (c != '\u0000') {
                            tempNum += c;
                        }
                    }
                    numbers[numAndOperatorCount] = Double.valueOf(calculate(tempNum, false));
                    numAndOperatorCount++;
                    i += tempNum.length(); // Once was +1, don't remember why, but it caused a bug, hence I took it out
                    tempNum = "";
                } else {
                    // Okay, it isn't a number anymore, if we haven't build the number before this
                    // already, do it now
                    if (!tempNum.equals("")) {
                        numbers[numAndOperatorCount] = Double.valueOf(tempNum);
                        tempNum = "";
                    }

                    if (outputArray[i] != ')') {
                        if (numAndOperatorCount > 0) {
                            if (operators[numAndOperatorCount - 1] == '\u0000') {
                                numAndOperatorCount--;
                            }
                        }
                        operators[numAndOperatorCount] = outputArray[i];
                        numAndOperatorCount++;
                    }
                }
            }
            // Last check if we didn't forgot to build a number
            if (!tempNum.equals("")) {
                numbers[numAndOperatorCount] = Double.valueOf(tempNum);
            }

            // Look if there are any primary operators and if so, where
            for (int k = 0; k < operators.length; k++) {
                if (operators[k] == '*' || operators[k] == '÷') {
                    primaryOperatorLocations[k] = k;
                } else {
                    primaryOperatorLocations[k] = -1;
                }
            }

            if (primariesExisting(primaryOperatorLocations) && numbers[0] == null && operators[0] == '-' && numbers[1] != null) {
                numbers[1] = (0 - numbers[1]);
            }
            Integer lastL = null;
            // If there were any, calculate with them
            for (int l = 0; l < primaryOperatorLocations.length; l++) {
                if (primaryOperatorLocations[l] >= 0) {
                    switch (operators[primaryOperatorLocations[l]]) {
                        case '*': {
                            if (lastL == null) {
                                lastL = l;
                            }
                            if (Math.abs(lastL - l) < 2) {
                                primarySolution = numbers[primaryOperatorLocations[l]] * numbers[primaryOperatorLocations[l] + 1];
                            } else {
                                primarySolution += numbers[primaryOperatorLocations[l]] * numbers[primaryOperatorLocations[l] + 1];
                            }
                            numbers[primaryOperatorLocations[l] + 1] = primarySolution;
                            allowReset[l] = true;
                            lastL = l;
                            break;
                        }
                        case '÷': {
                            if (numbers[primaryOperatorLocations[l] + 1] != 0) {
                                if (lastL == null) {
                                    lastL = l;
                                }
                                if (Math.abs(lastL - l) < 2) {
                                    primarySolution = numbers[primaryOperatorLocations[l]] / numbers[primaryOperatorLocations[l] + 1];
                                } else {
                                    primarySolution += numbers[primaryOperatorLocations[l]] / numbers[primaryOperatorLocations[l] + 1];
                                }
                                numbers[primaryOperatorLocations[l] + 1] = primarySolution;
                                allowReset[l] = true;
                                lastL = l;
                            } else {
                                Errors.errorToast(mContext,
                                        getResources().getString(R.string.invalid_entry));
                                return problem;
                            }
                            break;
                        }
                    }
                }
            }

            // Reset some variables.. not sure anymore why I had to do this
            for (int m = 0; m < allowReset.length; m++) {
                if (allowReset[m]) {
                    numbers[primaryOperatorLocations[m]] = 0.0;
                    numbers[primaryOperatorLocations[m] + 1] = 0.0;
                    operators[primaryOperatorLocations[m]] = ' ';
                }
            }
            // The first number is our starting point
            if (numbers[0] != null) {
                solution = numbers[0];
            } else {
                solution = 0;
            }

            // Woo! - Real calculation
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

            tempNum = roundAndDeleteUnusedNumbers(solution + primarySolution);

            if (updateLastProblem) {
                if (mRounded) {
                    mLastProblemView.setText(formatStringAccordingToLanguage(fixDecimals(problem) + "≈"));
                } else {
                    mLastProblemView.setText(formatStringAccordingToLanguage(fixDecimals(problem) + "="));
                }
            }

            // Everything went well, here comes the solution
            return tempNum;
        } else {
            // Noob
            Errors.errorToast(mContext, getResources().getString(R.string.invalid_entry));
            return problem;
        }
    }

    private boolean primariesExisting(int[] pO) {
        for (int i : pO) {
            if (i >= 0) {
                return true;
            }
        }
        return false;
    }

    private String roundAndDeleteUnusedNumbers(Double solution) {
        String tempSolution;
        boolean decimal = true;
        char[] outputArray;
        String tempNum;
        outputArray = (String.valueOf(solution)).toCharArray();
        for (int r = outputArray.length - 1; r > 0; r--) {
            if (outputArray[r] == '0') {
                outputArray[r] = 'ö';
            } else {
                if (outputArray[r] == '.') {
                    outputArray[r] = 'ö';
                    decimal = false;
                }
                r = 0;
            }
        }
        tempNum = "";
        for (char ch : outputArray) {
            if (ch != 'ö')
                tempNum += ch;
        }
        if (decimal) {
            solution = Double.valueOf(tempNum);
            tempSolution = tempNum;
            tempNum = String.valueOf(Math.round(solution * mRoundingValue) / mRoundingValue);
            // Only if it really changed we want the other equal sign
            if (!tempSolution.equals(tempNum)) {
                mRounded = true;
            }
        }
        return tempNum;
    }

    private void updateOutput(String outputText) {
        mOutputView.setText(formatStringAccordingToLanguage(outputText));
        mOutputText = "";
    }

    private boolean problemIsValid(String problem) {
        problem = problem.toLowerCase();
        char[] problemArray = problem.toCharArray();
        return !problem.equals("") && !problem.equals("+") && !problem.equals("-") &&
                !problem.equals("*") && !problem.equals("÷") && !problem.equals("(") &&
                !problem.contains("i") && !problem.contains("n") && !problem.contains("f") &&
                !problem.contains("t") && !problem.contains("y") && !problem.contains("a") &&
                !(problemArray[problemArray.length - 1] == '*') &&
                !(problemArray[problemArray.length - 1] == '+') &&
                !(problemArray[problemArray.length - 1] == '÷') &&
                !(problemArray[problemArray.length - 1] == '-');
    }

    @NonNull
    /**
     * Just in case the user was to lazy to close all thous stupid parentheses it opened, we close
     * them
     *
     * @param problem The string in which I should check the ps
     * @return returns the fixed string, if it was okay, itself
     */
    private String fixProblem(String problem) {
        char[] problemArray = problem.toCharArray();
        int openedParentheses = 0;
        int closedParentheses = 0;
        for (int p = 0; p < problemArray.length; p++) {
            if (problemArray[p] == '(') {
                openedParentheses++;
            } else if (problemArray[p] == ')') {
                closedParentheses++;
            } else if (problem.charAt(p) == ',') {
                problemArray[p] = '.';
            }
        }
        problem = String.valueOf(problemArray);
        if (openedParentheses > closedParentheses) {
            for (int q = 0; q < openedParentheses - closedParentheses; q++) {
                problem += ")";
            }
        }
        return problem;
    }

    @NonNull
    private String fixDecimals(String problem) {

        if (problem.charAt(problem.length() - 1) == '.') {
            problem += "0";
        }
        return problem;
    }

    private String formatStringAccordingToLanguage(String problem) {
        // Get the *APP* setting
        Locale loc = Locale.getDefault();
        // When german, put a ',' instead of a '.'
        if (loc.getDisplayLanguage().equals("Deutsch")) {
            char[] problemArray = problem.toCharArray();
            for (int i = 0; i <= problemArray.length - 1; i++) {
                if (problemArray[i] == '.') {
                    problemArray[i] = ',';
                }
            }
            problem = String.valueOf(problemArray);
        }
        return problem;
    }

    private void putSign(Button btn) {
        String buttonText = btn.getText().toString();
        if (!mOutputText.equals("")) {
            if (lastCharIsNumeric(mOutputText) || mOutputText.charAt(mOutputText.length() - 1) == ')') {
                mOutputText += buttonText;
            } else if (lastCharIsNumeric(mOutputText) || mOutputText.charAt(mOutputText.length() - 1) == '(') {
                if (buttonText.equals("-")) {
                    mOutputText += buttonText;
                }
            } else {
                Errors.errorToast(mContext, getResources().getString(R.string.invalid_entry));
            }
        } else {
            mOutputText = "0" + btn.getText().toString();
        }
    }

    private boolean currentNumIsDecimal(String string) {
        char[] text = string.toCharArray();
        for (int i = text.length - 1; i >= 0; i--) {
            if (!lastCharIsNumeric(String.valueOf(text[i]))) {
                return text[i] == '.' || text[i] == ',';
            }
        }
        return false;
    }

    private String reverseNum(String string) {
        char[] text = string.toCharArray();
        for (int i = text.length - 1; i >= 0; i--) {
            if (!lastCharIsNumeric(String.valueOf(text[i])) && text[i] != '.') {
                String[] tempStrings = new String[2];
                tempStrings[0] = string.substring(0, i + 1);
                tempStrings[1] = string.substring(i + 1);
                if (text[i] == ')') {
                    tempStrings[1] = "*(-" + tempStrings[1];
                } else {
                    tempStrings[1] = "(-" + tempStrings[1];
                }
                string = tempStrings[0] + tempStrings[1];
                return string;
            }
        }
        return "(-" + string;
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

    private boolean isShowingSolution() {
        return !mLastProblemView.getText().toString().equals("");
    }

    private void clearDisplay() {
        mLastProblemView.setText("");
        mOutputView.setText("");
        mOutputText = "";
        mRounded = false;
    }

    private void updateGlobals(View v) {
        mClickedButton = (Button) v;
        mOutputText = mOutputView.getText().toString();
        mWasShowingSolution = isShowingSolution();
        mLastProblemView.setText("");
    }
}
