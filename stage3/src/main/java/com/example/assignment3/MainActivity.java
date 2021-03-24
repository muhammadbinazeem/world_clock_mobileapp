package com.example.assignment3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Cites> cites;
    ArrayList<Cites> cites1;
    ArrayList<Cites> tmp;
    IDAO dao;
    final int REQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cites = new ArrayList<Cites>();
        setContentView(R.layout.activity_main);
        dao = new DbDAO(this);
        cites = Cites.load(dao);
        CreateView();
    }

    private void addButton() {
        Intent intent = new Intent(this,ListCites.class);
        intent.putExtra("list",cites);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void buttonClick(View v) {
        if (v.getId() == R.id.floatingActionButton) {
            addButton();
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,  data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                tmp = (ArrayList<Cites>) data.getSerializableExtra("list");
                cites = Cites.load(dao);
                if (cites.size() == 0) {
                    cites = tmp;
                    for (Cites c : cites) {
                        Cites d = new Cites();
                        d.connect(c,dao);
                        d.save();
                    }
                } else {
                    for (Cites c : tmp) {
                        if (!cites.contains(c)) {
                            cites.add(c);
                            Cites d = new Cites();
                            d.connect(c,dao);
                            d.save();
                        }
                    }
                }
            }
            CreateView();
        }
    }

   @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateView() {
        RecyclerView temp = (RecyclerView) findViewById(R.id.recycle);
        temp.setLayoutManager(new LinearLayoutManager(this));
        cites1 = Cites.load(dao);
        temp.setAdapter(new MainRecyclerView (cites1,dao));

        //  time update
        final Handler handler = new Handler();
        final int delay = 1000 ; //1000 milliseconds = 1 sec

        handler.postDelayed(new Runnable() {
            public void run(){
                // call for adapter method
                cites1 = Cites.updatetime(cites);
                temp.setAdapter(new MainRecyclerView (cites1,dao));
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void onPause(){
        super.onPause();
        for(Cites c : cites){
            c.save();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onResume(){
        super.onResume();
        cites = Cites.load(dao);
    }
}