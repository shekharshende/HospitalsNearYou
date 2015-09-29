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
    private static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_STATE = "state";
    public static final String KEY_CITY = "city";
    private static final String KEY_HOSPITAL_PRIVATE = "pvt";
    private static final String KEY_category = "category";
    private static final String KEY_SYSTEM_OF_MEDICINE = "SystemsOfMedicine";
    private static final String KEY_contact = "contact";
    private static final String KEY_PINCODE = "pincode";
    private static final String KEY_email = "email";
    private static final String KEY_website = "website";
    private static final String KEY_Specializations = "Specializations";
    private static final String KEY_SERVICES = "Services";
    private static final String TABLE_NAME = "hospital";
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "hospitalDb";

    private static final String DATABASE_CREATE = "create table hospital(_id integer primary key autoincrement default 1, "
            + "hospitalId text, timestamp text, state text , city text , pvt text , category text , SystemsOfMedicine text ," +
            "contact text , pincode text , email text ," +
            "website text , Specializations text , Services text);";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    ArrayList<ModelClassDB> hospitalData = new ArrayList<ModelClassDB>();
    private Context context;


    public void insertIntoDb(ArrayList<ModelClassDB> hospitalData) {
        this.hospitalData = hospitalData;
        for (int i = 0; i < hospitalData.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_HOSPITAL_ID, hospitalData.get(i).getHospitalId());
            values.put(KEY_TIMESTAMP, hospitalData.get(i).getTimestamp());
            values.put(KEY_STATE, hospitalData.get(i).getState());
            values.put(KEY_CITY, hospitalData.get(i).getCity());
            values.put(KEY_HOSPITAL_PRIVATE, hospitalData.get(i).getPvt());
            values.put(KEY_category, hospitalData.get(i).getCategory());
            values.put(KEY_SYSTEM_OF_MEDICINE, hospitalData.get(i).getSystemsOfMedicine());
            values.put(KEY_contact, hospitalData.get(i).getContact());
            values.put(KEY_PINCODE, hospitalData.get(i).getPincode());
            values.put(KEY_email, hospitalData.get(i).getEmail());
            values.put(KEY_website, hospitalData.get(i).getWebsite());
            values.put(KEY_Specializations, hospitalData.get(i).getSpecializations());
            values.put(KEY_SERVICES, hospitalData.get(i).getServices());
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
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
            Log.e("dbdb", "upgraded");
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

            } while (cursor.moveToNext());
        cursor.close();
        close();
        return hospitalDataList;
    }
}
