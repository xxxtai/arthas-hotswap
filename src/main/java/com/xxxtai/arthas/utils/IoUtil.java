package com.xxxtai.arthas.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

import com.xxxtai.arthas.dialog.MyToolWindow;

/**
 * Copyright (c) 2020, 2021, xxxtai. All rights reserved.
 *
 * @author xxxtai
 */
public class IoUtil {

    public static String getResourceFile(ClassLoader classLoader, String filePath) throws Exception {
        try (InputStream in = classLoader.getResourceAsStream(filePath)) {
            if (in == null) {
                throw new IOException(filePath + " can not be found ");
            }
            InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        }
    }

    public static byte[] getTargetClass(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            MyToolWindow.consoleLog("The class file can`t found, filePath:" + filePath);
            return null;
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int)file.length());
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len;
            while ((len = bufferedInputStream.read(buffer, 0, bufSize)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    public static String printStackTrace(Throwable t) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        t.printStackTrace(printStream);
        try {
            printStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return new String(byteArrayOutputStream.toByteArray());
    }
}
