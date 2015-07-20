package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimerActivity extends BaseActivity {

    final Context _context = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Button btn1 = (Button)findViewById(R.id.submitBtn);
        btn1.setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener;

    {
        btnListener = new View.OnClickListener() {

            public void onClick(View v) {
                String time;
                EditText textie = (EditText) findViewById(R.id.countdown);
                //for (int i = 0; i < 150; i++) {
                    time = new SimpleDateFormat("HH:mm:ss.SSS", Locale.GERMANY).format(Calendar.getInstance().getTime());
                    //textie.setText(time);
                    Toast toast = Toast.makeText(_context, time, Toast.LENGTH_LONG);
                    toast.show();

                    //TODO: Clear this shit
                //}
            }
        };
    }
}

//TODO: Add.. everything :D
// get the current time (down to seconds)
// print it somewhere?
//   best thing would be an analogue clock but meh... I will see
//       maybe an extra project/class (do I need a thread? possibly)
//       maybe an image off a clock I overlay a line, representing the hand
//       turning it relative to time
// calculate distance to given end time
// when not negative (damn users)
//  display on clock?
// print timer (down to seconds) - extra thread?
//
// other way round, so give timer and calculate and, otherwise same as above
//
// conclusion:
// TODO:       stop being lazy, start
// TODO:       learn about threads, you will definitely need them this time