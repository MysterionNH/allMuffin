package com.mysterionnh.allmuffin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class ClockAnim extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private DrawClockThread thread;
    private SurfaceView mSurfaceView = (SurfaceView) this;

    public ClockAnim (Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new DrawClockThread(holder, context);
    }

    public DrawClockThread getThread() {
        return thread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class DrawClockThread extends Thread {
        private final Object mRunLock = new Object();
        private final SurfaceHolder mSurfaceHolder;
        private Bitmap mBackgroundImage;
        private int mCanvasHeight = 1;
        private int mCanvasWidth = 1;
        private long mLsstTime;
        private Paint mLinePaint;
        private boolean mRun = false;

        public DrawClockThread(SurfaceHolder surfaceHolder, Context context) {
            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
            mContext = context;

            Resources res = context.getResources();

            mLinePaint = new Paint();
            mLinePaint.setAntiAlias(true);
            mLinePaint.setARGB(255, 255, 255, 255);
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas();
                    synchronized (mSurfaceHolder) {
                        synchronized (mRunLock) {
                            if (mRun) {
                                doDraw(c);
                            }
                        }
                    }
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        private void doDraw(Canvas canvas) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            mCanvasWidth = size.x / 2;
            mCanvasHeight = size.x / 2;
            canvas.drawBitmap(mBackgroundImage, mCanvasWidth, mCanvasHeight, null);
            canvas.drawCircle(mCanvasWidth, mCanvasHeight, size.x / 4, mLinePaint);
        }

        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                mCanvasWidth = width;
                mCanvasHeight = height;

                // don't forget to resize the background image
                mBackgroundImage = Bitmap.createScaledBitmap(
                        mBackgroundImage, width, height, true);
            }
        }

        public void setRunning(boolean b) {
            // Do not allow mRun to be modified while any canvas operations
            // are potentially in-flight. See doDraw().
            synchronized (mRunLock) {
                mRun = b;
            }
        }
    }
}
