package com.example.MessageHandler;

import android.content.Context;
import android.os.Message;
import android.widget.Toast;

public class LoginHandler extends BaseHandler {

    public LoginHandler(Context context) {
        super(context);
    }

    public static final int NODATA = 2;
    public static final int UNKNOWERROR = 500;

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SUCCESS:
                showText("Login Success.");
                break;
            case ERROR:
                showText("App System Error.");
                break;
            case NODATA:
                showText("User Name/Passoword is not valid.");
                break;
            case UNKNOWERROR:
                showText("Service System Error.");
                break;
        }
    }
}
