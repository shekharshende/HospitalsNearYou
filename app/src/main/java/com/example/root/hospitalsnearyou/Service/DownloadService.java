package com.example.root.hospitalsnearyou.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.root.hospitalsnearyou.DB.HospitalDataBase;
import com.example.root.hospitalsnearyou.ModelClass.ModelClassDB;
import com.example.root.hospitalsnearyou.Parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadService extends Service {
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;
    HospitalDataBase hospitalDataBase;

    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hospitalDataBase = new HospitalDataBase(getApplicationContext());
//        hospitalDataBase.readFromDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isNetworkAvailable(getApplicationContext())) {
                    jsonObject = jsonParser.makeHttpRequest("https://data.gov.in/api/datastore/resource.json?resource_id=b4d77a09-9cdc-4a5b-b900-8fddb78f3cbe&api-key=abcac7ea2c8e7c924ea3477c3c8741aa");
                   ArrayList<ModelClassDB> arrayList = hospitalDataBase.readFromDatabase();
                if(arrayList.size()==0){
                    addToDatabase(jsonObject);
                } }else {
                    Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
        return START_STICKY;
    }

    private boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void addToDatabase(JSONObject jsonObject1) {
        ArrayList<ModelClassDB> arrayList = new ArrayList<>();
        if (jsonObject1 != null) {
            try {
                JSONArray jsonArray = this.jsonObject.getJSONArray("records");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject array = jsonArray.getJSONObject(i);
                    ModelClassDB modelClassDB = new ModelClassDB();
                    modelClassDB.setHospitalId(array.getString("id"));
                    modelClassDB.setState(array.getString("State"));
                    modelClassDB.setCity(array.getString("City"));
                    modelClassDB.setPvt(array.getString("Hospital / Private"));
                    modelClassDB.setCategory(array.getString("Category"));
                    modelClassDB.setSpecializations(array.getString("Specializations"));
                    modelClassDB.setPincode(array.getString("AREA Pin CODE"));
                    modelClassDB.setContact(array.getString("Contact Details"));
                    modelClassDB.setSystemsOfMedicine(array.getString("Systems of Medicine"));
                    modelClassDB.setServices(array.getString("Services"));
                    modelClassDB.setWebsite(array.getString("Website link"));
                    modelClassDB.setTimestamp(array.getString("timestamp"));
                    modelClassDB.setEmail(array.getString("Email address"));
                    arrayList.add(modelClassDB);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("Size ::::", "" + arrayList.size());
            hospitalDataBase.open();
            if (arrayList.size() > 0) {
                hospitalDataBase.insertIntoDb(arrayList);
            }

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
