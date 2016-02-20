package example.com.task_reminder;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Toshiba on 20-Jan-16.
 */
    public class AlarmReceiver extends WakefulBroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // here you can start an activity or service depending on your need
            // for ex you can start an activity to vibrate phone or to ring the phone

       /* String phoneNumberReciver="03343322870";// phone number to which SMS to be send
        String message="Hi I will be there later, See You soon";// message to send
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
        // Show the toast  like in above screen shot
        Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();*/

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
            ringtone.play();
        }
}
