package com.example.test;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {
    String  ip="/118.139.43.181";
    private static final String BASE_URL = "http://192.168.1.30:8080/KeepfitApp/webresources/";  //118.139.43.181  192.168.1.30 49.127.130.56
    public static String getResult(String URL){
        URL url = null;
        HttpURLConnection conn = null;
        String str = "";
        //Making HTTP request
        try {
            url = new URL(URL);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                str += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return str;
    }
    public static String getUserInfo(String userName, String password){
        String userInfo = "";
        userName = userName.toLowerCase();
        String userUrl = BASE_URL + "application.credential/findByUsername/" + userName;

        String userStr = getResult(userUrl);
        //System.out.println(userStr);
        if (!userStr.equals("[]") ) {
            String pwUrl = BASE_URL + "application.credential/findByPasswordHash/" + password;
            String pw = getResult(pwUrl);
        //    System.out.println(pwUrl);
        //    System.out.println(pw);
            if (!pw.equals("[]")) {
                userInfo = userStr;

            } else
                userInfo = "";
        } else
            userInfo = "";
        System.out.println(userInfo);

        return userInfo;
    }

    public static String findAllCourses() {
        final String methodPath = "student.course/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(BASE_URL + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
}


