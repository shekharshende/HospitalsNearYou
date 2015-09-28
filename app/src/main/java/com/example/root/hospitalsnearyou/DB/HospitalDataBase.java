package com.example.root.hospitalsnearyou.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.root.hospitalsnearyou.ModelClass.ModelClassDB;

import java.util.ArrayList;

/**
 * Created by root on 28/9/15.
 */
public class HospitalDataBase {
    private static final String KEY_ROW_ID = "_id";
    private static final String KEY_HOSPITAL_ID = "hospitalId";
    public static final String KEY_STATE = "state";
    public static final String KEY_CITY = "city";
    public static final String KEY_DISTRICT = "district";
    private static final String KEY_HOSPITAL_NAME = "h_name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_pincode = "pincode";
    private static final String KEY_contact = "contact";
    private static final String KEY_helpline = "helpline";
    private static final String KEY_fax = "fax";
    private static final String KEY_category = "category";
    private static final String KEY_website = "website";
    private static final String KEY_email = "email";
    private static final String KEY_blood_component = "blood_component";
    private static final String KEY_blood_group = "blood_group";
    private static final String KEY_service_time = "service_time";
    private static final String KEY_latitude = "latitude";
    private static final String KEY_longitude = "longitude";
    private static final String TABLE_NAME = "hospital";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "hospitalDb";

    private static final String DATABASE_CREATE = "create table hospital(_id integer primary key autoincrement default 1, "
            + "hospitalId text not null, state text not null, city text not null, district text not null, h_name text not null," +
            "address text not null, pincode text not null, contact text not null, helpline text not null," +
            "fax text not null, category text not null, website text not null, email text not null," +
            "blood_component text not null, blood_group text not null, service_time text not null," +
            "latitude text not null," + "longitude integer not null );";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    ArrayList<ModelClassDB> hospitalData = new ArrayList<ModelClassDB>();


    public void insertIntoDb(ArrayList<ModelClassDB> hospitalData) {
        this.hospitalData = hospitalData;
        for (int i = 0; i < hospitalData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_ADDRESS, hospitalData.get(i).getAddress());
            values.put(KEY_blood_component, hospitalData.get(i).getBlood_component());
            values.put(KEY_blood_group, hospitalData.get(i).getBlood_group());
            values.put(KEY_category, hospitalData.get(i).getCategory());
            values.put(KEY_CITY, hospitalData.get(i).getCity());
            values.put(KEY_contact, hospitalData.get(i).getContact());
            values.put(KEY_DISTRICT, hospitalData.get(i).getDistrict());
            values.put(KEY_email, hospitalData.get(i).getEmail());
            values.put(KEY_fax, hospitalData.get(i).getFax());
            values.put(KEY_helpline, hospitalData.get(i).getHelpline());
            values.put(KEY_HOSPITAL_NAME, hospitalData.get(i).getHospitalName());
            values.put(KEY_HOSPITAL_ID, hospitalData.get(i).getHospitalId());
            values.put(KEY_latitude, hospitalData.get(i).getLatitude());
            values.put(KEY_longitude, hospitalData.get(i).getLongitude());
            values.put(KEY_pincode, hospitalData.get(i).getPincode());
            values.put(KEY_service_time, hospitalData.get(i).getService_time());
            values.put(KEY_STATE, hospitalData.get(i).getState());
            values.put(KEY_website, hospitalData.get(i).getWebsite());
        }
    }

    public HospitalDataBase open() {
//        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {


        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
            Log.e("db", "created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS playlist");
            onCreate(db);
        }
    }

}
