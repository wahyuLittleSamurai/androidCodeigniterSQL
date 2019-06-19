package com.example.anggi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class loginClass extends AsyncTask<String, String, String> {
    private TextView statusField,roleField;
    private Context context;
    private String dataNotNull;

    //flag 0 means get and 1 means post.(By default it is get.)
    public loginClass(Context context, TextView statusField, TextView roleField) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
    }

    @Override
    protected String doInBackground(String... arg0) {

        try {
            String username = (String) arg0[0];
            String password = (String) arg0[1];
            String link = "https://anggiproject.000webhostapp.com/index.php/dashboard/getAdminAccount/" + username + "/" + password + "/";

            URL url = new URL(link);
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = client.execute(request);
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {
                dataNotNull = line;
                sb.append(line);
                //break;
            }

            in.close();
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }


    }

    @Override
    protected void onPostExecute(String result) {
        Integer notNull = dataNotNull.length();
        this.roleField.setText(result);
        if(dataNotNull.equals("  1")){
            Intent intent = new Intent(context, dashboard.class);
            context.startActivity(intent);
            this.statusField.setText("Login Successful");
        }
    }
}
