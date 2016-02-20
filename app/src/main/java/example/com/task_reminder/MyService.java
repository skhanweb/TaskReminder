package example.com.task_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import example.com.Model.Task;
import example.com.Notification.NotificationView;
import example.com.Sliding_Tabs.Tab_Categories;
import example.com.ViewTasks.Task_Description;

public class MyService extends Service {
    Context mContext;
    int Date,Month,Year,Hour,Minute;
    Firebase ref;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
             super.onCreate();
        Log.i("ServiceCallingMethod"," OnCreate");
    }
    @Override
    public void onDestroy(){
             super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this.getApplicationContext();
        Log.i("ServiceCallingMethod"," OnStart");
        ref.setAndroidContext(this.getApplicationContext());
        ref = new Firebase("https://taskreminder.firebaseio.com");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                GetUpdatedData();
            }
        });
        t.start();
        return START_STICKY;
    }
   public String Task_Name;
    public String Task_Date;
    public String Task_Time;
    public String Task_Category;
    public String Task_Description;
    public String Date_Concat,Time_Concat;

    public BroadcastReceiver br;
    public AlarmManager am;
    public PendingIntent pi;
    public AlarmManager alarmManager;
    public PendingIntent pendingIntent;

    public void GetUpdatedData()
    {
        //Get Current DateTime
        Calendar calendar = Calendar.getInstance();
        Date = calendar.get(calendar.DAY_OF_MONTH);
        Month = calendar.get(calendar.MONTH);
        Year = calendar.get(calendar.YEAR);
        if ((Date >= 0 && Date <=9)  && (Month >= 0 && Month <=9))
            Date_Concat = "0"+Date+"/0"+(Month+1)+"/"+Year;
        else if ((Date >= 0 && Date <=9)  && (Month >= 10 && Month <=12))
            Date_Concat = "0"+Date+"/"+(Month+1)+"/"+Year;
        else if ((Date >= 10 && Date <=31)  && (Month >= 0 && Month <=9))
            Date_Concat = Date+"/0"+(Month+1)+"/"+Year;
        else
            Date_Concat = Date+"/"+(Month+1)+"/"+Year;

        //Get Current Time
        Hour = calendar.get(calendar.HOUR_OF_DAY);
        Minute = calendar.get(calendar.MINUTE);

        if ((Hour >= 0 && Hour <=9)  && (Minute >= 0 && Minute <=9))
            Time_Concat = "0"+Hour + ":0"+Minute;
        else if ((Hour >= 0 && Hour <=9)  && (Minute >= 10 && Minute <=59))
            Time_Concat = "0"+Hour+":"+Minute;

        else if ((Hour >= 10 && Hour <=24)  && (Minute >= 0 && Minute <=9))
            Time_Concat = Hour+":0"+Minute;
        else
            Time_Concat = Hour+":"+Minute;

        final Tab_Categories c = new Tab_Categories(mContext);
        ref.child("Categories").child(c.CategoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    for (DataSnapshot category : dataSnapshot.getChildren())
                    {

                    for (DataSnapshot child : category.getChildren()) {
                       Map<String, Object> task = (Map<String, Object>) child.getValue();


                        Task_Category = task.get("category").toString();
                        Task_Date = task.get("date").toString();
                        Task_Description = task.get("description").toString();
                        Task_Name = task.get("name").toString();
                        Task_Time = task.get("time").toString();


                        if (Task_Date.equals(Date_Concat) && Task_Time.equals(Time_Concat)) {

                            Intent intent = new Intent(MyService.this, Alarm_Task_Screen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyService.this.startActivity(intent);


                        }
                    }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GetUpdatedData();
    }

    public  void GetData()
    {
        ref.child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Task task = child.getValue(Task.class);

                        Task_Name = task.getName().toString();
                        Task_Date = task.getDate().toString();
                        Task_Time = task.getTime().toString();
                        Task_Category = task.getCategory().toString();
                    }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

    }
    });
    }
}
