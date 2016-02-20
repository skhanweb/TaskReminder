package example.com.task_reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import example.com.Model.Task;
import example.com.Navigation_Drawer.FragmentDrawer;
import example.com.Notification.NotificationView;
import example.com.Sliding_Tabs.SlidingTabLayout;
import example.com.Sliding_Tabs.Tab_Categories;
import example.com.Sliding_Tabs.Tab_Create;
import example.com.Sliding_Tabs.Tab_Settings;
import example.com.Sliding_Tabs.Tab_ViewTasks;
import example.com.Sliding_Tabs.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    Toolbar mToolbar;
    FragmentDrawer drawerFragment;
    TextView  txtName;
    Sign_In signin;

    ViewPager pager;
    //ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    String Titles[] = {"Categories", "View", "Create", "Settings"};
    int No_of_tabs = 4;

    Firebase ref;
    Context mContext;
    SessionManager session;

    String Email;
    String Password;
    HashMap<String, String> user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref.setAndroidContext(this);
        ref =new Firebase("https://taskreminder.firebaseio.com/");

        session = new SessionManager(this);
        session.checkLogin();

        // get user data from session
        user = session.getUserDetails();

        // Email
         Email = user.get(session.KEY_EMAIL);

        // Password
         Password = user.get(session.KEY_PASSWORD);

        Intent i = new Intent(this,MyService.class);
        this.startService(i);

        mContext = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // display the first navigation drawer view on app launch
        displayView(0);

        /*if (name.equals("") || email.equals(""))
        {
            Intent intent = new Intent(this,MyService.class);
            this.stopService(intent);
        }*/

       /* viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TableLayout) findViewById(R.id.tabs);
        setupViewPager(viewPager);*/


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        TextView txtEmail = (TextView) drawerFragment.getView().findViewById(R.id.txtEmialDisplay);
        txtEmail.setText(Email);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> TName =new ArrayList<String>();
    public ArrayList<String> TCategory =new ArrayList<String>();
    public ArrayList<String> TDesc =new ArrayList<String>();
    public ArrayList<String> TTime =new ArrayList<String>();
    public ArrayList<String> TDate =new ArrayList<String>();



    public void GetAllTasks(){
        try {
            ref.child("Tasks").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                   // for (DataSnapshot children : dataSnapshot.getChildren()) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                           // Map<String, Object> task = (Map<String, Object>) child.getValue();
                            Task task = child.getValue(Task.class);

                            TName.add(task.getName().toString());
                            TDesc.add(task.getDescription().toString());
                            TDate.add(task.getDate().toString());
                            TTime.add(task.getTime().toString());
                            TCategory.add(task.getCategory().toString());

                           // TName.add(task.get("name").toString());
                            //TDesc.add(task.get("description").toString());
                            //TDate.add(task.get("date").toString());
                            //TTime.add(task.get("time").toString());
                            //TCategory.add(task.get("category").toString());
                        }
                    }
                //}

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        // display the first navigation drawer view on app launch
        displayView(position);
    }

    public void displayView(int position) {


        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new Tab_Categories(mContext);
                title = getString(R.string.title_Category);
                break;
          /*  case 1:
                GetAllTasks();
                fragment = new Tab_ViewTasks(TName);
                title = getString(R.string.title_View);
                break;*/
            case 1:
                fragment = new Tab_Create(mContext);
                title = getString(R.string.title_Create);
                break;
           /* case 3:
                fragment = new Tab_Settings();
                title = getString(R.string.title_Settings);
                break;*/
            case 2:
                title = getString(R.string.title_Logout);
                break;

            default:
                break;
        }


        if (title.equals(getString(R.string.title_Logout)))
        {
            /*SharedPreferences.Editor preferences = (SharedPreferences.Editor) getSharedPreferences("TOKEN",MODE_PRIVATE).edit();
            Sign_In.Account_Token = "";
            preferences.putString("ACC_TOKEN",Sign_In.Account_Token);
            Intent i = new Intent(MainActivity.this,Sign_In.class);
            startActivity(i);*/
            session.logoutUser();
            Intent i = new Intent(this,MyService.class);
            this.stopService(i);
        }
        else {
             if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
        }
    }

}

