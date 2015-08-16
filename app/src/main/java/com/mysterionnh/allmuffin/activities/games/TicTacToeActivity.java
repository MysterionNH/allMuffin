package com.mysterionnh.allmuffin.activities.games;

import android.content.Context;
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
    Context mContext = (Context) this;
    boolean mMultiplayer = false;
    TableRow[] mTableRows;
    Button[] mButtons;
    String mNamePlayerOne;
    String mNamePlayerTwo;
    int mPlayerOneColor;
    int mPlayerTwoColor;
    private int playersTurn;
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
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorBlue: {
                    mPlayerTwoColor = 0xFF004EFF;
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorLTGray: {
                    mPlayerTwoColor = 0xFFA0A0A;
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorCyan: {
                    mPlayerTwoColor = 0xFF7FFFFF;
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
                    mPlayerOneColor = 0XFFFF0000;
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
                if (playersTurn == 1) {
                    ((TextView) v).setTextColor(mPlayerOneColor);
                    ((TextView) v).setText("×");
                    String htmlText = String.format("<font color=%s>%s</font>", mPlayerTwoColor, mNamePlayerTwo);
                    ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_turn, Html.fromHtml(htmlText)));
                    playersTurn = 2;
                } else if (playersTurn == 2) {
                    String htmlText = String.format("<font color=%s>%s</font>", mPlayerOneColor, mNamePlayerOne);
                    ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_turn, Html.fromHtml(htmlText)));
                    ((TextView) v).setTextColor(mPlayerTwoColor);
                    ((TextView) v).setText("O");
                    playersTurn = 1;
                } else {
                    ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_draw));

                    findViewById(R.id.replayButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.replayButton).setOnClickListener(startListener);
                }
                if (checkWin()) {
                    if (playersTurn == 1) {
                        ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_player_won, mNamePlayerTwo));
                        ((TextView) findViewById(R.id.turnView)).setTextColor(mPlayerTwoColor);

                        findViewById(R.id.replayButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.replayButton).setOnClickListener(startListener);
                    } else {
                        ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_player_won, mNamePlayerOne));
                        ((TextView) findViewById(R.id.turnView)).setTextColor(mPlayerOneColor);

                        findViewById(R.id.replayButton).setVisibility(View.VISIBLE);
                        findViewById(R.id.replayButton).setOnClickListener(startListener);
                    }
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

    private void iniGame() {
        playersTurn = (int) (Math.random() * 10);
        findViewById(R.id.lastSettingWrapper).setVisibility(View.GONE);
        findViewById(R.id.gameWrapper).setVisibility(View.VISIBLE);
        String htmlText;
        if (playersTurn >= 5) {
            htmlText = String.format("<font color=%s>%s</font>", mPlayerOneColor, mNamePlayerOne);
            playersTurn = 1;
        } else {
            htmlText = String.format("<font color=%s>%s</font>", mPlayerTwoColor, mNamePlayerTwo);
            playersTurn = 2;
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
            return true;
        } else if ((gridSigns[3] == gridSigns[4]) && (gridSigns[4] == gridSigns[5]) && (gridSigns[3] != '\u0000')) {  // Second Row
            return true;
        } else if ((gridSigns[6] == gridSigns[7]) && (gridSigns[7] == gridSigns[8]) && (gridSigns[6] != '\u0000')) {  // Third Row
            return true;
        } else if ((gridSigns[0] == gridSigns[3]) && (gridSigns[3] == gridSigns[6]) && (gridSigns[0] != '\u0000')) {  // First Column
            return true;
        } else if ((gridSigns[1] == gridSigns[4]) && (gridSigns[4] == gridSigns[7]) && (gridSigns[1] != '\u0000')) {  // Second Column
            return true;
        } else if ((gridSigns[2] == gridSigns[5]) && (gridSigns[5] == gridSigns[8]) && (gridSigns[2] != '\u0000')) {  // Third Column
            return true;
        } else if ((gridSigns[0] == gridSigns[4]) && (gridSigns[4] == gridSigns[8]) && (gridSigns[0] != '\u0000')) {  // First Diagonal
            return true;
        } else if ((gridSigns[2] == gridSigns[4]) && (gridSigns[4] == gridSigns[6]) && (gridSigns[2] != '\u0000')) {  // Second Diagonal
            return true;
        } else {
            if (filled == 8) {
                playersTurn = DRAW;
            }
            return false;
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
        if (mStage == 0 || mStage >= 5) {
            super.onBackPressed();
        } else {
            revertOneStep();
        }
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