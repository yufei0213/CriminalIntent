package com.bignerdanch.android.criminalintent.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by yufei0213 on 16/11/20.
 */

public class PollService extends IntentService {

    private static final String Tag = "PollService";
    private static final long POLL_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, PollService.class);

        return intent;
    }

    public static void setServiceAlarm(Context context, boolean isOn) {

        Intent intent = PollService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (isOn) {

            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    POLL_INTERVAL,
                    pendingIntent);
        } else {

            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public static boolean isServiceAlarmOn(Context context) {

        Intent intent = new Intent(context, PollService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);

        return pendingIntent != null;
    }

    public PollService() {

        super(Tag);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (isNetworkAvailableAndConnected())
            return;

        Log.i(Tag, "Received an initent: " + intent);
    }

    private boolean isNetworkAvailableAndConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = connectivityManager.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && connectivityManager.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }
}
