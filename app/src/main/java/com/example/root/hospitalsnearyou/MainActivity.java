package com.example.root.hospitalsnearyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        Intent intent=new Intent(MainActivity.this,DownloadService.class);
        startService(intent);
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cityName = edtCityName.getText().toString();
//
//            }
//        });
    }

   /* private class retrieveData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            JSONParser jParser = new JSONParser();
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
    }*/
}
