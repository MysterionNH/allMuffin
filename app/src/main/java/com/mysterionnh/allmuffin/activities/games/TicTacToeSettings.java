package com.mysterionnh.allmuffin.activities.games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.BaseActivity;

public class TicTacToeSettings extends BaseActivity {

    private Context mContext = (Context) this;

    private TableRow[] mTableRows;
    private Button[] mButtons;
    private LinearLayout mMPWrapper;
    private LinearLayout mSPWrapper;
    private int mStage;
    private boolean mMultiplayer;
    private String mPlayerOneName;
    private String mPlayerTwoName;
    private int mPlayerOneColor;
    private int mPlayerOneWeakColor;
    private int mPlayerTwoColor;
    private int mPlayerTwoWeakColor;

    // TODO : Clean up everything here. I made this and I have real problems to understand what's happening. Fix asap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tic_tac_toe_settings);

        mMPWrapper = ((LinearLayout) findViewById(R.id.multiplayerWrapper));
        mSPWrapper = ((LinearLayout) findViewById(R.id.singleplayerWrapper));

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

        // Register the start game button
        findViewById(R.id.startTTTButton).setOnClickListener(startListener);
    }

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
                                mPlayerOneName = tv.getText().toString();
                                tv.setEnabled(false);
                                tv.setClickable(false);
                                TextView colorPlayerOneView = (TextView) findViewById(R.id.colorPlayerOneViewSP);
                                colorPlayerOneView.setText(mContext.getString(R.string.game_ttt_settings_player_one_color, mPlayerOneName));
                                Button[] colorButtonsOne = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow),
                                        (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
                                for (Button b : colorButtonsOne) {
                                    b.setOnClickListener(colorListenerOne);
                                }
                                mSPWrapper.setVisibility(View.VISIBLE);
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
                    tv.setOnEditorActionListener(onEnter);
                    break;
                }
            }
            findViewById(R.id.buttonOnePlayer).setClickable(false);
            findViewById(R.id.buttonTwoPlayer).setClickable(false);
        }
    };

    TextView.OnEditorActionListener onEnter = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v.getId() == R.id.playerOneName) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    mPlayerOneName = v.getText().toString();
                    v.setEnabled(false);
                    v.setClickable(false);
                    mTableRows[mStage].setAlpha(0.3f);
                    mTableRows[mStage].setClickable(false);
                    mTableRows[mStage].setEnabled(false);
                    mStage++;
                    mMPWrapper.setVisibility(View.VISIBLE);
                    final TextView tv = (TextView) findViewById(R.id.playerTwoName);
                    tv.setOnEditorActionListener(onEnter);
                }
            } else {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    mPlayerTwoName = v.getText().toString();
                    v.setEnabled(false);
                    v.setClickable(false);
                    changeState();
                    TextView colorPlayerOneView = (TextView) findViewById(R.id.colorPlayerOneView);
                    colorPlayerOneView.setText(mContext.getString(R.string.game_ttt_settings_player_one_color, mPlayerOneName));
                    Button[] colorButtonsOne = {(Button) findViewById(R.id.colorBlack), (Button) findViewById(R.id.colorYellow),
                            (Button) findViewById(R.id.colorRed), (Button) findViewById(R.id.colorGreen)};
                    for (Button b : colorButtonsOne) {
                        b.setOnClickListener(colorListenerOne);
                    }
                }
                return false;
            }
            return false;
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
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mPlayerTwoName));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mPlayerOneName));
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
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mPlayerTwoName));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mPlayerOneName));
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
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mPlayerTwoName));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mPlayerOneName));
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
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mPlayerTwoName));
                    } else {
                        colorPlayerTwoView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color_ai, mPlayerOneName));
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

            if (mMultiplayer) {
                Button[] buttons2 = {(Button) findViewById(R.id.colorMagenta), (Button) findViewById(R.id.colorBlue), (Button) findViewById(R.id.colorLTGray), (Button) findViewById(R.id.colorCyan)};
                for (Button b : buttons2) {
                    b.setOnClickListener(colorListenerTwo);
                }
            } else {
                startNow();
            }
        }
    };

    private void startNow() {
        (findViewById(R.id.startRow)).setVisibility(View.VISIBLE);
    }

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
        }
    };

    private final View.OnClickListener startListener = new View.OnClickListener() {
        public void onClick(View v) {
            // Get the SharedPreferences we use to save the game settings temporally and edit them
            SharedPreferences.Editor tttSettings = getSharedPreferences(mContext.getString(R.string.ttt_prefs_key), Context.MODE_PRIVATE).edit();
            tttSettings.clear(); // First clear it
            // Now add all needed data
            tttSettings.putString(mContext.getString(R.string.ttt_pref_player_one_name), mPlayerOneName);
            tttSettings.putInt(mContext.getString(R.string.ttt_pref_player_one_color), mPlayerOneColor);
            tttSettings.putInt(mContext.getString(R.string.ttt_pref_player_one_color_weak), mPlayerOneWeakColor);
            if (mMultiplayer) {
                tttSettings.putString(mContext.getString(R.string.ttt_pref_player_two_name), mPlayerTwoName);
                tttSettings.putInt(mContext.getString(R.string.ttt_pref_player_two_color), mPlayerTwoColor);
                tttSettings.putInt(mContext.getString(R.string.ttt_pref_player_two_color_weak), mPlayerTwoWeakColor);
            }
            tttSettings.putBoolean(mContext.getString(R.string.ttt_pref_multiplayer), mMultiplayer);
            // Commit
            tttSettings.commit();
            startActivity(new Intent(mContext, TicTacToeActivity.class));
        }
    };

    @Override
    public void onBackPressed() {
        if (mStage != 0)
            revertOneStep();
        else
            super.onBackPressed();
    }

    private void revertOneStep() { // TODO: Completely broken due to the changes on how stuff is made visible
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
                mPlayerOneName = "";
            }
            case 2: {
                TextView tv = (TextView) findViewById(R.id.playerTwoName);
                tv.setText("");
                tv.setEnabled(true);
                tv.setClickable(true);
                mPlayerTwoName = "";
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

    // When the back button is hold, return to the game hub
    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            super.onBackPressed();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private void changeState() {
        mTableRows[mStage].setAlpha(0.3f);
        mTableRows[mStage].setClickable(false);
        mTableRows[mStage].setEnabled(false);
        mStage++;
        mTableRows[mStage].setVisibility(View.VISIBLE);
    }
}
