package com.example.anggi;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.text.DecimalFormat;

import static java.lang.Float.max;


/**
 * A simple {@link Fragment} subclass.
 */
public class result extends Fragment {

    private TextView txtName, txtResult1, txtResult2, txtResult3, txtDecision, txtKvp1, txtObstruktif, txtRestriktif;
    private TextView txtDecObs, txtDecRes;
    private Button btnExit, btnSave;
    private TextView returnData;

    private Double dataSensor1, dataSensor2, dataSensor3, kvp1, kvp2;
    private Double kvpRes, kvpObs;

    private static DecimalFormat df2 = new DecimalFormat(".##");

    public result() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
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

        txtName = (TextView)getView().findViewById(R.id.txtName);
        txtKvp1 = (TextView)getView().findViewById(R.id.txtKvp1);
        txtResult1 = (TextView)getView().findViewById(R.id.txtResult1);
        txtResult2 = (TextView)getView().findViewById(R.id.txtResult2);
        txtResult3 = (TextView)getView().findViewById(R.id.txtResult3);
        btnExit = (Button)getView().findViewById(R.id.btnExit);
        txtRestriktif = (TextView)getView().findViewById(R.id.txtRestriktif);
        txtObstruktif = (TextView)getView().findViewById(R.id.txtObstruktif);
        txtDecObs = (TextView)getView().findViewById(R.id.txtDecObs);
        txtDecRes = (TextView)getView().findViewById(R.id.txtDecRes);
        returnData = (TextView)getView().findViewById(R.id.returnData);
        btnExit = (Button)getView().findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getResultAllData().execute("btnExit");
                Intent myIntent = new Intent(getContext(), dashboard.class);
                result.this.startActivity(myIntent);

            }
        });

        new getResultAllData().execute("getDatabaseSensor");
        new getResultAllData().execute("getDatabase");

    }

    public class getResultAllData extends AsyncTask<String, String, String>{

        @Override
        protected void onPostExecute(String s) {

            String valueSensor = null;

            String valueName = null;
            String valueGender = null;
            int valueBerat = 0;
            int valueTinggi = 0;
            int valueUsia = 0;
            int valueResult = 0;

            returnData.setText(s);

            try{
                JSONObject root = new JSONObject(s);
                boolean responseUser = root.has("user");
                boolean responseAccount = root.has("account");
                if(responseUser){
                    JSONArray array= root.getJSONArray("user");

                    for(int i=0; i<=array.length(); i++){
                        JSONObject object= array.getJSONObject(i);

                        valueSensor = object.getString("kpa");
                        if(i == 0){
                            dataSensor1 = Double.valueOf(valueSensor);
                            int newDataSensor1 = (int)Math.round(dataSensor1);
                            txtResult1.setText(String.valueOf(newDataSensor1));

                        }
                        if(i == 1){
                            dataSensor2 = Double.valueOf(valueSensor);
                            int newDataSensor2 = (int)Math.round(dataSensor2);
                            txtResult2.setText(String.valueOf(newDataSensor2));

                        }
                        if(i == 2){
                            dataSensor3 = Double.valueOf(valueSensor);
                            int newDataSensor3 = (int)Math.round(dataSensor3);
                            txtResult3.setText(String.valueOf(newDataSensor3));

                            kvp2 = Math.max(dataSensor1, Math.max(dataSensor2,dataSensor3));

                        }
                    }


                }
                if(responseAccount){
                    JSONArray array= root.getJSONArray("account");
                    JSONObject object= array.getJSONObject(0);

                    valueName = object.getString("nama");
                    valueGender = object.getString("gender");
                    valueTinggi = object.getInt("tinggi");
                    valueBerat = object.getInt("berat");
                    valueUsia = object.getInt("usia");

                    txtName.setText(valueName);

                    if(valueGender.equals("Male")){
                        valueResult = (int) ((27.63-0.112*valueUsia)*valueTinggi);
                    }
                    else{
                        valueResult = (int) ((21.78-0.101*valueUsia)*valueTinggi);
                    }
                    String myDataResult = Integer.toString(valueResult);
                    kvp1 = Double.valueOf(myDataResult);

                    txtKvp1.setText(myDataResult);

                    String resultRes, resultObs;

                    //kvpRes = (0.8*kvp2)/kvp1*100;
                    //kvpObs = (0.75*kvp2)/kvp1*100;
                    kvpRes = (kvp2/(0.75*kvp1))*100/100;
                    kvpObs = (kvp2/(0.8*kvp1))*100/100;

                    if(kvpRes > 79){
                        resultRes = "Normal";
                    }
                    else if(kvpRes >= 60 && kvpRes <= 79){
                        resultRes = "Ringan";
                    }
                    else if(kvpRes >= 30 && kvpRes <= 59){
                        resultRes = "Sedang";
                    }
                    else{
                        resultRes = "Berat";
                    }

                    if(kvpObs > 74){
                        resultObs = "Normal";
                    }
                    else if(kvpObs >= 60 && kvpObs <= 74){
                        resultObs = "Ringan";
                    }
                    else if(kvpObs >= 30 && kvpObs <= 59){
                        resultObs = "Sedang";
                    }
                    else{
                        resultObs = "Berat";
                    }

                    //txtRestriktif.setText(df2.format(kvpRes));
                    //txtObstruktif.setText(df2.format(kvpObs));
                    txtRestriktif.setText(kvpRes.toString());
                    txtObstruktif.setText(kvpObs.toString());

                    txtDecRes.setText(resultRes);
                    txtDecObs.setText(resultObs);

                    /*
                    Log.i("result kvp1: ",kvp1.toString());
                    Log.i("result kvp2: ",kvp2.toString());
                    Log.i("result res: ",kvpRes.toString());
                    Log.i("result obs: ",kvpObs.toString());
                    */
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }



            super.onPostExecute(s);
        }

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
    }
}
