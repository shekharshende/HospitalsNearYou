package com.example.root.hospitalsnearyou;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {
    JSONParser jsonParser=new JSONParser();
    public DownloadService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                jsonParser.makeHttpRequest("https://data.gov.in/api/datastore/resource.json");

            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
