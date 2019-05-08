package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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
        if((userName.equals("Admin")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
        }else{
            counter--;

            Info.setText("No of attempts left: " + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
            }
        }
    }
}
