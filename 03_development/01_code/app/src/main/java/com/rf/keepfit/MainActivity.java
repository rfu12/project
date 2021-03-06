package com.rf.keepfit;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.util.ApiRequestCall;
import com.rf.util.EncryptionUtil;
import com.rf.util.StringUtil;
import com.rf.util.UrlConnectionUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {
    private final String SERVICE = "url.keepfit.service";
    private final String LOGINAPI = "api.keepfit.login";
    private EditText Name;
    private  EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private int counter = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name=(EditText) findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etPassword);
        Login=(Button)findViewById(R.id.btnLogin);
        Register=(Button)findViewById(R.id.btnRegister);
        Info=(TextView)findViewById(R.id.tryAmount);


        Info.setText("No of attempts left : 10");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 validate(Name.getText().toString(), Password.getText().toString());
            }
        });
    }
    private void validate(String userName, String userPassword){
        if(StringUtil.isEmptyStr(userName)){
            Toast.makeText(getApplicationContext(), "please write down your login name.", Toast.LENGTH_LONG).show();
            return;
        }
        if(StringUtil.isEmptyStr(userPassword)){
            Toast.makeText(getApplicationContext(), "please write down your login name.", Toast.LENGTH_LONG).show();
            return;
        }
        List<String> list = new ArrayList<>();
        list.add(userName);
        list.add(EncryptionUtil.string2MD5(userPassword));
        ApiRequestCall call = new ApiRequestCall(SERVICE, LOGINAPI, list, null, "utf-8");
        FutureTask<Map<String, String>> futureTask = new FutureTask(call);
        new Thread(futureTask).start();
        try {
            Map<String, String> retMap = futureTask.get();
            String retJson = retMap.get("retMsg");
            if("0".equals(retMap.get("retCode"))) //Toast.makeText(getApplicationContext(), "An error occurred when asking for service.", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), retJson, Toast.LENGTH_LONG).show();
            else {
                if(StringUtil.isEmptyStr(retJson)) Toast.makeText(getApplicationContext(), "UserName or password is not correct.", Toast.LENGTH_LONG).show();
                JSONArray jsonArray = new JSONArray(retJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    JSONObject userJson = obj.getJSONObject("userId");
                    String address = userJson.getString("address");
                    System.out.println(address);
                    Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), "please write down your login name.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "please write down your login name.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        /*new Thread(){
            public void run(){
                List<String> list = new ArrayList<>();
                list.add("1");
                Looper.prepare();
                try {
                    String responseJson = UrlConnectionUtils.sendGetByHttpUrlConnection(SERVICE, LOGINAPI, list, null, "utf-8");
                    if(StringUtil.isEmptyStr(responseJson) || "ERROR!".equals(responseJson)){
                        Toast.makeText(getApplicationContext(), "An error occurred when asking for service.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    JSONArray jsonArray = new JSONArray(responseJson);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String name = obj.getString("name");
                        System.out.println(name);
                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    Looper.loop();
                }
            }
        }.start();*/
        if((userName.equals("Admin")) && (userPassword.equals("1234"))){
            //Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            //startActivity(intent);
        }else{
            counter--;

            Info.setText("No of attempts left: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        }
    }
}
