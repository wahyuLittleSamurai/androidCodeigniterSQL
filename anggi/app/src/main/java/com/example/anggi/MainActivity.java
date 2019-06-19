package com.example.anggi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText emailAdmin;
    private EditText passwordAdmin;
    private Button signinAdmin;

    private TextView status,role;

    public static final String MyPREFERENCES = "adminPreference" ;
    public static final String Email = "emailKey";
    public static final String Password = "paswordKey";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        emailAdmin = (EditText)findViewById(R.id.input_email);
        passwordAdmin = (EditText)findViewById(R.id.input_password);
        signinAdmin = (Button) findViewById(R.id.btn_sigin);
        status = (TextView)findViewById(R.id.textView);
        role = (TextView)findViewById(R.id.textView2);

        status.setVisibility(View.INVISIBLE);
        role.setVisibility(View.INVISIBLE);

        emailAdmin.setText("root");
        passwordAdmin.setText("admin");

        signinAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailAdmin.getText().toString();
                String password = passwordAdmin.getText().toString();

                status.setVisibility(View.VISIBLE);
                role.setVisibility(View.VISIBLE);

                new loginClass(MainActivity.this, status, role).execute(email,password);

            }
        });
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
}
