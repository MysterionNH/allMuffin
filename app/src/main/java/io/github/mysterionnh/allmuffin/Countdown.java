package io.github.mysterionnh.allmuffin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Countdown extends Thread {

    Timer timer = new Timer();
    int i = 100;

    @Override
    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(getCurrentTime());
                if (i == 0) {
                    Countdown.this.stop();
                }
                i--;
            }
        }, 0, 3);
    }

    public String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
    }
}
