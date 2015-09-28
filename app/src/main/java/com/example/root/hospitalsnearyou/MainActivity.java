package com.example.root.hospitalsnearyou;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {
    JSONParser jsonParser = new JSONParser();
    String sb;
    JSONObject jsonObject = null;
    TextView txtHospitals;
    EditText edtCityName;
    Button btnSubmit;
    String cityName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtHospitals = (TextView) findViewById(R.id.btnGet);
        btnSubmit = (Button) findViewById(R.id.button);
        edtCityName = (EditText) findViewById(R.id.edittext);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityName = edtCityName.getText().toString();
                new retrieveData().execute();
            }
        });
    }

    private class retrieveData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONParser jParser = new JSONParser();
            jsonObject = jsonParser.makeHttpRequest("https://data.gov.in/api/datastore/resource.json?resource_id=b4d77a09-9cdc-4a5b-b900-8fddb78f3cbe&api-key=abcac7ea2c8e7c924ea3477c3c8741aa&filters[city]="+ cityName);
            try {

                sb = jsonObject.getString("records");
                String id = jsonObject.getString("id");
                Log.e("id",id);
                Log.e("cityName", ""+sb);
                Toast.makeText(MainActivity.this,"cokj",Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return sb;
        }

        @Override
        protected void onPostExecute(String sb) {
            txtHospitals.setText(sb);

        }
    }
}
