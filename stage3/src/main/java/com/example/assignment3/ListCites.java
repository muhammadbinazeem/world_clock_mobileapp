package com.example.assignment3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout.LayoutParams;


public class ListCites extends AppCompatActivity {
    private ArrayList<Cites> all_cites;
    private ArrayList<Cites> selected_cites;
    EditText text;
    IDAO dao;
    CitesListAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        dao = new DbDAO(this);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent intent = new Intent();
        all_cites = (ArrayList<Cites>) intent.getSerializableExtra("list");
        all_cites = Cites.load1(dao);
        if (all_cites == null) {
            Intent serviceIntent = new Intent(this,timezoneservice.class);
            startService(serviceIntent);
            all_cites = new ArrayList<Cites>();
        }
        CreateView();
    }

    private EditText createText(){
        text = new EditText(this);
        text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        text.setHint("Search");
        text.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable arg0) { }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) { }

            @Override
            public void onTextChanged(CharSequence text, int start, int before,int count) {
                adapter.getFilter().filter(text.toString());
            }

        });

        return text;
    }

    private ListView CreateView1() {
        ListView view = new ListView(this);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //Adapter
        adapter = new CitesListAdapter(this, all_cites);
        selected_cites = adapter.selectedCites;
        view.setAdapter(adapter);

        return view;
    }

    private void CreateView(){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        layout.setFocusable(true);
        layout.setFocusableInTouchMode(true);


        layout.addView(createText());
        layout.addView(CreateView1());

        setContentView(layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("list", selected_cites);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    /*public void onPause(){
        super.onPause();

        for(Cites note : selected_cites){
            note.save();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume(){
        super.onResume();

        selected_cites = Cites.load(dao);
    }*/
}
