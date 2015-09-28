package com.example.root.hospitalsnearyou.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
            + "hospitalId text, state text , city text , district text , h_name text ," +
            "address text , pincode text , contact text , helpline text ," +
            "fax text , category text , website text , email text ," +
            "blood_component text , blood_group text , service_time text ," +
            "latitude text ," + "longitude integer  );";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    ArrayList<ModelClassDB> hospitalData = new ArrayList<ModelClassDB>();
    private Context context;


    public void insertIntoDb(ArrayList<ModelClassDB> hospitalData) {
        this.hospitalData = hospitalData;
        for (int i = 0; i < hospitalData.size(); i++) {
            ContentValues values = new ContentValues();
//            values.put(KEY_ADDRESS, hospitalData.get(i).getAddress());
//            values.put(KEY_blood_component, hospitalData.get(i).getBlood_component());
//            values.put(KEY_blood_group, hospitalData.get(i).getBlood_group());
//            values.put(KEY_category, hospitalData.get(i).getCategory());
            values.put(KEY_CITY, hospitalData.get(i).getCity());
//            values.put(KEY_contact, hospitalData.get(i).getContact());
//            values.put(KEY_DISTRICT, hospitalData.get(i).getDistrict());
//            values.put(KEY_email, hospitalData.get(i).getEmail());
//            values.put(KEY_fax, hospitalData.get(i).getFax());
//            values.put(KEY_helpline, hospitalData.get(i).getHelpline());
//            values.put(KEY_HOSPITAL_NAME, hospitalData.get(i).getHospitalName());
//            values.put(KEY_HOSPITAL_ID, hospitalData.get(i).getHospitalId());
//            values.put(KEY_latitude, hospitalData.get(i).getLatitude());
//            values.put(KEY_longitude, hospitalData.get(i).getLongitude());
//            values.put(KEY_pincode, hospitalData.get(i).getPincode());
//            values.put(KEY_service_time, hospitalData.get(i).getService_time());
//            values.put(KEY_STATE hospitalData.get(i).getState());
            // values.put(KEY_website, hospitalData.get(i).getWebsite());
            open();
            db.insert(TABLE_NAME, null, values);
        }
    }

    public HospitalDataBase(Context context) {
        this.context = context;
    }

    public HospitalDataBase open() {
        dbHelper = new DatabaseHelper(context);
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
            Log.e("dbdb", "created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS hospital");
            onCreate(db);
        }
    }

    public ArrayList<ModelClassDB> readFromDatabase() {
        ArrayList<ModelClassDB> hospitalDataList = new ArrayList<>();
        String read = "";
        read += "select * from " + TABLE_NAME;
        open();
        Cursor cursor = db.rawQuery(read, null);

        if (cursor.moveToFirst())
            do {
                String city = cursor.getString(cursor.getColumnIndex(KEY_CITY));
                String state = cursor.getString(cursor.getColumnIndex(KEY_STATE));
                long _id = Long.parseLong(cursor.getString(cursor.getColumnIndex(KEY_ROW_ID)));
                ModelClassDB modelClassDB = new ModelClassDB();
//                modelClassDB.set_id(_id);
                modelClassDB.setCity(city);
                modelClassDB.setState(state);
                hospitalDataList.add(modelClassDB);
                Log.e("data",city);

            } while (cursor.moveToNext());
        cursor.close();
        close();
        return hospitalDataList;
    }
}
