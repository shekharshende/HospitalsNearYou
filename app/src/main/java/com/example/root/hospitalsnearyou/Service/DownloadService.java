package com.example.root.hospitalsnearyou.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.root.hospitalsnearyou.DB.HospitalDataBase;
import com.example.root.hospitalsnearyou.ModelClass.ModelClassDB;
import com.example.root.hospitalsnearyou.Parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DownloadService extends Service {
    JSONParser jsonParser=new JSONParser();
    JSONObject jsonObject=null;
    HospitalDataBase hospitalDataBase;
    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hospitalDataBase=new HospitalDataBase(getApplicationContext());
//        hospitalDataBase.readFromDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
              jsonObject=  jsonParser.makeHttpRequest("https://data.gov.in/api/datastore/resource.json?resource_id=b4d77a09-9cdc-4a5b-b900-8fddb78f3cbe&api-key=abcac7ea2c8e7c924ea3477c3c8741aa");
                addToDatabase( jsonObject);

            }
        }).start();
        return START_STICKY;
    }

    private void addToDatabase(JSONObject jsonObject1) {
        ArrayList<ModelClassDB> arrayList=new ArrayList<>();
        if (jsonObject1 != null) {
            try {
                JSONArray jsonArray = this.jsonObject.getJSONArray("records");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject array = jsonArray.getJSONObject(i);
                    ModelClassDB modelClassDB=new ModelClassDB();
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
            hospitalDataBase.open();
            hospitalDataBase.insertIntoDb(arrayList);

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
