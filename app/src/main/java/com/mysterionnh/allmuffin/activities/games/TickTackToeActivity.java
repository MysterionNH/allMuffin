package com.mysterionnh.allmuffin.activities.games;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.BaseActivity;

public class TickTackToeActivity extends BaseActivity {

    Context mContext = (Context) this;

    boolean mMultiplayer = false;

    /**
     * TableRows, wrapping the settings
     */
    TableRow[] mTableRows;

    /**
     * Buttons, used for the settings
     */
    Button[] mButtons;
    String mNamePlayerOne;
    String mNamePlayerTwo;
    /**
     * The main layout, everything else is in here
     */
    private RelativeLayout mMainFrame;
    /**
     * Defines the stage the user is in
     */
    private int mStage = 0;
    private final View.OnClickListener btnListener = new View.OnClickListener() { //TODO: Continue

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonOnePlayer: {
                    v.setBackgroundColor(Color.TRANSPARENT);
                    mTableRows[mStage].setAlpha(0.3f);
                    mTableRows[mStage].setClickable(false);
                    mStage++;
                    mTableRows[mStage].setVisibility(View.VISIBLE);
                    mMultiplayer = false;
                    final TextView tv = (TextView) findViewById(R.id.playerOneName);
                    tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                mNamePlayerOne = tv.getText().toString();
                                mTableRows[mStage].setAlpha(0.3f);
                                mTableRows[mStage].setClickable(false);
                                mStage++;
                                mTableRows[mStage].setVisibility(View.VISIBLE);
                                TextView display = (TextView) findViewById(R.id.namePlayerTwoView);
                                display.setText(mContext.getResources().getString(R.string.game_ttt_settings_player_two_name_ai, mNamePlayerOne) + ".");
                                final TextView tv = (TextView) findViewById(R.id.playerTwoName);
                                tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                            mNamePlayerTwo = tv.getText().toString();
                                            mTableRows[mStage].setAlpha(0.3f);
                                            mTableRows[mStage].setClickable(false);
                                            mStage++;
                                            mTableRows[mStage].setVisibility(View.VISIBLE);
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
                    mTableRows[mStage].setAlpha(0.3f);
                    mTableRows[mStage].setClickable(false);
                    mStage++;
                    mTableRows[mStage].setVisibility(View.VISIBLE);
                    mMultiplayer = true;
                    final TextView tv = (TextView) findViewById(R.id.playerOneName);
                    tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                mNamePlayerOne = tv.getText().toString();
                                mTableRows[mStage].setAlpha(0.3f);
                                mTableRows[mStage].setClickable(false);
                                mStage++;
                                mTableRows[mStage].setVisibility(View.VISIBLE);
                                final TextView tv = (TextView) findViewById(R.id.playerTwoName);
                                tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                    @Override
                                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                        if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                            mNamePlayerTwo = tv.getText().toString();
                                            mTableRows[mStage].setAlpha(0.3f);
                                            mTableRows[mStage].setClickable(false);
                                            mStage++;
                                            mTableRows[mStage].setVisibility(View.VISIBLE);
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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tic_tac_toe_settings);

        mMainFrame = (RelativeLayout) findViewById(R.id.MainFrame);

        mTableRows = new TableRow[]{(TableRow) findViewById(R.id.playerCountWrapper),
                (TableRow) findViewById(R.id.namePlayerOne),
                (TableRow) findViewById(R.id.namePlayerTwo)};

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
        } else {
            revertOneStep();
        }
    }

    private void revertOneStep() {
        mTableRows[mStage].setVisibility(View.GONE);
        mStage--;
        mTableRows[mStage].setAlpha(1.0f);
        if (mStage == 0) {
            for (Button b : mButtons) {
                b.setBackgroundColor(Color.LTGRAY); //// FIXME: 16.08.2015 Doesn't look like the button in the first place, just some weird gray square
            }
        }
    }
}