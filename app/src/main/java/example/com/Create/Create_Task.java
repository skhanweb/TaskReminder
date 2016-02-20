package example.com.Create;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.Categories.ShowCategories;
import example.com.task_reminder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Create_Task extends Fragment {

    EditText editNewTask;
    EditText editTaskDesc;
    Button btnSaveTask;
    Button btnCancelTask;
    Button btnSelectDate;
    Button btnSelectTime;
    Button btnSelect_Category;
    TextView txtDate;
    TextView txtTime;
    Firebase ref;
    List<String> Get_Category_Names;

    int Date,Month,Year,Hour,Minute;

    public Create_Task() {
        // Required empty public constructor
    }

    View rootView;
    Context mContext = this.getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // rootView = inflater.inflate(R.layout.create_task, container, false);

        ref.setAndroidContext(this.getActivity());
        ref = new Firebase("https://taskreminder.firebaseio.com/");

        /* final Dialog d = new Dialog(mContext);
        d.setContentView(R.layout.create_task_dialog);
        d.setTitle("New Task");*/

        editNewTask = (EditText) rootView.findViewById(R.id.editNewtask);
        editTaskDesc = (EditText) rootView.findViewById(R.id.editTaskDesc);
        btnSaveTask = (Button) rootView.findViewById(R.id.btnSaveTask);
        btnCancelTask = (Button) rootView.findViewById(R.id.btnCancelTask);
        btnSelectDate = (Button) rootView.findViewById(R.id.btnDatePicker);
        btnSelectTime = (Button) rootView.findViewById(R.id.btnTimePicker);
        btnSelect_Category = (Button) rootView.findViewById(R.id.btnSelect_Category);
        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        txtTime = (TextView) rootView.findViewById(R.id.txtTime);

        final Context mcontext = this.getActivity();

        //Get Date
        Calendar calendar = Calendar.getInstance();
        Date = calendar.get(calendar.DAY_OF_MONTH);
        Month = calendar.get(calendar.MONTH);
        Year = calendar.get(calendar.YEAR);

        //Get Time
        Hour = calendar.get(calendar.HOUR_OF_DAY);
        Minute = calendar.get(calendar.MINUTE);

        btnSelect_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("TAG").replace(R.id.container, new ShowCategories()).commit();
            }
        });
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDate();

            }
        });
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTime();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> data = new HashMap<String, String>();
                data.put("Description", editTaskDesc.getText().toString());
                data.put("Name", editNewTask.getText().toString());
                data.put("Date",txtDate.getText().toString());
                data.put("Time",txtTime.getText().toString());
                ref.child("Categories").child("Eating").push().setValue(data);
                Toast.makeText(mcontext, "New task has been added", Toast.LENGTH_SHORT).show();
                //d.dismiss();
            }
        });
        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //d.dismiss();
            }
        });



        return rootView;
    }


    public void LoadTaskDialog(){

        final Dialog d = new Dialog(mContext);
        d.setContentView(R.layout.create_category_dialog);
        d.setTitle("New Task");

        editNewTask = (EditText) rootView.findViewById(R.id.editNewtask);
        editTaskDesc = (EditText) rootView.findViewById(R.id.editTaskDesc);
        btnSaveTask = (Button) rootView.findViewById(R.id.btnSaveTask);
        btnCancelTask = (Button) rootView.findViewById(R.id.btnCancelTask);
        btnSelectDate = (Button) rootView.findViewById(R.id.btnDatePicker);
        btnSelectTime = (Button) rootView.findViewById(R.id.btnTimePicker);
        btnSelect_Category = (Button) rootView.findViewById(R.id.btnSelect_Category);
        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        txtTime = (TextView) rootView.findViewById(R.id.txtTime);

        final Context mcontext = this.getActivity();

        //Get Date
        Calendar calendar = Calendar.getInstance();
        Date = calendar.get(calendar.DAY_OF_MONTH);
        Month = calendar.get(calendar.MONTH);
        Year = calendar.get(calendar.YEAR);

        //Get Time
        Hour = calendar.get(calendar.HOUR_OF_DAY);
        Minute = calendar.get(calendar.MINUTE);

        btnSelect_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack("TAG").replace(R.id.container, new ShowCategories()).commit();
            }
        });
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDate();

            }
        });
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTime();
            }
        });

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> data = new HashMap<String, String>();
                data.put("Description", editTaskDesc.getText().toString());
                data.put("Name", editNewTask.getText().toString());
                data.put("Date",txtDate.getText().toString());
                data.put("Time",txtTime.getText().toString());
                ref.child("Categories").child("Eating").push().setValue(data);
                Toast.makeText(mcontext, "New task has been added", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });
        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();
            }
        });

    }

    public void SetDate()
    {
        final DatePickerDialog dialog = new DatePickerDialog(
                this.getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker picker, int yearSet,
                                          int monthSet, int daySet) {
                        Log.v("Date", yearSet + "/" + (monthSet + 1) + "/"
                                + daySet);
                        if ((daySet >= 0 && daySet <=9)  && (monthSet >= 0 && monthSet <=9))
                            txtDate.setText("0"+daySet + "/" + "0"+(monthSet + 1) + "/" + yearSet);

                        else if ((daySet >= 0 && daySet <=9)  && (monthSet >= 10 && monthSet <=12))
                            txtDate.setText("0"+daySet + "/" + (monthSet + 1)+ "/" + yearSet);

                        else if ((daySet >= 10 && daySet <=31)  && (monthSet >= 0 && monthSet <=9))
                            txtDate.setText(daySet + "/" + "0"+(monthSet + 1)+ "/" + yearSet);

                        else
                            txtDate.setText(daySet + "/" + (monthSet + 1) + "/" + yearSet);
                        Year = yearSet;
                        Month = monthSet;
                        Date = daySet;
                    }
                }, Year, Month,Date);
        dialog.show();


    }
    public void SetTime()
    {
        final TimePickerDialog dialog = new TimePickerDialog(
                this.getActivity(),new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker tPicker, int hourOfDaySet,
                                  int minuteSet) {
                Log.v("Hour", hourOfDaySet + ":" + (minuteSet));

                if ((hourOfDaySet >= 0 && hourOfDaySet <=9)  && (minuteSet >= 0 && minuteSet <=9))
                    txtTime.setText("0" + hourOfDaySet + ":" + "0" + (minuteSet));

                else if ((hourOfDaySet >= 0 && hourOfDaySet <=9)  && (minuteSet >= 10 && minuteSet <=59))
                    txtTime.setText("0"+hourOfDaySet + ":" + (minuteSet));

                else if ((hourOfDaySet >= 10 && hourOfDaySet <=24)  && (minuteSet >= 0 && minuteSet <=9))
                    txtTime.setText(hourOfDaySet + ":" +"0"+ (minuteSet));
                else
                    txtTime.setText(hourOfDaySet + ":" + (minuteSet));

                Hour = hourOfDaySet;
                Minute = minuteSet;

            }
        }, Hour, Minute, true);
        dialog.show();
    }


}
