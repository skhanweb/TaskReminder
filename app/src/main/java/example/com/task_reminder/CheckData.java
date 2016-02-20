package example.com.task_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import example.com.Sliding_Tabs.Tab_Categories;

/**
 * Created by Toshiba on 24-Jan-16.
 */
public class CheckData extends AsyncTask<String, Boolean, Long> {

    Context mContext;
    int Date,Month,Year,Hour,Minute;
    Firebase ref;

    public CheckData(Context mContext) {
        this.mContext = mContext;
        ref.setAndroidContext(mContext);
        ref = new Firebase("https://taskreminder.firebaseio.com");
    }

    @Override
    protected Long doInBackground(String... voids) {
//        Log.i("MyAsync", "1 doInBackground Data = " + voids[2]);
        publishProgress(true);

          /* try {
                Thread.sleep(Integer.parseInt(voids[0]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            GetUpdatedData();


  //      Log.i("MyAsync", "2 doInBackground Data = " + voids[0]);
        return new Date().getTime();
    }

    @Override
    protected void onPreExecute() {
        Log.i("MyAsync", "onPreExecute");
        super.onPreExecute();
        GetUpdatedData();
    }


    @Override
    protected void onPostExecute(Long integer) {
        Log.i("MyAsync", "onPostExecute Data = " + integer);
        super.onPostExecute(integer);
        GetUpdatedData();
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
 //       Log.i("MyAsync", "onProgressUpdate Data = " + values[0]);
        super.onProgressUpdate(values);
        GetUpdatedData();
    }
    public void GetUpdatedData()
    {
        // final Tab_Categories c = new Tab_Categories(this.getActivity());

        //Get Current DateTime
        Calendar calendar = Calendar.getInstance();
        Date = calendar.get(calendar.DAY_OF_MONTH);
        Month = calendar.get(calendar.MONTH);
        Year = calendar.get(calendar.YEAR);
        final String Date_Concat,Time_Concat;
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

      /*  for(int i=0;i<TempList_Date.size();i++) {

            if (TempList_Date.get(i).equals(Date_Concat) && TempList_Time.get(i).equals(Time_Concat)) {
                BroadcastReceiver br;
                AlarmManager am;
                PendingIntent pi;
                AlarmManager alarmManager;
                PendingIntent pendingIntent;
                alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                Intent myIntent = new Intent(mContext, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);

                //Calendar calendar = Calendar.getInstance();
                int timeHour = 0;
                calendar.set(Calendar.HOUR_OF_DAY, timeHour);
                int timeMinute = 0;
                calendar.set(Calendar.MINUTE, timeMinute);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1 * 1000, pendingIntent);

                Intent intent = new Intent(this.getActivity(), Alarm_Task_Screen.class);
                intent.putExtra("TaskDate",TempList_Date.get(i));
                intent.putExtra("TaskTime",TempList_Time.get(i));
                startActivity(intent);
            }
        }*/

        final Tab_Categories c = new Tab_Categories(mContext);
        ref.child("Categories/General").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : children.getChildren()) {
                        Map<String, Object> task = (Map<String, Object>) child.getValue();
                        // String name = (String) mapMsg.get("name");
                        // String message = (String) mapMsg.get("date");
                        // String title = (String) mapMsg.get("time");
                        //msg.add(new Message(title, message, isRead));
                        String Task_Name=task.get("name").toString();
                        String Task_Date = task.get("date").toString();
                        String Task_Time=task.get("time").toString();


                        if (Task_Date.equals(Date_Concat) && Task_Time.equals(Time_Concat))
                        {
                            BroadcastReceiver br;
                            AlarmManager am;
                            PendingIntent pi;
                            AlarmManager alarmManager;
                            PendingIntent pendingIntent;
                            alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            Intent myIntent = new Intent(mContext, AlarmReceiver.class);
                            pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);

                            Calendar calendar = Calendar.getInstance();
                            int timeHour = 0;
                            calendar.set(Calendar.HOUR_OF_DAY, timeHour);
                            int timeMinute = 0;
                            calendar.set(Calendar.MINUTE, timeMinute);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1 * 1000, pendingIntent);
                            Intent intent = new Intent(mContext, Alarm_Task_Screen.class);
                            intent.putExtra("TaskName",Task_Name);
                            intent.putExtra("TaskDate",Task_Date);
                            intent.putExtra("TaskTime",Task_Time);

                        }
                        //c.adapter.Category_List.add(task.get("name").toString()+"-"+task.get("date").toString()+"-"+task.get("time").toString());

                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
