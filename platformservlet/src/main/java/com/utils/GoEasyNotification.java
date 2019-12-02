package com.utils;

import io.goeasy.GoEasy;

public class GoEasyNotification {

    public static void notify(String chancel, String title, String content) {
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-d13831fe7eb94368971b59b5795311a2");
        goEasy.publish(chancel, "{\"title\":\"" + title + "\",\"content\":\"" + content + "\"}");
    }

}
