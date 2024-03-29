package edu.apsu.csci.local_app.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.util.concurrent.CountDownLatch;

import edu.apsu.csci.local_app.R;

/**
 * Created by PC on 4/21/2016.
 */
public class SplashActivity extends Activity {
    private final static String LOG_TAG = SplashActivity.class.getSimpleName();
    private final CountDownLatch timeoutLatch = new CountDownLatch(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Thread thread = new Thread(new Runnable() {
            public void run() {
                // Wait for the splash timeout.
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) { }

                // Expire the splash page delay.
                timeoutLatch.countDown();
            }
        });
        thread.start();
        goMain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Touch event bypasses waiting for the splash timeout to expire.
        timeoutLatch.countDown();
        return true;
    }

    /**
     * Starts an activity after the splash timeout.
     * @param intent the intent to start the activity.
     */
    private void goAfterSplashTimeout(final Intent intent) {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                // wait for the splash timeout expiry or for the user to tap.
                try {
                    timeoutLatch.await();
                } catch (InterruptedException e) {
                }

                SplashActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(intent);
                        // finish should always be called on the main thread.
                        finish();
                    }
                });
            }
        });
        thread.start();
    }

    /**
     * Go to the main activity after the splash timeout has expired.
     */
    protected void goMain() {
        Log.d(LOG_TAG, "Launching Main Activity...");
        goAfterSplashTimeout(new Intent(this, MainActivity.class));
    }

}
