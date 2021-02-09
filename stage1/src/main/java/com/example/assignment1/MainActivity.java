package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Cites> cites;
    ArrayList<Cites> tmp;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cites = new ArrayList<Cites>();
        setContentView(R.layout.activity_main);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                tmp = (ArrayList<Cites>) data.getSerializableExtra("list");
                if (cites.size() == 0) {
                    cites = tmp;
                }
                else {
                    for (Cites c:tmp){
                        if (!cites.contains(c)) {
                            cites.add(c);
                        }
                    }
                }
                CreateView();
            }
        }
    }

    private void CreateView() {
        RecyclerView temp = (RecyclerView) findViewById(R.id.recycle);
        temp.setLayoutManager(new LinearLayoutManager(this));
        temp.setAdapter(new MainRecyclerView (cites));
    }

}