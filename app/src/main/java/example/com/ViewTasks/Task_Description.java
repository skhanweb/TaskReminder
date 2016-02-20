package example.com.ViewTasks;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class Task_Description extends Fragment {

    TextView txtTitle;
    TextView txtDescription;
    TextView txtDate;
    TextView txtTime;
    View rootView;
    //Button EditBtn;
    Button DeleteBtn;
    Firebase ref;

    Context mContext = this.getActivity();

    //For Create Task//
    EditText editNewTask;
    EditText editTaskDesc;
    Button btnSaveTask;
    Button btnCancelTask;
    Button btnSelectDate;
    Button btnSelectTime;
    Button btnSelect_Category;
    TextView txtDat;
    TextView txtTim;
    TextView txtSelectedCategory;
    List<String> Get_Category_Names;
    int Date,Month,Year,Hour,Minute;
    String  CategoryName;

    ArrayList <String> name;
    ArrayList <String> desc;
    ArrayList <String> time;
    ArrayList <String> category;
    ArrayList <String> date;
    String TName;

    String EditName="";
    String EditDesc="";
    String EditTime="";
    String EditDate="";
    String EditCategory="";
    Dialog d;

    public Task_Description(String Tname, ArrayList<String> name,  ArrayList <String> desc,  ArrayList <String> time,  ArrayList <String> category,  ArrayList <String> date) {

        if (name !=null || desc !=null || time !=null || category !=null || date !=null) {
            this.date = date;
            this.category = category;
            this.time = time;
            this.desc = desc;
            this.name = name;
            this.TName = Tname;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.task__description, container, false);

        ref.setAndroidContext(this.getActivity());
        ref = new Firebase("https://taskreminder.firebaseio.com");

        txtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        txtDescription = (TextView) rootView.findViewById(R.id.txtDescription);
        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        txtTime = (TextView) rootView.findViewById(R.id.txtTime);
        //EditBtn = (Button) rootView.findViewById(R.id.EditBtn);
        DeleteBtn = (Button) rootView.findViewById(R.id.DeleteBtn);

        mContext = this.getActivity();

        for(int i=0;i<name.size();i++) {
            if (name.get(i).equals(TName)) {
                EditName=  name.get(i).toString();
                EditDesc=  desc.get(i).toString();
                EditTime=   time.get(i).toString();
                EditCategory = category.get(i).toString();
                EditDate= date.get(i).toString();

            }
        }

       /* EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<name.size();i++) {
                    if (name.get(i).equals(TName)) {
                        EditName=  name.get(i).toString();
                        EditDesc=  desc.get(i).toString();
                        EditTime=   time.get(i).toString();
                        EditCategory = category.get(i).toString();
                        EditDate= date.get(i).toString();

                    }
                }

                 final Dialog dd = new Dialog(mContext);
                dd.setContentView(R.layout.create_task_dialog);
                dd.setTitle("Edit Your Task");

                editNewTask = (EditText) dd.findViewById(R.id.editNewtask);
                editTaskDesc = (EditText) dd.findViewById(R.id.editTaskDesc);
                btnSaveTask = (Button) dd.findViewById(R.id.btnSaveTask);
                btnCancelTask = (Button) dd.findViewById(R.id.btnCancelTask);
                btnSelectDate = (Button) dd.findViewById(R.id.btnDatePicker);
                btnSelectTime = (Button) dd.findViewById(R.id.btnTimePicker);
                btnSelect_Category = (Button) dd.findViewById(R.id.btnSelect_Category);
                txtDat = (TextView) dd.findViewById(R.id.txtDate);
                txtTim = (TextView) dd.findViewById(R.id.txtTime);
                txtSelectedCategory = (TextView) dd.findViewById(R.id.txtSelectCategory);


                editNewTask.setText(EditName.toString());
                editTaskDesc.setText(EditDesc.toString());
                txtDat.setText(EditDate.toString());
                txtTim.setText(EditTime.toString());
                txtSelectedCategory.setText(EditCategory.toString());

                final String PreviousName = EditName;


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
                                    for (DataSnapshot child : children.getChildren()) {
                                        Map<String, Object> task = (Map<String, Object>) child.getValue();

                                        adapter.Category_List.add(task.get("name").toString());
                                    }
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
                        Tab_Create create = new Tab_Create(mContext);
                        create.SetDate();

                    }
                });
                btnSelectTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tab_Create create = new Tab_Create(mContext);
                        create.SetTime();
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
                            Map<String, Task> data = new HashMap<String, Task>();
                            data.put(editNewTask.getText().toString(), task);

                            //ref.child("Categories/"+txtSelectedCategory.getText().toString()+"/"+PreviousName).setValue(data);
                            Toast.makeText(mContext, "Task has been Edited", Toast.LENGTH_SHORT).show();
                            dd.dismiss();
                        }
                    }
                });
                btnCancelTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dd.dismiss();
                    }
                });
                dd.show();
            }
        });*/

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 ref.child("Categories/" + EditCategory + "/" + EditName).removeValue(new Firebase.CompletionListener() {
                     @Override
                     public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                         firebase.removeValue();
                         Toast.makeText(mContext, "Task has been removed", Toast.LENGTH_SHORT).show();
                         getFragmentManager().beginTransaction().addToBackStack("TAG").replace(R.id.container,new Tab_Categories(mContext)).commit();

                     }
                 });


            }
        });

        SetValues();

        return rootView;
    }

    public void LoadEditTaskDialog(){

        final Dialog d = new Dialog(mContext);
        d.setContentView(R.layout.create_task_dialog);
        d.setTitle("Edit Your Task");

        editNewTask = (EditText) d.findViewById(R.id.editNewtask);
        editTaskDesc = (EditText) d.findViewById(R.id.editTaskDesc);
        btnSaveTask = (Button) d.findViewById(R.id.btnSaveTask);
        btnCancelTask = (Button) d.findViewById(R.id.btnCancelTask);
        btnSelectDate = (Button) d.findViewById(R.id.btnDatePicker);
        btnSelectTime = (Button) d.findViewById(R.id.btnTimePicker);
        btnSelect_Category = (Button) d.findViewById(R.id.btnSelect_Category);
        txtDat = (TextView) d.findViewById(R.id.txtDate);
        txtTim = (TextView) d.findViewById(R.id.txtTime);
        txtSelectedCategory = (TextView) d.findViewById(R.id.txtSelectCategory);

        final Context mcontext = this.getActivity();

        editNewTask.setText(EditName.toString());
        editTaskDesc.setText(EditDesc.toString());
        txtDat.setText(EditDate.toString());
        txtTim.setText(EditTime.toString());

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
                            for (DataSnapshot child : children.getChildren()) {
                                Map<String, Object> task = (Map<String, Object>) child.getValue();

                                adapter.Category_List.add(task.get("name").toString());
                            }
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
                Tab_Create create = new Tab_Create(mContext);
                create.SetDate();

            }
        });
        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tab_Create create = new Tab_Create(mContext);
                create.SetTime();
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
                    Map<Object, Task> data = new HashMap<Object, Task>();
                    data.put(editNewTask.getText().toString(), task);

                    ref.child("Tasks").setValue(data);
                    Toast.makeText(mcontext, "Task has been Edited", Toast.LENGTH_SHORT).show();
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

    public void SetValues()
    {
        int i;
       Custom_Tasks adp = new Custom_Tasks(name,R.layout.task,this.getActivity());
        for( i=0;i<name.size();i++) {
            if (name.get(i).equals(TName)) {
                //adp.Task_List.add(name.get(i).toString());
                txtTitle.setText(name.get(i).toString());
                txtDescription.setText(desc.get(i).toString());
                txtTime.setText(time.get(i).toString());
                txtDate.setText(date.get(i).toString());

            }
        }

    }


}
