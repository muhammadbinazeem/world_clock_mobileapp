package com.example.assignment2;

import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Hashtable;

public interface IDAO {
    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public Hashtable<String,String> load(String id);
    public void delete(Hashtable<String,String> attributes);
}
