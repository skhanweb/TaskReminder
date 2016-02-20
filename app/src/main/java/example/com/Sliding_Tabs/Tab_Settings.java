package example.com.Sliding_Tabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.Create.Tab_CreateList;
import example.com.Settings.CheckBoxEvent;
import example.com.Settings.SettingsAdapter;
import example.com.task_reminder.MainActivity;
import example.com.task_reminder.R;

public class Tab_Settings extends Fragment {

    TextView txtView;
    CheckBox chkbx;
    ListView list_Settings;
    SettingsAdapter adapter;

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
     * @return A new instance of fragment Tab_Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab_Settings newInstance(String param1, String param2) {
        Tab_Settings fragment = new Tab_Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Tab_Settings() {
        // Required empty public constructor
    }
    ArrayList<String> items = new ArrayList<String>();
    public void LoadSettingsList(){
        items.add("Allow Notification");
        items.add("Allow Alarm");
        items.add("Snooze Duration");
        adapter = new SettingsAdapter(this.getActivity(),R.layout.settings_list_item,items);
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
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.tab__settings, container, false);

        list_Settings = (ListView) rootView.findViewById(R.id.Settings_list);
        LoadSettingsList();
        list_Settings.setAdapter(adapter);
        mContext = this.getActivity();

        AddOnItemClickListener();
        adapter.notifyDataSetChanged();
        return rootView;
    }

    public void AddOnItemClickListener()
    {
        list_Settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                String option = (String) parent.getItemAtPosition(position);
                boolean SettingsCheck=false;
                final CheckBox chkbx = (CheckBox) view.findViewById(R.id.chkbxSettings);

                if ((option.equals("Allow Notification") && chkbx.isChecked()) || (option.equals("Allow Alarm") && chkbx.isChecked()) || (option.equals("Snooze Duration") && chkbx.isChecked())) {
                   /* if ((option.equals("Snooze Duration") && chkbx.isChecked()))
                    {
                        getFragmentManager().beginTransaction().replace(R.id.container,new Snooze()).commit();
                    }*/
                    chkbx.setChecked(false);
                    SettingsCheck = false;
                }

                else if ((option.equals("Allow Notification") && !chkbx.isChecked()) || (option.equals("Allow Alarm") && !chkbx.isChecked()) || (option.equals("Snooze Duration") && !chkbx.isChecked())) {
                    chkbx.setChecked(true);
                    SettingsCheck = true;
                }

                SharedPreferences.Editor settings = (SharedPreferences.Editor) mContext.getSharedPreferences("Name",Context.MODE_PRIVATE).edit();
                settings.putBoolean("SettingsCheck",SettingsCheck);
                settings.putString("SettingName",option);
                settings.commit();

            }
        });



       /* AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListView lv = (ListView) parent;
                if(lv.isItemChecked(position)){
                    Toast.makeText(getActivity(), "You checked " + items.get(position), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "You unchecked " + items.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        };
        list_Settings.setOnItemClickListener(itemClickListener);*/

        /*list_Settings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int counter = position;
                String Item = (String) parent.getItemAtPosition(counter);
                if (counter == 0)
                {}
                else if (counter == 1)
                {}
                else if (counter == 2)
                {}
                else
                {}


            }
        });*/


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
