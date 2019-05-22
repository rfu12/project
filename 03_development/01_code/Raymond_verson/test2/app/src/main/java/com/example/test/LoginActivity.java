package com.example.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.MessageHandler.LoginHandler;
import com.example.entity.Credential;
import com.example.util.ApiRequestCall;
import com.example.util.EncryptionUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Register;
    private int counter = 10;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String loginKey = "api.keepfit.login";

        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText) findViewById(R.id.etPassword);
        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegister);
        Info = (TextView) findViewById(R.id.tryAmount);
        Info.setText("No of attempts left : 10");

        Name.setText(getIntent().getStringExtra("userName"));

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!"".equals(Name.getText().toString().trim()) && !"".equals(Password.getText().toString().trim())) {
                    LoginHandler handler = new LoginHandler(getApplicationContext());
                    Message message = Message.obtain();
                    String userNameString = Name.getText().toString().trim();
                    //Send encrypted password to server
                    String encryPW = Password.getText().toString();
                    List<String> userpwd = new ArrayList<>();
                    userpwd.add(userNameString);
                    userpwd.add(EncryptionUtil.string2MD5(encryPW));
                    try {
                        ApiRequestCall apiRequestCall = new ApiRequestCall(loginKey, userpwd, null);
                        Map<String, String> returnMap = apiRequestCall.execute();
                        message.what = LoginHandler.ERROR;
                        message.obj = returnMap.get("detail");
                        if ("1".equals(returnMap.get("opcode"))) {
                            message.what = LoginHandler.SUCCESS;
                            Credential credential = gson.fromJson(returnMap.get("detail"), Credential.class);
                            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                            intent.putExtra("credential", credential);
                            startActivity(intent);
                            finish();
                        } else {
                            message.what = LoginHandler.NODATA;
                            counter--;
                            Info.setText("No of attempts left: " + String.valueOf(counter));

                            if (counter == 0) {
                                Login.setEnabled(false);
                            }
                        }
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        message.what = LoginHandler.UNKNOWERROR;
                        handler.sendMessage(message);
                        e.printStackTrace();
                    }
                    /*new AsyncTask<String, Void, String>() {

                        @Override
                        protected String doInBackground(String... params) {
                            //test create
                            //RestClient.createResident("{\"address\":\"5 Martin Street Brighton\",\"dob\":\"1961-06-05T00:00:00+10:00\",\"email\":\"jeremyclarkson@outlook.com\",\"firstname\":\"Jeremy\",\"mobile\":\"0458963584\",\"nameofenergyprovider\":\"Ocean Energy\",\"numofresident\":3,\"postcode\":\"3186\",\"resid\":8,\"surname\":\"Clarkson\"}");
                            return RestClient.getUserInfo(params[0], params[1]);
                        }
                        @Override
                        protected void onPostExecute(String userInfo){
                            if (userInfo.equals("")){
                                System.out.println("User Name/Passoword is not valid.");
                                counter--;
                                Info.setText("No of attempts left: " + String.valueOf(counter));

                                if (counter == 0) {
                                            Login.setEnabled(false);
                                        }
                            } else {
                                System.out.println("Welcome");

                                try {
                                    JSONArray logedUser = new JSONArray(userInfo);
                                    System.out.println(logedUser);
                                    System.out.println(userInfo);

                                    //showText("Welcome, " + userName.getText().toString());
                                    Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                    //JsonArray logedUser = new JsonParser().parse(userInfo).getAsJsonArray();
                                    //Resident resident = new Resident(logedUser);
                                    //intent.putExtra("user",resident);
                                    startActivity(intent);
                                    finish();
                                    //System.out.println(userInfo);
                                    //System.out.println(logedUser);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                finish();
                            }
                        }
                    }.execute(new String[] {userNameString, encryPW});*/
                } else {
                    System.out.println("User name/password can not be empty!");
                    counter--;
                    Info.setText("No of attempts left: " + String.valueOf(counter));

                    if (counter == 0) {
                        Login.setEnabled(false);
                    }
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}


//  counter--;
//
//                                        Info.setText("No of attempts left: " + String.valueOf(counter));
//
//                                        if (counter == 0) {
//                                            Login.setEnabled(false);
//                                        } else {
//                                            System.out.println(("123456"));