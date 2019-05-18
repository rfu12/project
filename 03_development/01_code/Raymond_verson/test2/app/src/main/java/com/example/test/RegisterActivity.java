package com.example.test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.MessageHandler.LoginHandler;
import com.example.entity.Credential;
import com.example.entity.Users;
import com.example.util.ApiRequestCall;
import com.example.util.StringUtil;
import com.example.util.UrlConnectionUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private LoginHandler handler = new LoginHandler(getApplicationContext());
    private int resId;
    private int year = 1960, month = 5, date = 5;
    private String datePicked;
    private String gender = "Male";
    private String pCode;
    private String loa;
    private Button bnSetDate;
    private Button btSignup;
    private Spinner spLevelOfActivity, spPostCode;
    private RadioGroup rgGender;

    private TextView tvUserName, tvPassword, tvCPassword,
            tvFName, tvLName, tvAddress, tvEmail, tvDOB, tvGender,
            tvHeight, tvWeight, tvLevelOfActivity, tvStepsPerMile, tvPostCode;

    private EditText userName, password, confirmPassword,
            edFName, edLName, edAddress, edEmail, edHeight,
            edWeight, edStepsPerMile;

    private boolean validUserName = false, validPassowrd = false,
            validCPassword = false, validFName = false, validLName = false,
            validAddress = false, validEmail = false, validDOB = false,
            validHeight = false, validWeight = false,  validPostCode = false,
            validLevelOfActivity = false, validStepsPerMile = false, validGender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spLevelOfActivity = (Spinner) findViewById(R.id.spLevelOfActivity);
        spPostCode = (Spinner) findViewById(R.id.spPostCode);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);

        btSignup = (Button) findViewById(R.id.btSignup);
        bnSetDate = (Button) findViewById(R.id.bnSetDate);
        btSignup.setFocusableInTouchMode(true);

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvPassword = (TextView) findViewById(R.id.tvPssword);
        tvCPassword = (TextView) findViewById(R.id.tvCPasswrod);
        tvFName = (TextView) findViewById(R.id.tvFName);
        tvLName = (TextView) findViewById(R.id.tvLName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvDOB = (TextView) findViewById(R.id.tvDOB);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvLevelOfActivity = (TextView) findViewById(R.id.tvLevelOfActivity);
        tvStepsPerMile = (TextView) findViewById(R.id.tvStepsPerMile);
        tvPostCode = (TextView) findViewById(R.id.tvPostCode);

        userName = (EditText) findViewById(R.id.edUserName);
        password = (EditText) findViewById(R.id.edPassword);
        confirmPassword = (EditText) findViewById(R.id.edCPassword);
        edFName = (EditText) findViewById(R.id.edFName);
        edLName = (EditText) findViewById(R.id.edLName);
        edAddress = (EditText) findViewById(R.id.edAddress);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edHeight = (EditText) findViewById(R.id.edHeight);
        edWeight = (EditText) findViewById(R.id.edWeight);
        edStepsPerMile = (EditText) findViewById(R.id.edStepsPerMile);

        userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!StringUtil.isEmptyStr(userName.getText().toString())) {
                        new AsyncTask<String, Void, String>() {

                            @Override
                            protected String doInBackground(String... params) {
                                List<String> paramsList = new ArrayList<>();
                                paramsList.add(params[0]);
                                String returnStr = null;
                                try {
                                    returnStr = UrlConnectionUtils.sendGetByHttpUrlConnection(ApiRequestCall.concatUrl("url.keepfit.service", "api.keepfit.findUser", paramsList, null), "utf-8");
                                } catch (Exception e) {
                                    returnStr = "ERROR!:" + e.getLocalizedMessage();
                                }
                                return returnStr;
                            }

                            @Override
                            protected void onPostExecute(String exist) {
                                if(exist.startsWith("ERROR!:")) {
                                    Message message = Message.obtain();
                                    message.what = LoginHandler.UNKNOWERROR;
                                    message.obj = exist;
                                    handler.sendMessage(message);
                                } else if(StringUtil.isEmptyStr(exist)){
                                    tvUserName.setText("User Name");
                                    tvUserName.setTextColor(0xFF303F9F);
                                    validUserName = true;
                                } else {
                                    Users users = gson.fromJson(exist, Users.class);
                                    if (users != null) {
                                        tvUserName.setText("User Name-User Name Exist");
                                        tvUserName.setTextColor(0xFFFF4081);
                                        validUserName = false;
                                    } else {
                                        tvUserName.setText("User Name");
                                        tvUserName.setTextColor(0xFF303F9F);
                                        validUserName = true;
                                    }
                                }
                            }
                        }.execute(userName.getText().toString());

                    } else {
                        tvUserName.setText("User Name-Required");
                        tvUserName.setTextColor(0xFFFF4081);
                        validUserName = false;
                    }
                }
            }
        });

        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (!StringUtil.isEmptyStr(confirmPassword.getText().toString())) {
                        if (confirmPassword.getText().toString().equals(password.getText().toString())) {
                            tvCPassword.setText("Confirm Password");
                            tvCPassword.setTextColor(0xFF303F9F);
                            validCPassword = true;
                        } else {
                            tvCPassword.setText("Confirm Password-Not Match");
                            tvCPassword.setTextColor(0xFFFF4081);
                            validCPassword = false;
                        }
                    } else {
                        tvCPassword.setText("Confirm Password-Required");
                        tvCPassword.setTextColor(0xFFFF4081);
                        validCPassword = false;
                    }
                }
            }
        });

        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!StringUtil.isEmptyStr(edEmail.getText().toString())) {
                        String email = edEmail.getText().toString();
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(email);
                        if (!m.find()) {
                            tvEmail.setText("Email format is incorrect");
                            tvEmail.setTextColor(0xFFFF4081);
                            validEmail = false;
                        } else {
                            tvEmail.setText("Email Address");
                            tvEmail.setTextColor(0xFF303F9F);
                            validEmail = true;
                        }
                    } else {
                        tvEmail.setText("Email Address-Required");
                        tvEmail.setTextColor(0xFFFF4081);
                        validEmail = false;
                    }
                }
            }
        });

        edHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^[0-2]+[0-9]{2}$";

            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!StringUtil.isEmptyStr(edHeight.getText().toString())) {
                        String height = edHeight.getText().toString();
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(height);
                        if (!m.find()) {
                            tvHeight.setText("Height format is incorrect");
                            tvHeight.setTextColor(0xFFFF4081);
                            validHeight = false;
                        } else {
                            tvHeight.setText("Height");
                            tvHeight.setTextColor(0xFF303F9F);
                            validHeight = true;
                        }
                    } else {
                        tvHeight.setText("Height-Required");
                        tvHeight.setTextColor(0xFFFF4081);
                        validHeight = false;
                    }
                }
            }
        });

        edWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^[0-9]{3}$";

            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!StringUtil.isEmptyStr(edWeight.getText().toString())) {
                        String weight = edWeight.getText().toString();
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(weight);
                        if (!m.find()) {
                            tvWeight.setText("Weight format is incorrect");
                            tvWeight.setTextColor(0xFFFF4081);
                            validWeight = false;
                        } else {
                            tvWeight.setText("Weight");
                            tvWeight.setTextColor(0xFF303F9F);
                            validWeight = true;
                        }
                    } else {
                        tvWeight.setText("Weight-Required");
                        tvWeight.setTextColor(0xFFFF4081);
                        validWeight = false;
                    }
                }
            }
        });

        edStepsPerMile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final String pattern = "^[0-9]{4}$";

            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!StringUtil.isEmptyStr(edStepsPerMile.getText().toString())) {
                        String spm = edStepsPerMile.getText().toString();
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(spm);
                        if (!m.find()) {
                            tvStepsPerMile.setText("Steps Of Mile format is incorrect");
                            tvStepsPerMile.setTextColor(0xFFFF4081);
                            validStepsPerMile = false;
                        } else {
                            tvStepsPerMile.setText("Steps Of Mile");
                            tvStepsPerMile.setTextColor(0xFF303F9F);
                            validStepsPerMile = true;
                        }
                    } else {
                        tvStepsPerMile.setText("Steps Of Mile-Required");
                        tvStepsPerMile.setTextColor(0xFFFF4081);
                        validStepsPerMile = false;
                    }
                }
            }
        });

        spPostCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                pCode = spPostCode.getSelectedItem().toString();
                tvPostCode.setText("Post Code");
                tvPostCode.setTextColor(0xFF303F9F);
                validPostCode = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spLevelOfActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                loa = spLevelOfActivity.getSelectedItem().toString();
                tvLevelOfActivity.setText("Level Of Activity");
                tvLevelOfActivity.setTextColor(0xFF303F9F);
                validLevelOfActivity = true;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        hasFocus(password,tvPassword,"validPassowrd");
        hasFocus(edFName,tvFName,"validFName");
        hasFocus(edLName,tvLName,"validLName");
        hasFocus(edAddress,tvAddress,"validStreet");



        bnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this, dateListener, year, month, date).show();
            }
        });

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Debug
                System.out.println(validUserName+ "," +validFName+ "," +validLName+ "," +validEmail+ "," +validCPassword
                        + "," + validAddress);
                int genderId = rgGender.getCheckedRadioButtonId();
                if (genderId != -1)
                {
                    RadioButton checked = findViewById(genderId);
                    gender = checked.getText().toString();
                    validGender = true;
                }
                if (validUserName && validCPassword && validFName && validLName && validAddress && validEmail
                        && validHeight && validWeight && validLevelOfActivity) {

                    if (validDOB && validPostCode && validStepsPerMile && validGender) {
                        new AsyncTask<Void, Void, String>(){

                            @Override
                            protected String doInBackground(Void... params) {
                                String retStr = null;
                                try {
                                    Users users = new Users(null,edLName.getText().toString(),edFName.getText().toString(),edEmail.getText().toString(), new Date(datePicked),Integer.parseInt(edHeight.getText().toString()),Integer.parseInt(edWeight.getText().toString()),gender,edAddress.getText().toString(),Integer.parseInt(pCode),Integer.parseInt(loa),Integer.parseInt(tvStepsPerMile.getText().toString()));
                                    Credential credential = new Credential(userName.getText().toString(),confirmPassword.getText().toString(),null,null);
                                    JsonObject paramJson = new JsonObject();
                                    JsonParser jsonParser = new JsonParser();
                                    paramJson.add("users", jsonParser.parse(gson.toJson(users)));
                                    paramJson.add("credential", jsonParser.parse(gson.toJson(credential)));
                                    System.out.println(paramJson.getAsString());
                                    retStr = UrlConnectionUtils.sendPostByHttpUrlConnect(ApiRequestCall.concatUrl("url.keepfit.service", "api.keepfit.register", null, null), paramJson.getAsString(), "utf-8");
                                } catch (Exception e) {
                                    retStr = "ERROR!:" + e.getLocalizedMessage();
                                }
                                return retStr;
                            }
                            @Override
                            protected void onPostExecute(String retStr){
                                Message message = Message.obtain();
                                if(retStr.startsWith("ERROR!:")) {
                                    message.what = LoginHandler.UNKNOWERROR;
                                    message.obj = retStr;
                                } else if(StringUtil.isEmptyStr(retStr)){
                                    message.what = LoginHandler.NODATA;
                                } else {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("userName", retStr);
                                    startActivity(intent);
                                    finish();
                                }
                                handler.sendMessage(message);
                            }
                        }.execute();
                    } else if (!validDOB) {
                        tvDOB.setText("Date of Birth-Incorrect Date");
                        tvDOB.setTextColor(0xFFFF4081);
                    } else if (!validPostCode){
                        tvPostCode.setText("Post Code-Required");
                        tvPostCode.setTextColor(0xFFFF4081);
                    } else if(!validLevelOfActivity) {
                        tvLevelOfActivity.setText("Level Of Activity-Require");
                        tvLevelOfActivity.setTextColor(0xFFFF4081);
                    } else if(!validGender) {
                        tvGender.setText("Gender");
                        tvGender.setTextColor(0xFFFF4081);
                    }

                }
            }
        });
    }

    public boolean hasFocus(final EditText ed, final TextView tv, final String valid) {
        boolean s = false;
        final String tvTextOriginal = tv.getText().toString();
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            boolean b1 = false;
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!StringUtil.isEmptyStr(ed.getText().toString())) {
                        tv.setText(tvTextOriginal);
                        tv.setTextColor(0xFF303F9F);
                        switch(valid){
                            case "validPassword":
                                validPassowrd = true; break;
                            case "validFName":
                                validFName = true; break;
                            case "validLName":
                                validLName = true; break;
                            case "validAddress":
                                validAddress = true; break;
                            case "validStepsPerMile":
                                validAddress = true; break;
                        }
                    } else {
                        tv.setText(tvTextOriginal + "-Required");
                        tv.setTextColor(0xFFFF4081);
                        switch(valid){
                            case "validPassword":
                                validPassowrd = false; break;
                            case "validFName":
                                validFName = false; break;
                            case "validLName":
                                validLName = false; break;
                            case "validAddress":
                                validAddress = false; break;
                            case "validStepsPerMile":
                                validAddress = false; break;
                        }
                    }
                }
            }
        });
        return s;
    }

    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int newYear, int newMonth, int newDate) {
            year = newYear;
            month = newMonth;
            date = newDate;
            String displayDate = date + "/" + Integer.toString(month + 1) + "/" + year;

            Date temp = new Date(displayDate);
            if (temp.after(new Date())) {
                tvDOB.setText("Date of Birth-Incorrect Date");
                tvDOB.setTextColor(0xFFFF4081);
                validDOB = false;
            } else {
                String strDay = "";
                String strMonth = "";
                if (date < 10)
                    strDay = "0" + date;
                else
                    strDay = date + "";
                if (month + 1 < 10)
                    strMonth = "0" + Integer.toString(month + 1);
                else
                    strMonth = month + "";

                datePicked = year + "-" + strMonth + "-" + strDay + "T00:00:00+10:00";
                bnSetDate.setText("Date of birth: " + displayDate);
                tvDOB.setText("Date of Birth");
                tvDOB.setTextColor(0xFF303F9F);
                validDOB = true;
            }
        }
    };

}
