package example.com.Sliding_Tabs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import example.com.Categories.CustomAdapter;
import example.com.Model.Category;
import example.com.Model.Task;
import example.com.ViewTasks.Task_List;
import example.com.task_reminder.AlarmReceiver;
import example.com.task_reminder.Alarm_Task_Screen;
import example.com.task_reminder.CheckData;
import example.com.task_reminder.MyService;
import example.com.task_reminder.R;

public class Tab_Categories extends Fragment {

    Context mContext;
   public CustomAdapter adapter;
    GridView grid;
    ArrayList<String> values = new ArrayList<String>();
    TextView txtCat;
    ArrayList<String> ii = new ArrayList<String>();
    ArrayList<String> TaskList = new ArrayList<String>();
    Firebase ref;
    int mDate,mMonth,mYear,mHour,mMin;
    ArrayList<String> TempList_Date = new ArrayList<String>();
    ArrayList<String> TempList_Time = new ArrayList<String>();

   public String CategoryName="";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab_Categories.
     */
    // TODO: Rename and change types and number of parameters
   /* public static Tab_Categories newInstance(String param1, String param2) {
       // Tab_Categories fragment = new Tab_Categories(this.getActivity());
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    public Tab_Categories(Context mcontext)
    {
        this.mContext=mcontext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ref.setAndroidContext(this.getActivity());
        ref = new Firebase("https://taskreminder.firebaseio.com");

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.tab__categories, container, false);

         grid= (GridView) rootView.findViewById(R.id.GridCategories);
        adapter = new CustomAdapter(new ArrayList<String>(), R.layout.category_item, this.getActivity());
        grid.setAdapter(adapter);

        final Category cat = new Category();

        Calendar c = Calendar.getInstance();
        mHour = c.get(c.HOUR_OF_DAY);
        mMin = c.get(c.MINUTE);
        mDate = c.get(c.DAY_OF_MONTH);
        mMonth = c.get(c.MONTH);
        mYear = c.get(c.YEAR);

        ref.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                   // for (DataSnapshot child : children.getChildren()) {
                       // Map<String, Category> category = (Map<String, Category>) children.getValue();
                         //Map<String, Object> task = (Map<String, Object>) children.getValue();
                        //Category category = children.getValue(Category.class);

                        //adapter.Category_List.add(children.getKey().toString());
                    adapter.Category_List.add(children.getKey().toString());
                   // }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        adapter.notifyDataSetChanged();
        AddOnItemClickListener();
        adapter.notifyDataSetChanged();

        return rootView;
    }
    public ArrayList<String> TName =new ArrayList<String>();
    public ArrayList<String> TCategory =new ArrayList<String>();
    public ArrayList<String> TDesc =new ArrayList<String>();
    public ArrayList<String> TTime =new ArrayList<String>();
    public ArrayList<String> TDate =new ArrayList<String>();




    public void GetAllTasks(){
        ref.child("Categories").child(CategoryName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                         Map<String, Object> task = (Map<String, Object>) child.getValue();

                        Task_Name = task.get("name").toString();
                        Task_Date = task.get("date").toString();
                        Task_Time = task.get("time").toString();
                        Task_Description = task.get("description").toString();
                        Task_Category = task.get("category").toString();

                        if (Task_Name.equals("") || Task_Date.equals("") || Task_Time.equals("") || Task_Description.equals("") || Task_Category.equals(""))
                        {
                            Toast.makeText(mContext,"There is no task in this category",Toast.LENGTH_SHORT).show();
                        }

                        TName.add(Task_Name.toString());
                        TDate.add(Task_Date.toString());
                        TTime.add(Task_Time.toString());
                        TDesc.add(Task_Description.toString());
                        TCategory.add(Task_Category.toString());



                    }
                }
                //}
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        }


    public String Task_Name;
    public String Task_Date;
    public String Task_Time;
    public String Task_Category;
    public  String Task_Description;

    public void AddOnItemClickListener()
    {
       // try {
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int counter = position;
                    CategoryName = (String) parent.getItemAtPosition(position);
                        GetAllTasks();
                    //Toast.makeText(mContext, "You clicked " + CategoryName, Toast.LENGTH_SHORT).show();
                    getFragmentManager().beginTransaction().addToBackStack("TAG").replace(R.id.container, new Task_List(CategoryName,TName,TDesc,TTime,TDate,TCategory)).commit();

                    if (TName.size() != 0 || TDate.size() != 0  || TTime.size() != 0  || TCategory.size() != 0  || TDesc.size() != 0 ) {
                        TName.clear();
                        TDate.clear();
                        TTime.clear();
                        TCategory.clear();
                        TDesc.clear();
                    }
                }
            });
       // }
       /* catch (Exception ex)
        {
            Toast.makeText(mContext, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }*/
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
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
        public void onFragmentInteraction();
    }

}


