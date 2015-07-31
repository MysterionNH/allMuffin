package io.github.mysterionnh.allmuffin;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.RelativeLayout;
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

        findViewById(R.id.testButton).setOnClickListener(btnListener0);
    }

    private View.OnClickListener btnListener0 = new View.OnClickListener() {
        public void onClick(View v) {
            Toast toast = Toast.makeText(_context,
                    new SimpleDateFormat("dd.MM.yyyy, hh:mm:ss.SSS", Locale.GERMANY).format(Calendar.getInstance().getTime()),
                    Toast.LENGTH_LONG);
            toast.show();

            ((RelativeLayout) findViewById(R.id.testButton).getParent()).setBackgroundColor(Color.GREEN);

            ViewPropertyAnimator animate = findViewById(R.id.testButton).animate();
            animate.setDuration(500);
            animate.rotationBy(360);
            animate.scaleXBy(5);
            animate.scaleYBy(5);
            animate.start();
        }
    };
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