package com.example.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;


public class LoginActivity extends AppCompatActivity {
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

        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {



                if (!Name.getText().toString().trim().equals("") && !Password.getText().toString().trim().equals("")) {
                    String userNameString = Name.getText().toString().trim();
                    //Send encrypted password to server
                    String encryPW = Password.getText().toString();



                    new AsyncTask<String, Void, String>() {

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
                    }.execute(new String[] {userNameString, encryPW});
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