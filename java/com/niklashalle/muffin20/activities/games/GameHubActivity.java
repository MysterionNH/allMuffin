package com.niklashalle.muffin20.activities.games;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.niklashalle.muffin20.activities.R;
import com.niklashalle.muffin20.activities.BaseActivity;

/**
 * The GameHub, from here all games are accessible
 */
public class GameHubActivity extends BaseActivity {

    private final Context mContext = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguageAccordingToSettings(mContext);
        setContentView(R.layout.activity_game_hub);
        setBGColorAccordingToSettings(mContext);

        // Sets up the button listeners for the open buttons
        findViewById(R.id.drOpenButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(mContext, TicTacToeSettings.class));
            }
        });    //TickTackToe
    }
}