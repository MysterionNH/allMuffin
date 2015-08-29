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
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.BaseActivity;
import com.mysterionnh.allmuffin.helper.Errors;

public class TicTacToeSettings extends BaseActivity {

    private final Context mContext = (Context) this;

    private TableRow countRow;
    private TableRow oneNameRow;
    private TableRow oneColorRow;
    private TableRow twoNameRow;
    private TableRow twoColorRow;
    private TableRow startRow;

    private Button singlePlayer;
    private Button multiPlayer;

    private Button[] oneColorButtons;
    private Button[] twoColorButtons;

    private EditText oneNameView;
    private EditText twoNameView;

    private TextView twoColorView;
    private TextView oneColorView;

    private final float DISABLED = 0.3f;
    private final float ENABLED = 1.0f;

    private int mStage;
    private boolean mMultiplayer;
    private String mPlayerOneName;
    private String mPlayerTwoName;
    private int mPlayerOneColor;
    private int mPlayerOneWeakColor;
    private int mPlayerTwoColor;
    private int mPlayerTwoWeakColor;

    private boolean initialized = false;

    // TODO : Clean up everything here. I made this and I have real problems to understand what's happening. Fix asap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tic_tac_toe_settings);

        iniActivity();
    }

    private void iniActivity() {
        if (!initialized) {
            initialized = true;

            mStage = 0;

            countRow = (TableRow) findViewById(R.id.tttCountRow);
            oneNameRow = (TableRow) findViewById(R.id.tttOneNameRow);
            oneColorRow = (TableRow) findViewById(R.id.tttOneColorRow);
            twoNameRow = (TableRow) findViewById(R.id.tttTwoNameRow);
            twoColorRow = (TableRow) findViewById(R.id.tttTwoColorRow);
            startRow = (TableRow) findViewById(R.id.tttStartRow);

            singlePlayer = (Button) findViewById(R.id.buttonOnePlayer);
            multiPlayer = (Button) findViewById(R.id.buttonTwoPlayer);
            Button newGame = (Button) findViewById(R.id.startTTTButton);

            singlePlayer.setOnClickListener(btnListener);
            multiPlayer.setOnClickListener(btnListener);
            newGame.setOnClickListener(startListener);

            oneColorButtons = new Button[]{(Button) findViewById(R.id.colorBlack),
                    (Button) findViewById(R.id.colorYellow),
                    (Button) findViewById(R.id.colorRed),
                    (Button) findViewById(R.id.colorGreen)};

            for (Button b : oneColorButtons) {
                b.setOnClickListener(colorListenerOne);
            }

            twoColorButtons = new Button[]{(Button) findViewById(R.id.colorMagenta),
                    (Button) findViewById(R.id.colorBlue),
                    (Button) findViewById(R.id.colorGray),
                    (Button) findViewById(R.id.colorCyan)};

            for (Button b : twoColorButtons) {
                b.setOnClickListener(colorListenerTwo);
            }

            oneNameView = (EditText) findViewById(R.id.playerOneName);
            twoNameView = (EditText) findViewById(R.id.playerTwoName);

            oneNameView.setOnEditorActionListener(onEnter);
            twoNameView.setOnEditorActionListener(onEnter);

            oneColorView = (TextView) findViewById(R.id.colorPlayerOneView);
            twoColorView = (TextView) findViewById(R.id.colorPlayerTwoView);

        } else
            Errors.logWarning(mContext, "Activity was already initialized!");
    }

    private final View.OnClickListener btnListener = new View.OnClickListener() {

        public void onClick(View v) {
            mMultiplayer = (v.getId() == R.id.buttonTwoPlayer);

            v.setBackgroundColor(Color.TRANSPARENT);
            countRow.setAlpha(DISABLED);
            // countRow.setClickable(false);

            oneNameRow.setVisibility(View.VISIBLE);

            singlePlayer.setClickable(false);
            multiPlayer.setClickable(false);
            mStage++;
        }
    };

    private final TextView.OnEditorActionListener onEnter = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v.getId() == R.id.playerOneName && ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE))) {
                mPlayerOneName = v.getText().toString();
                oneNameRow.setAlpha(DISABLED);
                oneColorView.setText(mContext.getString(R.string.game_ttt_settings_player_one_color, mPlayerOneName));
                oneColorRow.setVisibility(View.VISIBLE);
            } else {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    mPlayerTwoName = v.getText().toString();
                    twoNameRow.setAlpha(DISABLED);
                    twoColorView.setText(mContext.getString(R.string.game_ttt_settings_player_two_color, mPlayerTwoName));
                    twoColorRow.setVisibility(View.VISIBLE);
                }
            }
            v.setEnabled(false);
            v.setClickable(false);
            mStage++;
            return false;
        }
    };

    private final View.OnClickListener colorListenerOne = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.colorBlack: {
                    mPlayerOneColor = 0xFF000000;
                    mPlayerOneWeakColor = 0xFFA0A0A0; // Gray, because black with less alpha is still black
                    break;
                }
                case R.id.colorYellow: {
                    mPlayerOneColor = 0xFFFFD800;
                    mPlayerOneWeakColor = 0xAAFFD800;
                    break;
                }
                case R.id.colorRed: {
                    mPlayerOneColor = 0xFFFF0000;
                    mPlayerOneWeakColor = 0xAAFF0000;
                    break;
                }
                case R.id.colorGreen: {
                    mPlayerOneColor = 0xFF00FF21;
                    mPlayerOneWeakColor = 0xAA00FF21;
                    break;
                }
            }

            for (Button b : oneColorButtons) {
                b.setEnabled(false);
                b.setClickable(false);
                b.setText("");
            }

            ((Button) v).setText("X");
            oneColorRow.setAlpha(DISABLED);

            if (mMultiplayer) {
                twoNameRow.setVisibility(View.VISIBLE);
            } else {
                // Start now
                startRow.setVisibility(View.VISIBLE);
            }
            mStage++;
        }
    };

    private final View.OnClickListener colorListenerTwo = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.colorMagenta: {
                    mPlayerTwoColor = 0xFFFF00DC;
                    mPlayerTwoWeakColor = 0xAAFF00DC;
                    break;
                }
                case R.id.colorBlue: {
                    mPlayerTwoColor = 0xFF004EFF;
                    mPlayerTwoWeakColor = 0xAA004EFF;
                    break;
                }
                case R.id.colorGray: {
                    mPlayerTwoColor = 0xFFA0A0A0;
                    mPlayerTwoWeakColor = 0xAAA0A0A0;
                    break;
                }
                case R.id.colorCyan: {
                    mPlayerTwoColor = 0xFF7FFFFF;
                    mPlayerTwoWeakColor = 0xAA7FFFFF;
                    break;
                }
            }

            for (Button b : twoColorButtons) {
                b.setText("");
                b.setEnabled(false);
                b.setClickable(false);
            }

            ((Button) v).setText("O");
            twoColorRow.setAlpha(DISABLED);
            startRow.setVisibility(View.VISIBLE);

            mStage++;
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

            startRow.setVisibility(View.GONE);

            // Re-enable last changed setting
            if (mMultiplayer) {
                twoColorRow.setAlpha(ENABLED);
                for (Button b : twoColorButtons) {
                    b.setEnabled(true);
                    b.setClickable(true);
                }
                mStage--;
            } else {
                oneColorRow.setAlpha(ENABLED);
                for (Button b : oneColorButtons) {
                    b.setEnabled(true);
                    b.setClickable(true);
                }
                mStage = 2;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (mStage != 0)
            revertOneStep();
        else
            super.onBackPressed();
    }

    private void revertOneStep() {
        switch (mStage) {
            case 1: {
                oneNameRow.setVisibility(View.GONE);

                countRow.setAlpha(ENABLED);
                singlePlayer.setClickable(true);
                singlePlayer.setBackgroundColor(Color.TRANSPARENT);
                multiPlayer.setClickable(true);
                multiPlayer.setBackgroundColor(Color.TRANSPARENT);
                mStage--;
                break;
            }
            case 2: {
                oneColorRow.setVisibility(View.GONE);

                oneNameRow.setAlpha(ENABLED);
                oneNameView.setEnabled(true);
                oneNameView.setClickable(true);
                mStage--;
                break;
            }
            case 3: {
                twoNameRow.setVisibility(View.GONE);

                for (Button b : twoColorButtons) {
                    b.setText("");
                }

                oneColorRow.setAlpha(ENABLED);
                for (Button b : oneColorButtons) {
                    b.setEnabled(true);
                    b.setClickable(true);
                }
                mStage--;
                break;
            }
            case 4: {
                twoColorRow.setVisibility(View.GONE);

                twoNameRow.setAlpha(ENABLED);
                twoNameView.setEnabled(true);
                twoNameView.setClickable(true);
                mStage--;
                break;
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
}
