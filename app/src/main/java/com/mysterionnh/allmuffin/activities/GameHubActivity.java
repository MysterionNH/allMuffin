package com.mysterionnh.allmuffin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mysterionnh.allmuffin.R;
import com.mysterionnh.allmuffin.activities.games.TickTackToeActivity;

/**
 * The GameHub, from here all games are accessible
 */
public class GameHubActivity extends BaseActivity {

    private final Context _context = (Context) this;
    private final View.OnClickListener btnListener = new View.OnClickListener() {

        public void onClick(View v) {
            startActivity(new Intent(_context, TickTackToeActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppLanguageAccordingToSettings(_context);
        setContentView(R.layout.activity_game_hub);
        setBGColorAccordingToSettings(_context);

        // Sets up the button listeners for the open buttons
        findViewById(R.id.drOpenButton).setOnClickListener(btnListener);    //TickTackToe
    }
}