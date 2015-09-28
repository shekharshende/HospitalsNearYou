package com.example.root.hospitalsnearyou;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.root.hospitalsnearyou.DB.HospitalDataBase;
import com.example.root.hospitalsnearyou.ModelClass.ModelClassDB;

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
//                    modelClassDB.setHospitalId(array.get(0).toString());
                    modelClassDB.setState(array.getString("State"));
                    modelClassDB.setCity(array.getString("City"));
//                    modelClassDB.setDistrict(array.get(3).toString());
//                    modelClassDB.setHospitalName(array.get(4).toString());
//                    modelClassDB.setAddress(array.get(5).toString());
//                    modelClassDB.setPincode(array.get(6).toString());
//                    modelClassDB.setContact(array.get(7).toString());
//                    modelClassDB.setHelpline(array.get(8).toString());
//                    modelClassDB.setFax(array.get(9).toString());
//                    modelClassDB.setCategory(array.get(10).toString());
//                    modelClassDB.setWebsite(array.get(11).toString());
//                    modelClassDB.setEmail(array.get(12).toString());
//                    modelClassDB.setBlood_component(array.get(13).toString());
//                    modelClassDB.setBlood_group(array.get(14).toString());
//                    modelClassDB.setService_time(array.get(15).toString());
//                    modelClassDB.setLatitude(array.get(16).toString());
//                    modelClassDB.setLongitude(array.get(17).toString());
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
