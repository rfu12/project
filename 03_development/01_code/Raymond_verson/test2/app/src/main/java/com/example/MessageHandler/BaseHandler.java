package com.example.MessageHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BaseHandler extends Handler {
    public static final int SUCCESS = 1;
    public static final int ERROR = 0;
    protected Context context;

    public BaseHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SUCCESS:
                showText((String) msg.obj);
                break;
            case ERROR:
                showText((String) msg.obj);
                break;
            default:
                showText((String) msg.obj);
                break;
        }
    }

    public void showText(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
