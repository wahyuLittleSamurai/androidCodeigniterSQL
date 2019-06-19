package com.example.anggi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class addUserClass extends AsyncTask<String, String, String> {
    private TextView statusField,roleField;
    private Context context;


    //flag 0 means get and 1 means post.(By default it is get.)
    public addUserClass(Context context, TextView statusField, TextView roleField) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
    }

    @Override
    protected String doInBackground(String... arg0) {

        try{
            String mynama = (String)arg0[0];
            String strGender = (String)arg0[1];
            String usia = (String)arg0[2];
            String tinggi = (String)arg0[3];
            String berat = (String)arg0[4];

            String link="https://nazarene-debit.000webhostapp.com/index.php/dashboard/addUser/";
            String data  = URLEncoder.encode("nama", "UTF-8") + "=" +
                    URLEncoder.encode(mynama, "UTF-8");
            data += "&" + URLEncoder.encode("gender", "UTF-8") + "=" +
                    URLEncoder.encode(strGender, "UTF-8");
            data += "&" + URLEncoder.encode("usia", "UTF-8") + "=" +
                    URLEncoder.encode(usia, "UTF-8");
            data += "&" + URLEncoder.encode("tinggi", "UTF-8") + "=" +
                    URLEncoder.encode(tinggi, "UTF-8");
            data += "&" + URLEncoder.encode("berat", "UTF-8") + "=" +
                    URLEncoder.encode(berat, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();

        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPostExecute(String result) {

        this.statusField.setText("Done" + result);
        this.roleField.setText(result);


    }



}
