package example.com.ViewTasks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.com.task_reminder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTask extends Fragment {

    View rootView;

    public EditTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // rootView = inflater.inflate(R.layout.edit_task, container, false);



        return rootView;
    }


}
