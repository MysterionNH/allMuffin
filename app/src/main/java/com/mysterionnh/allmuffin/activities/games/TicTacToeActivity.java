package com.mysterionnh.allmuffin.activities.games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.BaseActivity;
import com.mysterionnh.allmuffin.helper.Errors;

public class TicTacToeActivity extends BaseActivity {

    private final int DRAW = -1;
    private final int EASY = 0;
    private final int NORMAL = 1;
    private final int HARD = 2;
    private final Context mContext = (Context) this;
    private final View.OnClickListener nullListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Do nothing. Just in case we ain't able to disable something, but want to make it unusable
        }
    };
    private boolean mMultiplayer = false;
    private TableRow[] mTableRows;
    private Button[] mButtons;
    private String mNamePlayerOne;
    private String mNamePlayerTwo;
    private int mPlayerOneColor;
    private int mPlayerOneWeakColor;
    private int mPlayerTwoColor;
    private int mPlayerTwoWeakColor;
    private int mPlayersTurn;
    private int mStage = 0;
    private final View.OnClickListener btnListener = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonOnePlayer: {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    mTableRows[mStage].setAlpha(0.3f);
                    mTableRows[mStage].setClickable(false);
                    v.setClickable(false);
                    v.setEnabled(false);
                    mStage++;
                    mTableRows[mStage].setVisibility(View.VISIBLE);
                    mMultiplayer = false;
                    final TextView tv = (TextView) findViewById(R.id.playerOneName);
                    tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                mNamePlayerOne = tv.getText().toString();
                                tv.setEnabled(false);
                                tv.setClickable(false);
                                changeState();
                                TextView display = (TextView) findViewById(R.id.namePlayerTwoView);
                                display.setText(mContext.getResources().getString(R.string.game_ttt_settings_player_two_name_ai, mNamePlayerOne));
                                final TextView tv = (TextView) findViewById(R.id.playerTwoName);
                                tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                            mNamePlayerTwo = tv.getText().toString();
                                            tv.setEnabled(false);
                                            tv.setClickable(false);
                                            changeState();
                                            TextView colorPlayerOneView = (TextView) findViewById(R.id.colorPlayerOneView);
                                            colorPlayerOneView.setText(mContext.getString(R.string.game_ttt_settings_player_one_color, mNamePlayerOne));
                                            Button[] colorButtonsOne = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow), (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
                                            for (Button b : colorButtonsOne) {
                                                b.setOnClickListener(colorListenerOne);
                                            }
                                        }
                                        return false;
                                    }
                                });
                            }
                            return false;
                        }
                    });
                    break;
                }
                case R.id.buttonTwoPlayer: {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    v.setClickable(false);
                    v.setEnabled(false);
                    changeState();
                    mMultiplayer = true;
                    final TextView tv = (TextView) findViewById(R.id.playerOneName);
                    tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                mNamePlayerOne = tv.getText().toString();
                                tv.setEnabled(false);
                                tv.setClickable(false);
                                changeState();
                                final TextView tv = (TextView) findViewById(R.id.playerTwoName);
                                tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                            mNamePlayerTwo = tv.getText().toString();
                                            tv.setEnabled(false);
                                            tv.setClickable(false);
                                            changeState();
                                            TextView colorPlayerOneView = (TextView) findViewById(R.id.colorPlayerOneView);
                                            colorPlayerOneView.setText(mContext.getString(R.string.game_ttt_settings_player_one_color, mNamePlayerOne));
                                            Button[] colorButtonsOne = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow), (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
                                            for (Button b : colorButtonsOne) {
                                                b.setOnClickListener(colorListenerOne);
                                            }
                                        }
                                        return false;
                                    }
                                });
                            }
                            return false;
                        }
                    });
                    break;
                }
            }
            findViewById(R.id.buttonOnePlayer).setClickable(false);
            findViewById(R.id.buttonTwoPlayer).setClickable(false);
        }
    };
    private final View.OnClickListener colorListenerTwo = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.colorMagenta: {
                    mPlayerTwoColor = 0xFFFF00DC;
                    mPlayerTwoWeakColor = 0xAAFF00DC;
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorBlue: {
                    mPlayerTwoColor = 0xFF004EFF;
                    mPlayerTwoWeakColor = 0xAA004EFF;
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorLTGray: {
                    mPlayerTwoColor = 0xFFA0A0A0;
                    mPlayerTwoWeakColor = 0xAAA0A0A0;
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorCyan: {
                    mPlayerTwoColor = 0xFF7FFFFF;
                    mPlayerTwoWeakColor = 0xAA7FFFFF;
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
            }
            Button[] buttons2 = {(Button) findViewById(R.id.colorMagenta), (Button) findViewById(R.id.colorBlue), (Button) findViewById(R.id.colorLTGray), (Button) findViewById(R.id.colorCyan)};
            for (Button b : buttons2) {
                b.setEnabled(false);
                b.setClickable(false);
                b.setOnClickListener(null);
            }
            findViewById(R.id.startTTTButton).setOnClickListener(startListener);
        }
    };
    private final View.OnClickListener colorListenerOne = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.colorBlack: {
                    mPlayerOneColor = 0xFF000000;
                    mPlayerOneWeakColor = 0xFFA0A0A0; // Gray, because black with less alpha is still black
                    changeState();
                    TextView colorPlayerTwoView = (TextView) findViewById(R.id.colorPlayerTwoView);
                    if (mMultiplayer) {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mNamePlayerTwo));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mNamePlayerOne));
                    }
                    ((Button) v).setText("X");
                    break;
                }
                case R.id.colorYellow: {
                    mPlayerOneColor = 0xFFFFD800;
                    mPlayerOneWeakColor = 0xAAFFD800;
                    changeState();
                    TextView colorPlayerTwoView = (TextView) findViewById(R.id.colorPlayerTwoView);
                    if (mMultiplayer) {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mNamePlayerTwo));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mNamePlayerOne));
                    }
                    ((Button) v).setText("X");
                    break;
                }
                case R.id.colorRed: {
                    mPlayerOneColor = 0xFFFF0000;
                    mPlayerOneWeakColor = 0xAAFF0000;
                    changeState();
                    TextView colorPlayerTwoView = (TextView) findViewById(R.id.colorPlayerTwoView);
                    if (mMultiplayer) {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mNamePlayerTwo));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mNamePlayerOne));
                    }
                    ((Button) v).setText("X");
                    break;
                }
                case R.id.colorGreen: {
                    mPlayerOneColor = 0xFF00FF21;
                    mPlayerOneWeakColor = 0xAA00FF21;
                    changeState();
                    TextView colorPlayerTwoView = (TextView) findViewById(R.id.colorPlayerTwoView);
                    if (mMultiplayer) {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mNamePlayerTwo));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mNamePlayerOne));
                    }
                    ((Button) v).setText("X");
                    break;
                }
            }
            Button[] buttons = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow), (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
            for (Button b : buttons) {
                b.setEnabled(false);
                b.setClickable(false);
                b.setOnClickListener(null);
            }

            Button[] buttons2 = {(Button) findViewById(R.id.colorMagenta), (Button) findViewById(R.id.colorBlue), (Button) findViewById(R.id.colorLTGray), (Button) findViewById(R.id.colorCyan)};
            for (Button b : buttons2) {
                b.setOnClickListener(colorListenerTwo);
            }
        }
    };
    private int mAiLevel;
    private TextView[] mGrid;
    private final View.OnClickListener gridListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (((TextView) v).getText().equals("")) {
                if (mPlayersTurn == 1) {
                    ((TextView) v).setTextColor(mPlayerOneColor);
                    ((TextView) v).setText("×");
                    String htmlText = String.format("<font color=%s>%s</font>", mPlayerTwoColor, mNamePlayerTwo);
                    ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_turn, Html.fromHtml(htmlText)));
                    mPlayersTurn = 2;
                } else if (mPlayersTurn == 2) {
                    String htmlText = String.format("<font color=%s>%s</font>", mPlayerOneColor, mNamePlayerOne);
                    ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_turn, Html.fromHtml(htmlText)));
                    ((TextView) v).setTextColor(mPlayerTwoColor);
                    ((TextView) v).setText("O");
                    mPlayersTurn = 1;
                } else {
                    ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_draw));

                    findViewById(R.id.replayButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.replayButton).setOnClickListener(startListener);
                }
                if (checkWin()) {
                    endGame("win");
                } else if (mPlayersTurn == DRAW) {
                    endGame("draw");
                }
            } else {
                Errors.errorToast(mContext, mContext.getString(R.string.game_ttt_already_filled));
            }
        }
    };
    private final View.OnClickListener setAiLevelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.aiLevelEasy: {
                    mAiLevel = EASY;
                }
                case R.id.aiLevelMedium: {
                    mAiLevel = NORMAL;
                }
                case R.id.aiLevelHard: {
                    mAiLevel = HARD;
                }
            }
            iniGame();
        }
    };
    private final View.OnClickListener startListener = new View.OnClickListener() {
        public void onClick(View v) {
            setContentView(R.layout.game_tic_tac_toe);
            if (!mMultiplayer) {
                findViewById(R.id.lastSettingWrapper).setVisibility(View.VISIBLE);
                View[] views = {findViewById(R.id.aiLevelEasy), findViewById(R.id.aiLevelMedium), findViewById(R.id.aiLevelHard)};
                for (View v2 : views) {
                    v2.setOnClickListener(setAiLevelListener);
                }
            } else {
                iniGame();
            }
        }
    };

    private void endGame(String status) {
        String winnerName;
        int winnerColor;
        TextView turnView = (TextView) findViewById(R.id.turnView);
        switch (status) {
            case "win": {
                if (mPlayersTurn == 1) {
                    winnerName = mNamePlayerTwo;
                    winnerColor = mPlayerTwoWeakColor;
                } else {
                    winnerName = mNamePlayerOne;
                    winnerColor = mPlayerOneWeakColor;
                }
                turnView.setText(mContext.getString(R.string.game_ttt_player_won, winnerName));
                turnView.setTextColor(winnerColor);
                enableNewGame();
                break;
            }
            case "draw": {
                turnView.setText(mContext.getString(R.string.game_ttt_draw));
                turnView.setTextColor(0xFFFF8000); // Orange, not used in game, so it's good as a color for a draw
                enableNewGame();
                break;
            }
        }
        // Disable the game area
        for (TextView tv : mGrid) {
            tv.setEnabled(false);
            tv.setOnClickListener(nullListener);
        }
    }

    private void enableNewGame() {
        Button newGameButton = (Button) findViewById(R.id.replayButton);
        newGameButton.setVisibility(View.VISIBLE);
        newGameButton.setOnClickListener(startListener);
    }

    private void iniGame() { // FIXME: 18.08.2015 Get the turnView to display the string in two Colors.
        mPlayersTurn = (int) (Math.random() * 10);
        findViewById(R.id.lastSettingWrapper).setVisibility(View.GONE);
        findViewById(R.id.gameWrapper).setVisibility(View.VISIBLE);
        String htmlText;
        if (mPlayersTurn >= 5) {
            htmlText = String.format("<font color=%s>%s</font>", mPlayerOneColor, mNamePlayerOne);
            mPlayersTurn = 1;
        } else {
            htmlText = String.format("<font color=%s>%s</font>", mPlayerTwoColor, mNamePlayerTwo);
            mPlayersTurn = 2;
        }
        ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_turn, Html.fromHtml(htmlText)));

        GridLayout battleField = (GridLayout) findViewById(R.id.battleField);
        int textViewCount = battleField.getChildCount();
        mGrid = new TextView[textViewCount];

        for (int i = 0; i < textViewCount; i++) {
            mGrid[i] = (TextView) battleField.getChildAt(i);
            mGrid[i].setText("");
            mGrid[i].setOnClickListener(gridListener);
        }
    }

    private boolean checkWin() {
        char[] gridSigns = new char[9];
        int filled = 0;
        for (int i = 0; i < 9; i++) {
            if (!mGrid[i].getText().toString().equals("")) {
                gridSigns[i] = mGrid[i].getText().toString().charAt(0);
                ++filled;
            }
        }
        if ((gridSigns[0] == gridSigns[1]) && (gridSigns[1] == gridSigns[2]) && (gridSigns[0] != '\u0000')) {         // First Row
            colorWinningLineBackgrounds(0, 1, 2);
            return true;
        } else if ((gridSigns[3] == gridSigns[4]) && (gridSigns[4] == gridSigns[5]) && (gridSigns[3] != '\u0000')) {  // Second Row
            colorWinningLineBackgrounds(3, 4, 5);
            return true;
        } else if ((gridSigns[6] == gridSigns[7]) && (gridSigns[7] == gridSigns[8]) && (gridSigns[6] != '\u0000')) {  // Third Row
            colorWinningLineBackgrounds(6, 7, 8);
            return true;
        } else if ((gridSigns[0] == gridSigns[3]) && (gridSigns[3] == gridSigns[6]) && (gridSigns[0] != '\u0000')) {  // First Column
            colorWinningLineBackgrounds(0, 3, 6);
            return true;
        } else if ((gridSigns[1] == gridSigns[4]) && (gridSigns[4] == gridSigns[7]) && (gridSigns[1] != '\u0000')) {  // Second Column
            colorWinningLineBackgrounds(1, 4, 7);
            return true;
        } else if ((gridSigns[2] == gridSigns[5]) && (gridSigns[5] == gridSigns[8]) && (gridSigns[2] != '\u0000')) {  // Third Column
            colorWinningLineBackgrounds(2, 5, 8);
            return true;
        } else if ((gridSigns[0] == gridSigns[4]) && (gridSigns[4] == gridSigns[8]) && (gridSigns[0] != '\u0000')) {  // First Diagonal
            colorWinningLineBackgrounds(0, 4, 8);
            return true;
        } else if ((gridSigns[2] == gridSigns[4]) && (gridSigns[4] == gridSigns[6]) && (gridSigns[2] != '\u0000')) {  // Second Diagonal
            colorWinningLineBackgrounds(2, 4, 6);
            return true;
        } else {
            if (filled > 8) {
                mPlayersTurn = DRAW;
            }
            return false;
        }
    }

    private void colorWinningLineBackgrounds(int gridOne, int gridTwo, int gridThree) {
        if (mPlayersTurn == 1) {
            mGrid[gridOne].setBackgroundColor(mPlayerTwoWeakColor);
            mGrid[gridTwo].setBackgroundColor(mPlayerTwoWeakColor);
            mGrid[gridThree].setBackgroundColor(mPlayerTwoWeakColor);
        } else {
            mGrid[gridOne].setBackgroundColor(mPlayerOneWeakColor);
            mGrid[gridTwo].setBackgroundColor(mPlayerOneWeakColor);
            mGrid[gridThree].setBackgroundColor(mPlayerOneWeakColor);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tic_tac_toe_settings);

        mTableRows = new TableRow[]{(TableRow) findViewById(R.id.playerCountWrapper),
                (TableRow) findViewById(R.id.namePlayerOne),
                (TableRow) findViewById(R.id.namePlayerTwo),
                (TableRow) findViewById(R.id.colorPlayerOne),
                (TableRow) findViewById(R.id.colorPlayerTwo),
                (TableRow) findViewById(R.id.startRow)};

        mButtons = new Button[]{(Button) findViewById(R.id.buttonOnePlayer),
                (Button) findViewById(R.id.buttonTwoPlayer)};

        for (Button b : mButtons) {
            b.setOnClickListener(btnListener);
        }
    }

    @Override
    public void onBackPressed() {
        if (mStage == 0) {
            super.onBackPressed();
        } else if (mStage >= 5) {
            Resources res = mContext.getResources();
            new AlertDialog.Builder(this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(res.getString(R.string.game_ttt_leave_title))
                    .setMessage(res.getString(R.string.game_ttt_leave_message))
                    .setPositiveButton(res.getString(R.string.popup_positive_button_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stupidHelperBecauseOfStuff();
                        }

                    })
                    .setNegativeButton(R.string.popup_negative_button_text, null)
                    .show();
        } else {
            revertOneStep();
        }
    }

    private void stupidHelperBecauseOfStuff() {
        super.onBackPressed();
    }

    private void revertOneStep() {
        mTableRows[mStage].setVisibility(View.GONE);
        mStage--;
        mTableRows[mStage].setAlpha(1.0f);
        switch (mStage) {
            case 0: {
                for (Button b : mButtons) {
                    b.setBackgroundColor(Color.TRANSPARENT);
                    b.setEnabled(true);
                    b.setClickable(true);
                }
            }
            case 1: {
                TextView tv = (TextView) findViewById(R.id.playerOneName);
                tv.setText("");
                tv.setEnabled(true);
                tv.setClickable(true);
                mNamePlayerOne = "";
            }
            case 2: {
                TextView tv = (TextView) findViewById(R.id.playerTwoName);
                tv.setText("");
                tv.setEnabled(true);
                tv.setClickable(true);
                mNamePlayerTwo = "";
            }
            case 3: {
                Button[] buttons = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow), (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
                for (Button b : buttons) {
                    b.setEnabled(true);
                    b.setClickable(true);
                    b.setText("");
                }
            }
            case 4: {
                Button[] buttons = {(Button) findViewById(R.id.colorMagenta), (Button) findViewById(R.id.colorBlue), (Button) findViewById(R.id.colorLTGray), (Button) findViewById(R.id.colorCyan)};
                for (Button b : buttons) {
                    b.setText("");
                }
            }
        }
    }

    private void changeState() {
        mTableRows[mStage].setAlpha(0.3f);
        mTableRows[mStage].setClickable(false);
        mTableRows[mStage].setEnabled(false);
        mStage++;
        mTableRows[mStage].setVisibility(View.VISIBLE);
    }
}