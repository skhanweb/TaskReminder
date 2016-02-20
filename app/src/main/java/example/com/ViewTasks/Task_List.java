package example.com.ViewTasks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import example.com.Categories.CustomAdapter;
import example.com.Model.Task;
import example.com.Sliding_Tabs.Tab_Categories;
import example.com.Sliding_Tabs.Tab_Create;
import example.com.task_reminder.R;

public class Task_List extends Fragment {

    ArrayList<String> TaskList = new ArrayList<String>();
    ListView tasklist;
    Firebase ref;
    Custom_Tasks adp;
    TextView txtViewDate;
    TextView txtViewTime;
     int i;


    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String Cat="";
    ArrayList<String> Name = new ArrayList<String>();
    ArrayList<String> Desc =new ArrayList<String>();
    ArrayList<String> Time = new ArrayList<String>();
    ArrayList<String> Category =new ArrayList<String>();
    ArrayList<String> Task_Date = new ArrayList<String>();

    public Task_List(String cat, ArrayList<String> name, ArrayList<String> desc, ArrayList<String> time, ArrayList<String> date, ArrayList<String> category) {

        if (name !=null || desc !=null || time !=null || category !=null || date !=null) {
            Cat = cat;
            Name = name;
            Desc = desc;
            Time = time;
            Category = category;
            Task_Date = date;
        }
    }
   /* public Task_List(String cat, String name, String desc, String time, String date, String category) {
        Cat = cat;
        Name.add(name.toString());
        Desc.add(desc.toString());
        Time.add(time.toString());
        Category.add(category.toString()) ;
        Task_Date.add(date.toString());
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }
    View rootView;
  /* ArrayList <String>TaskName;
    ArrayList <String> TaskDesc;
    ArrayList <String>TaskCategory;
    ArrayList <String> TaskDate;
    ArrayList <String> TaskTime;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.task__list, container, false);

        ref.setAndroidContext(this.getActivity());
        ref = new Firebase("https://taskreminder.firebaseio.com");

        Tab_Categories categories = new Tab_Categories(this.getActivity());
        tasklist = (ListView) rootView.findViewById(R.id.Task_List);
        //txtViewDate = (TextView) rootView.findViewById(R.id.txtViewDate);
        //txtViewTime = (TextView) rootView.findViewById(R.id.txtViewTime);

        adp = new Custom_Tasks(TaskList,R.layout.task,this.getActivity());
        tasklist.setAdapter(adp);

        if (Category !=null) {
            for (i = 0; i < Category.size(); i++) {
                if (Category.get(i).equals(Cat)) {
                    adp.Task_List.add(Name.get(i).toString());
                /*TaskName.add(Name.get(i).toString());
                TaskDesc = Desc.get(i).toString();
                TaskCategory = Category.get(i).toString();
                TaskDate = Date.get(i).toString();
                TaskTime = Time.get(i).toString();*/

                }
            }
        }
        AddOnItemClickListener();
        adp.notifyDataSetChanged();


        return rootView;
    }


    public void AddOnItemClickListener()
    {
            tasklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int counter = position;
                   String Task_Name = (String) parent.getItemAtPosition(position);
                    getFragmentManager().beginTransaction().addToBackStack("TAG").replace(R.id.container, new Task_Description(Task_Name,Name,Desc,Time,Category,Task_Date)).commit();

                   /* if (Name.size() != 0 || Task_Date.size() != 0  || Time.size() != 0  || Category.size() != 0  || Desc.size() != 0 ) {
                        Name.clear();
                        Task_Date.clear();
                        Time.clear();
                        Category.clear();
                        Desc.clear();
                    }*/
                }
            });
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
