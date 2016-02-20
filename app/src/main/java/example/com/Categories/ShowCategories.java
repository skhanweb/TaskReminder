package example.com.Categories;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import example.com.Sliding_Tabs.Tab_Categories;
import example.com.task_reminder.R;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ShowCategories extends Fragment {

    ListView listView;
    Firebase ref;
    Context mContext;
    List<String> CategoryList;
    CategorySelection adapter;
    String cat_name = "";

    public ShowCategories() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.show_categories, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);

        ref.setAndroidContext(this.getActivity());
        ref = new Firebase("https://taskreminder.firebaseio.com/");

        //category = new Category();
        mContext = this.getActivity();

         adapter = new CategorySelection(new ArrayList<String>(), R.layout.category_item,this.getActivity());
        listView.setAdapter(adapter);

        AddListener();



        adapter.notifyDataSetChanged();

        return rootView;
    }

    public void Load_Category_In_TaskDialog()
    {
        Tab_Categories t = new Tab_Categories(this.getActivity());
        cat_name = t.CategoryName.toString();
        ArrayList<String> LoadCat = new ArrayList<String>();

        adapter  = new CategorySelection(new ArrayList<String>(), R.layout.list_category_selection, this.getActivity());

        listView.setAdapter(adapter);

        ref.child("Categories/"+cat_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : children.getChildren()) {
                        Map<String,Object> task = (Map<String,Object>) child.getValue();
                        // String name = (String) mapMsg.get("name");
                        // String message = (String) mapMsg.get("date");
                        // String title = (String) mapMsg.get("time");
                        //msg.add(new Message(title, message, isRead));
                        adapter.Category_List.add(task.get("name").toString());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        adapter.notifyDataSetChanged();



    }


    public  void AddListener(){

        ref.child("Categories/"+cat_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    for (DataSnapshot child : children.getChildren()) {
                        Map<String, Object> task = (Map<String, Object>) child.getValue();
                        // String name = (String) mapMsg.get("name");
                        // String message = (String) mapMsg.get("date");
                        // String title = (String) mapMsg.get("time");
                        //msg.add(new Message(title, message, isRead));
                        adapter.Category_List.add(task.get("name").toString());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


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
