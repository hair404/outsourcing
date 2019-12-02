package com.dp.dto;

import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ServerNotification {

    private String title;
    private String content;
    private Date time;
    private String url;

    public ServerNotification(
            String title,
            String content,
            Date time,
            String url
    ) {
        this.title = title;
        this.content = content;
        this.time = time;
        this.url = url;
    }

    public String getTimeFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return sdf.format(this.time);
    }
}
