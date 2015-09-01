package com.mysterionnh.allmuffin.activities.games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.BaseActivity;
import com.mysterionnh.allmuffin.helper.Errors;

import java.util.Random;

public class TicTacToeActivity extends BaseActivity {

    private final Context mContext = (Context) this;

    private TextView[] mGrid;
    private Button mNewGameButton;
    private TextView mTurnView;
    private String mPlayerOneName;
    private String mPlayerTwoName;
    private int mPlayerOneColor;
    private int mPlayerOneWeakColor;
    private int mPlayerTwoColor;
    private int mPlayerTwoWeakColor;
    private int mPlayersTurn;
    private boolean mMultiplayer;
    private int mTurnCount;

    private boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tic_tac_toe);

        iniActivity();
    }

    private void iniActivity() {
        if (!initialized) {
            initialized = true; // Make sure this only happens once, because onCreate is always called when the screen rotates
            mTurnView = ((TextView) findViewById(R.id.turnView));
            mNewGameButton = (Button) findViewById(R.id.replayButton);
            mNewGameButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    iniGame();
                }
            });

            SharedPreferences gameSettings = getSharedPreferences(mContext.getString(R.string.ttt_prefs_key), Context.MODE_PRIVATE);

            mMultiplayer = gameSettings.getBoolean(mContext.getString(R.string.ttt_pref_multiplayer), false);

            mPlayerOneName = gameSettings.getString(mContext.getString(R.string.ttt_pref_player_one_name), "Player 1");
            mPlayerOneColor = gameSettings.getInt(mContext.getString(R.string.ttt_pref_player_one_color), 0xFF000000);
            mPlayerOneWeakColor = gameSettings.getInt(mContext.getString(R.string.ttt_pref_player_one_color_weak), 0xFFA0A0A0);
            if (mMultiplayer) {
                mPlayerTwoName = gameSettings.getString(mContext.getString(R.string.ttt_pref_player_two_name), "Player 2");
                mPlayerTwoColor = gameSettings.getInt(mContext.getString(R.string.ttt_pref_player_two_color), 0xFF000000);
                mPlayerTwoWeakColor = gameSettings.getInt(mContext.getString(R.string.ttt_pref_player_two_color_weak), 0xFFA0A0A0);
            } else {
                mPlayerTwoName = gameSettings.getString(mContext.getString(R.string.ttt_pref_player_two_name), "COM");
                mPlayerTwoColor = gameSettings.getInt(mContext.getString(R.string.ttt_pref_player_two_color), 0xFF7FFFFF);
                mPlayerTwoWeakColor = gameSettings.getInt(mContext.getString(R.string.ttt_pref_player_two_color_weak), 0xFF18C2FF);
            }
            iniGame();
        } else
            Errors.logWarning(mContext, "Activity was already initialized!");
    }

    private void iniGame() {
        mTurnCount = 0;
        mNewGameButton.setVisibility(View.GONE);
        mTurnView.setTextColor(Color.BLACK);
        Random rand = new Random(System.currentTimeMillis());
        mPlayersTurn = rand.nextInt(10);
        String htmlText;

        if (mPlayersTurn >= 5 || !mMultiplayer) {
            htmlText = String.format(mContext.getString(R.string.game_ttt_turn),
                    String.format("<font color=%s>%s</font>", convertIntegerToHTMLColorCode(mPlayerOneColor), mPlayerOneName));
            mPlayersTurn = 1;
        } else {
            htmlText = String.format(mContext.getString(R.string.game_ttt_turn),
                    String.format("<font color=%s>%s</font>", convertIntegerToHTMLColorCode(mPlayerTwoColor), mPlayerTwoName));
            mPlayersTurn = 2;
        }

        mTurnView.setText(Html.fromHtml(htmlText), TextView.BufferType.SPANNABLE);

        GridLayout battleField = (GridLayout) findViewById(R.id.battleField);
        int textViewCount = battleField.getChildCount();
        mGrid = new TextView[textViewCount];

        for (int i = 0; i < textViewCount; i++) {
            mGrid[i] = (TextView) battleField.getChildAt(i);
            mGrid[i].setText("");
            mGrid[i].setBackgroundColor(Color.WHITE);
            mGrid[i].setEnabled(true);
            mGrid[i].setOnClickListener(gridListener);
        }
    }

    private final View.OnClickListener gridListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (((TextView) v).getText().toString().equals("")) {
                turn(v);
                checkWin();
            } else {
                Errors.errorToast(mContext, mContext.getString(R.string.game_ttt_already_filled));
            }
        }
    };

    private void turn(View v) {
        String htmlText;
        if (mPlayersTurn == 1) {
            mTurnCount++;
            ((TextView) v).setTextColor(mPlayerOneColor);
            ((TextView) v).setText("×");
            htmlText = String.format(mContext.getString(R.string.game_ttt_turn),
                    String.format("<font color=%s>%s</font>", convertIntegerToHTMLColorCode(mPlayerTwoColor), mPlayerTwoName));
            mTurnView.setText(Html.fromHtml(htmlText), TextView.BufferType.SPANNABLE);
            mPlayersTurn = 2;
            if (!mMultiplayer && !(mTurnCount > 8) && !checkWin()) {
                // Add delay for a little more "game like" feeling
                aiTurn();
                mTurnCount++;
            }
        } else if (mPlayersTurn == 2) {
            mTurnCount++;
            ((TextView) v).setTextColor(mPlayerTwoColor);
            ((TextView) v).setText("O");
            htmlText = String.format(mContext.getString(R.string.game_ttt_turn),
                    String.format("<font color=%s>%s</font>", convertIntegerToHTMLColorCode(mPlayerOneColor), mPlayerOneName));
            mTurnView.setText(Html.fromHtml(htmlText), TextView.BufferType.SPANNABLE);
            mPlayersTurn = 1;
        } else {
            mTurnView.setText(mContext.getString(R.string.game_ttt_draw));
            mNewGameButton.setVisibility(View.VISIBLE);
        }
    }

    private void aiTurn() {
        int fieldOfRandomChoice = new Random(System.currentTimeMillis()).nextInt(9);
        if (mGrid[fieldOfRandomChoice].getText().toString().equals("")) {
            mGrid[fieldOfRandomChoice].setTextColor(mPlayerTwoColor);
            mGrid[fieldOfRandomChoice].setText("O");
            String htmlText = String.format(mContext.getString(R.string.game_ttt_turn),
                    String.format("<font color=%s>%s</font>", convertIntegerToHTMLColorCode(mPlayerOneColor), mPlayerOneName));
            mTurnView.setText(Html.fromHtml(htmlText), TextView.BufferType.SPANNABLE);
        } else {
            aiTurn();
        }
        mPlayersTurn = 1;
    }

    @SuppressWarnings("ConstantConditions")
    private boolean checkWin() {
        boolean gameOver = false;
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
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[3] == gridSigns[4]) && (gridSigns[4] == gridSigns[5]) && (gridSigns[3] != '\u0000')) {  // Second Row
            colorWinningLineBackgrounds(3, 4, 5);
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[6] == gridSigns[7]) && (gridSigns[7] == gridSigns[8]) && (gridSigns[6] != '\u0000')) {  // Third Row
            colorWinningLineBackgrounds(6, 7, 8);
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[0] == gridSigns[3]) && (gridSigns[3] == gridSigns[6]) && (gridSigns[0] != '\u0000')) {  // First Column
            colorWinningLineBackgrounds(0, 3, 6);
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[1] == gridSigns[4]) && (gridSigns[4] == gridSigns[7]) && (gridSigns[1] != '\u0000')) {  // Second Column
            colorWinningLineBackgrounds(1, 4, 7);
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[2] == gridSigns[5]) && (gridSigns[5] == gridSigns[8]) && (gridSigns[2] != '\u0000')) {  // Third Column
            colorWinningLineBackgrounds(2, 5, 8);
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[0] == gridSigns[4]) && (gridSigns[4] == gridSigns[8]) && (gridSigns[0] != '\u0000')) {  // First Diagonal
            colorWinningLineBackgrounds(0, 4, 8);
            endGame(false);
            gameOver = true;
        } else if ((gridSigns[2] == gridSigns[4]) && (gridSigns[4] == gridSigns[6]) && (gridSigns[2] != '\u0000')) {  // Second Diagonal
            colorWinningLineBackgrounds(2, 4, 6);
            endGame(false);
            gameOver = true;
        } else {
            if (filled > 8) {
                endGame(true);
                gameOver = true;
            }
        }
        return gameOver;
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

    private void endGame(boolean draw) {
        String winnerName;
        int winnerColor;
        if (!draw) {
            if (mPlayersTurn == 1) {
                winnerName = mPlayerTwoName;
                winnerColor = mPlayerTwoWeakColor;
            } else {
                winnerName = mPlayerOneName;
                winnerColor = mPlayerOneWeakColor;
            }
            mTurnView.setText(mContext.getString(R.string.game_ttt_player_won, winnerName));
            mTurnView.setTextColor(winnerColor);
        } else {
            mTurnView.setText(mContext.getString(R.string.game_ttt_draw));
            mTurnView.setTextColor(0xFFFF8000); // Orange, not used in game, so it's good as a color for a draw
        }

        // Disable the game area
        for (TextView tv : mGrid) {
            tv.setEnabled(false);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do nothing. Just in case we ain't able to disable something, but want to make it unusable
                }
            });
        }
        // Enable the new game button
        mNewGameButton.setVisibility(View.VISIBLE);
    }

    private String convertIntegerToHTMLColorCode(int i) {
        String result = "";
        char[] temp = Integer.toHexString(i).toCharArray();
        if (temp.length > 1) {
            for (int j = 2; j <= temp.length - 1; j++) {
                result += temp[j];
            }
        }
        return "#" + result;
    }

    @Override
    public void onBackPressed() {
        Resources res = mContext.getResources();
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.CustomPopup));
        builder
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
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(mContext, GameHubActivity.class)); // Return to game hub
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    private void stupidHelperBecauseOfStuff() {
        super.onBackPressed();
    }
}