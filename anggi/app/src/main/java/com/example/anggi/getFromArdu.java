package com.example.anggi;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class getFromArdu extends Fragment {

    private TextView role;
    private TextView status;
    private Switch switch1;
    private Switch switch2;
    private Switch switch3;
    private Button btnReset, btnNext;
    private Context context;

    public getFromArdu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_from_ardu, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        role = (TextView)getView().findViewById(R.id.role);
        status = (TextView)getView().findViewById(R.id.status);
        switch1 = (Switch) getView().findViewById(R.id.switch1);
        switch2 = (Switch) getView().findViewById(R.id.switch2);
        switch3 = (Switch) getView().findViewById(R.id.switch3);
        btnReset = (Button)getView().findViewById(R.id.btnReset);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                new getDatabaseUser().execute();
            }
        },0,2000);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new resetData().execute("btnExit");
                Intent myIntent = new Intent(getContext(), dashboard.class);
                getFromArdu.this.startActivity(myIntent);
            }
        });


    }
    public class resetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            try{
                String myDatabase = (String)strings[0];

                String link="https://nazarene-debit.000webhostapp.com/index.php/dashboard/"+myDatabase;

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write("");
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    //break;
                }

                return sb.toString();

            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {

            Log.i("return data: ", s);
            super.onPostExecute(s);

        }
    }



    public class getDatabaseUser extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try{

                String link="https://nazarene-debit.000webhostapp.com/index.php/dashboard/getDatabaseSensor/";

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write("");
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    //break;
                }

                return sb.toString();

            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String valueSwitch = null;
            status.setText(s);
            try {
                JSONObject root = new JSONObject(s);
                JSONArray array= root.getJSONArray("user");

                for(int i=0; i<=array.length(); i++){
                    JSONObject object= array.getJSONObject(i);
                    role.setText(object.getString("kpa"));
                    valueSwitch = object.getString("kpa");
                    if(valueSwitch.equals("0")){
                        if(i == 0){
                            switch1.setChecked(false);
                        }
                        if(i == 1){
                            switch2.setChecked(false);
                        }
                        if(i == 2){
                            switch3.setChecked(false);
                        }
                    }
                    else{
                        if(i == 0){
                            switch1.setChecked(true);
                        }
                        if(i == 1){
                            switch2.setChecked(true);
                        }
                        if(i == 2){
                            switch3.setChecked(true);
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }



            super.onPostExecute(s);
        }
    }

}
