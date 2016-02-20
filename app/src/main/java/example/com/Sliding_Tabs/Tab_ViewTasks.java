package example.com.Sliding_Tabs;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import example.com.Categories.CategorySelection;
import example.com.Categories.CustomAdapter;
import example.com.ViewTasks.Custom_Tasks;
import example.com.task_reminder.MainActivity;
import example.com.task_reminder.R;

public class Tab_ViewTasks extends Fragment {

    ListView Listviewtask;
    View rootView;
    Firebase ref;


    ArrayList<String> Name = new ArrayList<String>();
    public Tab_ViewTasks() {
    }


    public Tab_ViewTasks(ArrayList<String> name) {
            this.Name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tab__view_tasks, container, false);

        ref.setAndroidContext(this.getActivity());
        ref = new Firebase("https://taskreminder.firebaseio.com");
        Tab_Categories t = new Tab_Categories(this.getActivity());

        Listviewtask = (ListView) rootView.findViewById(R.id.Listviewtask);

     /*   adapter = new ViewAllTasks(new ArrayList<String>(), R.layout.category_item, this.getActivity());
        Listviewtask.setAdapter(adapter);
        for(String item : Name)
        {
            adapter.tasklist.add(item);
        }*/
        /*ref.child("Tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : children.getChildren()) {
                        Map<String, Object> task = (Map<String, Object>) child.getValue();

                       adapter.Task_List.add(task.get("name").toString());
                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/




        //adapter.notifyDataSetChanged();

        return rootView;
    }


   /* @Override
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

}
