package example.com.task_reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import example.com.Model.User;


public class Sign_In extends ActionBarActivity {

    Button btnSignIn;
    Button btnSignUp;

    EditText editEmail;
    EditText editPassword;
    public String username;

    Context mContext;
    Firebase ref;
    String Email = "";
    String Password="";
   public int UserId=0;

    View rootView;

    public static String Account_Token="";


    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignup);

        ref.setAndroidContext(this);
        ref = new Firebase("https://taskreminder.firebaseio.com/");

        mContext = this;

        session = new SessionManager(this);
        HashMap<String,String> user = new HashMap<String,String>();
        user = session.getUserDetails();
        //try {
            if (user.get(session.KEY_EMAIL) != null) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
       // }
        //catch (Exception ex)
       // {
         //   ex.printStackTrace();
        //}


            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Email = editEmail.getText().toString();
                    Password = editPassword.getText().toString();

                    if (Email.isEmpty() || Password.isEmpty())
                        Toast.makeText(mContext, "Please Enter Credentials", Toast.LENGTH_SHORT).show();

                    else {
                        session.createLoginSession(Email,Password);

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }

                }
            });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Sign_In.this, Sign_Up.class);
                    startActivity(i);

                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign__in, menu);
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
  /*  public void CheckAuthentication(final String Email, final String Password) {

        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        ref.authWithPassword(Email, Password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {

                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                Account_Token = authData.getToken().toString();

                editor.putBoolean(IS_LOGIN, true);

                // Storing email in pref
                editor.putString(KEY_EMAIL, Email);
                editor.putString(KEY_PASSWORD, Password);
                editor.putString(TOKEN, Account_Token);

                // commit changes
                editor.commit();


            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Toast.makeText(mContext, "There was an error/ Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean GetUserDetails()
    {
        String StoredEmail = pref.getString(KEY_EMAIL,null);
        String StoredPassword = pref.getString(KEY_PASSWORD,null);
        String StoredToken = pref.getString(TOKEN,null);

        if ((StoredEmail.equals("") || StoredEmail==null) && (StoredPassword.equals("") || StoredPassword==null)
                && (StoredToken.equals("") || StoredToken ==null))
            return false;
        else
        return true;
    }

    HashMap<String, String> user = new HashMap<String, String>();
    public HashMap<String, String> getUserDetails(){
        // user name
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user email id
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        //get auth Id
        user.put("TOKEN", pref.getString("TOKEN", null));

        // return user
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, Sign_In.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        mContext.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(Sign_In.this, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }
        else
        {
            CheckAuthentication(Email,Password);
        }

    }*/
}
