package example.com.task_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Map;

import example.com.Notification.NotificationView;
import example.com.Sliding_Tabs.Tab_Categories;


public class Alarm_Task_Screen extends ActionBarActivity {

     TextView txtTitle;
    TextView txtDate;
     TextView txtTime;
     Button btnStop;
    Firebase ref;

    String Name, mDate, Time;
    String Date_Concat, Time_Concat;
    int Month,Date,Year,Hour,Minute;

    public BroadcastReceiver br;
    public AlarmManager alarmManager;
    public PendingIntent pendingIntent;
    Context mContext = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm__task__screen);
        final MyService service = new MyService();

        ref.setAndroidContext(this);
        ref = new Firebase("https://taskreminder.firebaseio.com/");

        txtTitle = (TextView) findViewById(R.id.txtTaskTitle);
        txtDate = (TextView) findViewById(R.id.txtTaskDate);
        txtTime = (TextView) findViewById(R.id.txtTaskTime);
        btnStop = (Button) findViewById(R.id.btnStopAlarm);


        //For Alarm//
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(mContext, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);

        Calendar calendar1 = Calendar.getInstance();
        int timeHour = 0;
        calendar1.set(Calendar.HOUR_OF_DAY, timeHour);
        int timeMinute = 0;
        calendar1.set(Calendar.MINUTE, timeMinute);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1 * 1000, pendingIntent);



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

        Tab_Categories c = new Tab_Categories(this);
        ref.child("Categories").child(c.CategoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot category : dataSnapshot.getChildren()) {

                    for (DataSnapshot child : category.getChildren()) {
                        Map<String, Object> task = (Map<String, Object>) child.getValue();


                        mDate = task.get("date").toString();
                        Name = task.get("name").toString();
                        Time = task.get("time").toString();


                        if (mDate.equals(Date_Concat) && Time.equals(Time_Concat)) {

                            txtTitle.setText(Name.toString());
                            txtDate.setText(mDate.toString());
                            txtTime.setText(Time.toString());
                        }
                    }
                }
            }

                @Override
                public void onCancelled (FirebaseError firebaseError){

                }
    });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);

            }
        });

       /* //For Notification//
        NotificationView nv = new NotificationView(Alarm_Task_Screen.this);
        nv.Notify(Name, "Reminder for your Task");*/
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_alarm__task__screen, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }


