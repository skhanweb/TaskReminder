package example.com.Sliding_Tabs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.Categories.CategorySelection;
import example.com.Categories.CustomAdapter;
import example.com.Categories.ShowCategories;
import example.com.Create.Create_Task;
import example.com.Create.Tab_CreateList;
import example.com.Model.Category;
import example.com.Model.Task;
import example.com.ViewTasks.Task_List;
import example.com.task_reminder.AlarmReceiver;
import example.com.task_reminder.Alarm_Task_Screen;
import example.com.task_reminder.MyService;
import example.com.task_reminder.R;

public class Tab_Create extends Fragment {

    //For Create Category//
    AlertDialog alertdialog;
    Context mContext;
    ListView list;
    ListView listCategory;
    Tab_CreateList creator;
    String  CategoryName;

    Button btnSaveCategory;
    Button btnCancelCategory;
    EditText editTextCategory;
    private Spinner spinner1;

    Firebase ref;
    String selectedText;

    //For Create Task//
    EditText editNewTask;
    EditText editTaskDesc;
    Button btnSaveTask;
    Button btnCancelTask;
    Button btnSelectDate;
    Button btnSelectTime;
    Button btnSelect_Category;
    TextView txtDate;
    TextView txtTime;
    TextView txtSelectedCategory;
    List<String> Get_Category_Names;

    TextView txtCategory;
    RadioButton rdo;

    int Date,Month,Year,Hour,Minute;

    RadioButton rdoCreate;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
   /* public static Tab_Create newInstance(String param1, String param2) {
       // Tab_Create fragment = new Tab_Create();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    public Tab_Create(Context mcontext) {
        // Required empty public constructor
        mContext = mcontext;
    }

    public void LoadList(){
        ArrayList<String> items = new ArrayList<String>();
        items.add("Add Task");
        items.add("Add Category");
        creator = new Tab_CreateList(this.getActivity(),R.layout.create_list_item,items);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ref.setAndroidContext(this.getActivity());
        ref =new Firebase("https://taskreminder.firebaseio.com/");
        // Inflate the layout for this fragment
         rootView =  inflater.inflate(R.layout.tab__create, container, false);

        list = (ListView) rootView.findViewById(R.id.Create_list);
       // rdoCreate = (RadioButton) rootView.findViewById(R.id.rdoCreate);
        LoadList();


        list.setAdapter(creator);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listCategory = (ListView) rootView.findViewById(R.id.ListCategorySelection);

        AddOnItemClickListener();


        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void AddOnItemClickListener()
    {
        try {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int counter = position;
                    String item = (String) list.getItemAtPosition(counter);
                    if (counter == 0) {
                        // getFragmentManager().beginTransaction().addToBackStack("TAG").add(R.id.container, new Create_Task()).commit();
                         LoadTaskDialog();
                    }
                    else if (counter == 1) {
                        // getFragmentManager().beginTransaction().addToBackStack("TAG").add(R.id.container, new Create_Category()).commit();
                        LoadCategoryDialog();


                    }
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(mContext, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadCategoryDialog()
    {
        final Dialog d = new Dialog(mContext);
        d.setContentView(R.layout.create_category_dialog);
        d.setTitle("New Category");

        editTextCategory= (EditText) d.findViewById(R.id.editCreateCategory);
        btnSaveCategory = (Button) d.findViewById(R.id.btnSaveCategory);
        btnCancelCategory = (Button) d.findViewById(R.id.btnCancelCategory);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextCategory.getText().toString().isEmpty() || editTextCategory.getText().toString()==null)
                    Toast.makeText(mContext,"Please insert category", Toast.LENGTH_SHORT).show();
                else {

                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put(editTextCategory.getText().toString(),editTextCategory.getText().toString());

                    ref.child("Categories").updateChildren(data);

                    Toast.makeText(mContext, "New category has been added", Toast.LENGTH_SHORT).show();
                    d.dismiss();
                }
            }
        });
        btnCancelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }


    public void LoadTaskDialog(){

        final Dialog d = new Dialog(mContext);
        d.setContentView(R.layout.create_task_dialog);
        d.setTitle("New Task");

        editNewTask = (EditText) d.findViewById(R.id.editNewtask);
        editTaskDesc = (EditText) d.findViewById(R.id.editTaskDesc);
        btnSaveTask = (Button) d.findViewById(R.id.btnSaveTask);
        btnCancelTask = (Button) d.findViewById(R.id.btnCancelTask);
        btnSelectDate = (Button) d.findViewById(R.id.btnDatePicker);
        btnSelectTime = (Button) d.findViewById(R.id.btnTimePicker);
        btnSelect_Category = (Button) d.findViewById(R.id.btnSelect_Category);
        txtDate = (TextView) d.findViewById(R.id.txtDate);
        txtTime = (TextView) d.findViewById(R.id.txtTime);
        txtSelectedCategory = (TextView) d.findViewById(R.id.txtSelectCategory);

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
                //ShowAlertDialogWithListview();
                final Dialog dc = new Dialog(mContext);
                dc.setContentView(R.layout.list_dialog);
                dc.setTitle("Please Select Category");
                ListView list = (ListView) dc.findViewById(R.id.List_Categories);
                final CustomAdapter adapter = new CustomAdapter(new ArrayList<String>(), R.layout.category_item, mContext);
                list.setAdapter(adapter);
                ref.child("Categories").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot children : dataSnapshot.getChildren()) {
                           // for (DataSnapshot child : children.getChildren()) {
                              //  Map<String, Category> categories = (Map<String, Category>) child.getValue();

                                adapter.Category_List.add(children.getKey().toString());
                            //}
                        }
                    }


                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                adapter.notifyDataSetChanged();

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int counter = position;
                       CategoryName = (String) parent.getItemAtPosition(position);
                        //Toast.makeText(mContext, "You clicked " + CategoryName, Toast.LENGTH_SHORT).show();
                        txtSelectedCategory.setText(CategoryName.toString());
                        dc.dismiss();

                    }
                });

                dc.show();

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
                if ((editNewTask.getText().toString().isEmpty() || editNewTask.getText().toString()==null) || (editTaskDesc.getText().toString().isEmpty() || editTaskDesc.getText().toString()==null)
                        || (txtDate.getText().toString().equals("") || txtDate.getText().toString()==null)|| (txtTime.getText().toString().equals("") || txtTime.getText().toString()==null) ||
                        (txtSelectedCategory.getText().toString().equals("") || txtSelectedCategory.getText().toString()==null))
                    Toast.makeText(mContext, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else
                {
                Task task = new Task(editNewTask.getText().toString(), editTaskDesc.getText().toString(), txtDate.getText().toString(), txtTime.getText().toString(),txtSelectedCategory.getText().toString());
                  Map<String, Object> data = new HashMap<String, Object>();
                    data.put("name",editNewTask.getText().toString());
                    data.put("description",editTaskDesc.getText().toString());
                    data.put("time",txtTime.getText().toString());
                    data.put("date",txtDate.getText().toString());
                    data.put("category",txtSelectedCategory.getText().toString());
             //       data.put(editNewTask.getText().toString(), task);

                    ref.child("Categories").child(txtSelectedCategory.getText().toString()).child(editNewTask.getText().toString()).updateChildren(data);

                //ref.child("Tasks").setValue(task);
                    Toast.makeText(mcontext, "New task has been added", Toast.LENGTH_SHORT).show();
                    d.dismiss();
            }
        }
    });
        btnCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();
            }
        });
        d.show();

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




    public void ShowAlertDialogWithListview()
    {
        CustomAdapter adapter;
        final List<String> Categories = new ArrayList<String>();
        //Tab_Categories cate = new Tab_Categories(this.getActivity());
        /*Categories.add("General");
        Categories.add("working");
        Categories.add("eating");*/
        list = (ListView) rootView.findViewById(R.id.List_Categories);
        adapter = new CustomAdapter(new ArrayList<String>(), R.layout.category_item, this.getActivity());
        list.setAdapter(adapter);

        ref.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : children.getChildren()) {
                        Map<String, Object> task = (Map<String, Object>) child.getValue();

                        Categories.add(task.get("name").toString());

                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        //Create sequence of items
        if (Categories.size() !=0) {
            final CharSequence[] categorys = Categories.toArray(new String[Categories.size()]);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.getActivity());
            dialogBuilder.setTitle("Categories");

            dialogBuilder.setItems(categorys, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    selectedText = categorys[item].toString();  //Selected item in listview
                   // Toast.makeText(mContext, "You Select " + selectedText, Toast.LENGTH_SHORT).show();
                    txtSelectedCategory.setText(selectedText.toString());
                }
            });
            //Create alert dialog object via builder
            AlertDialog alertDialogObject = dialogBuilder.create();
            //Show the dialog
            alertDialogObject.show();
        }
        else
            Toast.makeText(mContext, "No Category", Toast.LENGTH_SHORT).show();
    }


}
