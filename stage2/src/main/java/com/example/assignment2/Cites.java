package com.example.assignment2;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.UUID;

public class Cites implements  java.io.Serializable {

    private String Name;
    private String BehindOrAhead;
    private String Time;
    private transient IDAO dao = null;

    public Cites(){
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cites(String name){
        init();
        this.Name = name.substring(name.lastIndexOf("/")+1);
        TimeZone.setDefault(TimeZone.getTimeZone(name));
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        this.Time = df.format(date);
        this.BehindOrAhead = temp(name);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Cites(String name, IDAO dao){
        init();
        //String name1 = name;
        this.Name = name.substring(name.lastIndexOf("/")+1);
        TimeZone.setDefault(TimeZone.getTimeZone(name));
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
        this.Time = df.format(date);
        this.BehindOrAhead = temp(name);
        this.dao = dao;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String temp(String name) {
        String a ;
        a = "Today, ";
        ZoneId zone1 = ZoneId.of(name);
        ZoneId zone2 = ZoneId.of("Asia/Karachi");

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        long hoursBetween = ChronoUnit.HOURS.between(now2, now1);
        if (hoursBetween > 0) {
            a += hoursBetween;
            a += "hr Ahead";
        }
        else {
            int b = (int) hoursBetween;
            b = b + (-b) + (-b);
            a += b;
            a += "hr Behind";
        }
        return a;
    }

    private void init() {
        this.Name = UUID.randomUUID().toString();
        this.BehindOrAhead = UUID.randomUUID().toString();
    }

    public void setName(String content) {
        this.Name = content;
    }

    public String getName(){
        return Name;
    }

    public String getTime() {
        return Time;
    }

    public String getBehindOrAhead() {
        return BehindOrAhead;
    }

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Cites temp = (Cites) object;
            if (this.Name.equals(temp.getName())) {
                result = true;
            }
        }
        return result;
    }

    public void save(Cites temp){

        if (dao != null){

            Hashtable<String,String> data = new Hashtable<String, String>();

            data.put("Name",temp.Name);
            data.put("Time",temp.Time);
            data.put("BehindOrAhead",temp.BehindOrAhead);

            dao.save(data);
        }
    }

    public void load(Hashtable<String,String> data){
        Name = data.get("Name");
        Time = data.get("Time");
        BehindOrAhead = data.get("BehindOrAhead");
    }

    public void delete() {
        if(dao != null) {
            Hashtable<String, String> data = new Hashtable<String, String>();

            data.put("Name", this.Name);
            data.put("Time", this.Time);
            data.put("BehindOrAhead", this.BehindOrAhead);

            dao.delete(data);
        }
    }

    public void save(){

        if (dao != null){

            Hashtable<String,String> data = new Hashtable<String, String>();

            data.put("Name",Name);
            data.put("Time",Time);
            data.put("BehindOrAhead",BehindOrAhead);

            dao.save(data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Cites> load(IDAO dao){
        ArrayList<Cites> cites = new ArrayList<Cites>();
        if(dao != null){

            ArrayList<Hashtable<String,String>> objects = dao.load();
            for(Hashtable<String,String> obj : objects){
                Cites temp = new Cites();
                temp.load(obj);
                cites.add(temp);
            }
        }
        return cites;
    }

    public void connect(Cites temp, IDAO dao) {
        this.Name = temp.getName();
        this.Time = temp.Time;
        this.BehindOrAhead = temp.getBehindOrAhead();
        this.dao = dao;
    }
}
