package com.example.root.hospitalsnearyou;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by root on 24/9/15.
 */
public class JSONParser {

    static InputStream inputStream=null;
    static JSONObject jsonObject=null;
    static  String json="";
    public  JSONParser(){

    }

    public JSONObject makeHttpRequest(String url)
    {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet= new HttpGet(url);
        try {
            HttpResponse httpResponse=httpClient.execute(httpGet);
            HttpEntity httpEntity=httpResponse.getEntity();
            inputStream=httpEntity.getContent();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
            StringBuilder stringBuilder=new StringBuilder();
            String line=null;
            try {
                while ((line=bufferedReader.readLine()) !=null)
                {
                    stringBuilder.append(line+"\n");
                }
                inputStream.close();
                json=stringBuilder.toString();
                try {
                    jsonObject=new JSONObject(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
}
