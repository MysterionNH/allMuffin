package com.mysterionnh.allmuffin.activities.games;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

    /**
     * The main layout, everything else is in here
     */
    private RelativeLayout mMainFrame;

    /**
     * The TextView were all questions regarding the game settings are displayed
     */
    private TextView mQuestionView;

    /**
     * Defines the stage the user is in
     */
    private int mStage = 0;
    private final View.OnClickListener btnListener = new View.OnClickListener() { //TODO: Continue

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonOnePlayer: {
                    mTableRows[mStage].setAlpha(0.3f);
                    mTableRows[mStage].setEnabled(false);
                    mQuestionView.setText("Choose Name");
                    mStage++;
                    mMultiplayer = false;
                    break;
                }
                case R.id.buttonTwoPlayer: {
                    mTableRows[mStage].setAlpha(0.3f);
                    mTableRows[mStage].setEnabled(false);
                    mQuestionView.setText("Choose Names");
                    mStage++;
                    mMultiplayer = true;
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_tic_tac_toe);

        mMainFrame = (RelativeLayout) findViewById(R.id.MainFrame);

        mQuestionView = (TextView) findViewById(R.id.tttSettingsTitle);

        mTableRows = new TableRow[]{(TableRow) findViewById(R.id.playerCountWrapper)};

        mButtons = new Button[]{(Button) findViewById(R.id.buttonOnePlayer),
                (Button) findViewById(R.id.buttonTwoPlayer)};

        for (Button b : mButtons) {
            b.setOnClickListener(btnListener);
        }
    }
}
