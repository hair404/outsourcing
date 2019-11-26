package com.utils;

import org.apache.zookeeper.server.ByteBufferInputStream;

import java.io.*;

public class FileTools {

    public static byte[] getBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        return bytes;
    }
}
