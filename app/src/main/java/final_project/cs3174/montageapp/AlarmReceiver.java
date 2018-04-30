package final_project.cs3174.montageapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Zac on 4/30/2018.
 */

public final class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, AlarmService.class);
        context.startService(intent1);


    }
}
