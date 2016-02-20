package example.com.Notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.task_reminder.Alarm_Task_Screen;
import example.com.task_reminder.R;

/**
 * Created by Toshiba on 27-Jan-16.
 */
public class NotificationView extends Fragment {

    Context mContext;

    public NotificationView(Context mContext) {
        this.mContext = mContext;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.alarm__task__screen, container, false);

        return rootView;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Notify(String notificationTitle, String notificationMessage)
    {
        Uri SoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        // NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Notification notification = new Notification(R.drawable.notification_template_icon_bg,"New SMS",System.currentTimeMillis());
        Intent notificationintent = new Intent(mContext, Alarm_Task_Screen.class);
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, notificationintent, 0);

        Notification mNotification = new Notification.Builder(mContext)
                .setContentTitle("Its Task Time")
                .setContentText("This is reminder for your task")
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentIntent(pi)
                .setSound(SoundUri)
                .addAction(R.drawable.notification_template_icon_bg, "View", pi)
                .addAction(0, "Remind", pi)

                .build();

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //notification.setLatestEventInfo(MainActivity.this,notificationTitle,notificationMessage,pi);
        manager.notify(0,mNotification);

    }
}
