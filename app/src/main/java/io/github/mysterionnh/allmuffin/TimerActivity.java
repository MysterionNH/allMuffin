package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Just as Countdown, it's partner, it has a dream too. Cool stuff 's supposed to happen here some
 * day soon(TM)
 */
public class TimerActivity extends BaseActivity {

    /** A hack used to get the context of this Activity in places where I need it put am not allowed
     * to get it. Sad life.
     */
    final Context _context = (Context) this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        findViewById(R.id.submitBtn).setOnClickListener(btnListener);
    }

    private View.OnClickListener btnListener;

    {
        btnListener = new View.OnClickListener() {

            public void onClick(View v) {
                String time = new SimpleDateFormat("dd.MM.yyyy (GGG) - HH:mm:ss.SSS", Locale.GERMANY).format(Calendar.getInstance().getTime());
                Toast toast = Toast.makeText(_context, time, Toast.LENGTH_LONG);
                toast.show();
            }
        };
    }
}

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
// TODO:       learn about threads, you will definitely need them this time

// TODO:       DAMN GRAPHICS!!!!!! mep .-.