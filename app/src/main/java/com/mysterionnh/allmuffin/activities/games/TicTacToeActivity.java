package com.mysterionnh.allmuffin.activities.games;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.BaseActivity;

public class TicTacToeActivity extends BaseActivity {

    /**
     * ------------------------------------------------------ Everything below this line handles the game itself, everything above the settings ------------------------------------------------------
     **/

    private final int EASY = 0;
    private final int NORMAL = 1;
    private final int HARD = 2;
    Context mContext = (Context) this;
    boolean mMultiplayer = false;
    TableRow[] mTableRows;
    Button[] mButtons;
    String mNamePlayerOne;
    String mNamePlayerTwo;
    String mPlayerOneColor;
    String mPlayerTwoColor;
    private int mStage = 0;
    private final View.OnClickListener colorListenerOne = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.colorBlack: {
                    mPlayerOneColor = "#000";
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
                    mPlayerOneColor = "#FFD800";
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
                    mPlayerOneColor = "#FF0000";
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
                    mPlayerOneColor = "#00FF21";
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
    private int mAiLevel;
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
            findViewById(R.id.lastSettingWrapper).setVisibility(View.GONE);
            findViewById(R.id.gameWrapper).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.turnView)).setText(mContext.getString(R.string.game_ttt_turn, Html.fromHtml("<font color=" + mPlayerOneColor + ">" + mNamePlayerOne + "</font>")));
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
            }
        }
    };
    private final View.OnClickListener colorListenerTwo = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.colorMagenta: {
                    mPlayerTwoColor = "#FF00DC";
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorBlue: {
                    mPlayerTwoColor = "#004EFF";
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorLTGray: {
                    mPlayerTwoColor = "#A0A0A0";
                    changeState();
                    ((Button) v).setText("O");
                    break;
                }
                case R.id.colorCyan: {
                    mPlayerTwoColor = "#7FFFFF";
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
                mPlayerOneColor = "";
                Button[] buttons = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow), (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
                for (Button b : buttons) {
                    b.setEnabled(true);
                    b.setClickable(true);
                    b.setText("");
                }
            }
            case 4: {
                mPlayerTwoColor = "";
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