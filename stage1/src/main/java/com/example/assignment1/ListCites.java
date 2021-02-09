package com.example.assignment1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
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
    CitesListAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Intent intent = new Intent();
        all_cites = (ArrayList<Cites>) intent.getSerializableExtra("list");
        if (all_cites == null) {
            addcites();
        }
        CreateView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addcites() {
            Cites temp = new Cites("America/Tijuana");
            Cites temp1 = new Cites("Asia/Macao");
            Cites temp2 = new Cites("US/Alaska");
            Cites temp3 = new Cites("Pacific/Honolulu");
            Cites temp4 = new Cites("US/Hawaii");
            Cites temp5 = new Cites("America/Phoenix");
            Cites temp6 = new Cites("America/Anchorage");
            Cites temp7 = new Cites("Europe/Oslo");
            Cites temp8 = new Cites("Europe/Paris");
            Cites temp9 = new Cites("Asia/Kuwait");
            Cites temp10 = new Cites("Asia/Dubai");
            Cites temp11 = new Cites("Asia/Karachi");
            Cites temp12 = new Cites("Asia/Istanbul");
            Cites temp13 = new Cites("Africa/Tripoli");
            Cites temp14 = new Cites("Canada/Pacific");
            Cites temp15 = new Cites("America/Creston");
            Cites temp16 = new Cites("America/Los_Angeles");
            Cites temp17 = new Cites("America/Chicago");
            all_cites = new ArrayList<Cites>();
            all_cites.add(temp);
            all_cites.add(temp1);
            all_cites.add(temp3);
            all_cites.add(temp4);
            all_cites.add(temp5);
            all_cites.add(temp6);
            all_cites.add(temp7);
            all_cites.add(temp8);
            all_cites.add(temp9);
            all_cites.add(temp10);
            all_cites.add(temp11);
            all_cites.add(temp12);
            all_cites.add(temp13);
            all_cites.add(temp14);
            all_cites.add(temp15);
            all_cites.add(temp16);
            all_cites.add(temp17);
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
        //setContentView(view);

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("list", selected_cites);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

}
