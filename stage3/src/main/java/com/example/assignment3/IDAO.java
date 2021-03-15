package com.example.assignment3;

import java.util.ArrayList;
import java.util.Hashtable;

public interface IDAO {
    public void save(Hashtable<String,String> attributes);
    public void save1(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public ArrayList<Hashtable<String,String>> load1();
    public Hashtable<String,String> load(String id);
    public void delete(Hashtable<String,String> attributes);
}
