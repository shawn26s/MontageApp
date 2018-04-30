package final_project.cs3174.montageapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by Zac on 4/30/2018.
 */

public class AlarmService extends IntentService{

    private static final int NOTIFICATION_ID = 3;

    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Montage App");
        builder.setContentText("Don't forget to take a picture for the day");
        builder.setSmallIcon(R.drawable.logo);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

    }
}
