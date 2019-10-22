package com.utils;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import org.json.JSONObject;

public class Fcm {
    static String address = "127.0.0.1";
    static int port = 1080;

    public static void send(String token, String title, String body, String link) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject();
                    json.put("to", token);

                    JSONObject notification = new JSONObject();
                    notification.put("title", title);
                    notification.put("body", body);
                    notification.put("click_action", link);
                    json.put("notification", notification);

                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, port));

                    HttpURLConnection connection = (HttpURLConnection) new URL("https://fcm.googleapis.com/fcm/send").openConnection(proxy);
                    connection.addRequestProperty("Content-Type", "application/json");
                    connection.addRequestProperty("Authorization", "key=AIzaSyCPRBtRGbk50WUpXr2XnKiixw6P4OWh0e4");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    connection.setRequestMethod("POST");
                    connection.connect();
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    out.write(json.toString().getBytes());
                    out.flush();
                    out.close();
                    connection.getResponseCode();
                    connection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

}
