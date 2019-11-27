package com.type;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

public enum PictureType {

    PROJECT("img/project"),
    ACTIVITY("img/activity"),
    AVATAR("img/user/avatar"),
    USER("img/user/background");

    private static String baseFolder;

    static {
        try {
            baseFolder = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("application.image.save.folder");
            System.out.print(baseFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String path;

    PictureType(String path) {
        this.path = path;
    }

    public File getSaveFolder() {
        File file;
        if (baseFolder.isEmpty()) {
            file = new File(baseFolder, path);
        } else {
            file = new File(path);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


}
