package example.com.task_reminder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


public class Sign_Up extends ActionBarActivity {

    Button btnSignup;
    EditText editSignUpEmail;
    EditText editPassword;
    EditText ediConfirmPassword;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);


        editSignUpEmail = (EditText) findViewById(R.id.SignUpEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        ediConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);


        ref.setAndroidContext(this);
        ref = new Firebase("https://taskreminder.firebaseio.com/");


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = editSignUpEmail.getText().toString();
                final String Password = editPassword.getText().toString();
                final String ConfirmPassword = ediConfirmPassword.getText().toString();

                if (Email.isEmpty() || Password.isEmpty() || ConfirmPassword.isEmpty())
                    Toast.makeText(Sign_Up.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                else  if (!Password.equals(ConfirmPassword))
                    Toast.makeText(Sign_Up.this, "Password do no match", Toast.LENGTH_SHORT).show();

                else {
                    ref.createUser(Email, Password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            System.out.println("Successfully created user account with uid: " + result.get("uid"));
                            Toast.makeText(Sign_Up.this, "Successfully Sign Up", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Sign_Up.this,Sign_In.class);
                            startActivity(i);
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            // there was an error
                            Toast.makeText(Sign_Up.this, "There was an error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign__up, menu);
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
}
