package com.example.anggi;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class addUser extends Fragment {

    private Spinner listGender;
    private Button btnRegister, btnNext;
    private EditText nama;
    private EditText usia;
    private EditText tinggi;
    private EditText berat;
    private TextView status,role;

    public addUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
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

        listGender = (Spinner)getView().findViewById(R.id.myGender);
        String[] items = new String[]{"Gender", "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);

        listGender.setAdapter(adapter);

        btnRegister = (Button)getView().findViewById(R.id.btn_register);
        btnNext = (Button)getView().findViewById(R.id.btn_next);
        nama = (EditText)getView().findViewById(R.id.namaUser);
        usia = (EditText)getActivity().findViewById(R.id.usiaUser);
        tinggi = (EditText)getActivity().findViewById(R.id.tinggiUser);
        berat = (EditText)getActivity().findViewById(R.id.beratUser);


        status = (TextView) getView().findViewById(R.id.textView3) ;
        role = (TextView) getView().findViewById(R.id.textView4) ;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNama = nama.getText().toString();
                String strUsia = usia.getText().toString();
                String strTinggi = tinggi.getText().toString();
                String strBerat = berat.getText().toString();
                String spinnerItem = listGender.getSelectedItem().toString();
                new addUserClass(getActivity(), status, role).execute(strNama,spinnerItem, strUsia, strTinggi, strBerat);


            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment();
            }
        });


    }

    private void swapFragment(){
        getFromArdu newGamefragment = new getFromArdu();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.valueContent, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
