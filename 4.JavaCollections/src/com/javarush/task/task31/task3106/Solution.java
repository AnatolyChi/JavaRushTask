package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Разархивируем файл
*/

public class Solution {
    public static void main(String[] args) throws FileNotFoundException {
        String resultFileName = args[0];
        List<String> listFiles = new ArrayList<>();
        List<InputStream> listStream = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            if (args[i] != null) {
                listFiles.add(args[i]);
            }
        }

        Collections.sort(listFiles);
        for (String listFile : listFiles) {
            listStream.add(new FileInputStream(listFile));
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new SequenceInputStream(Collections.enumeration(listStream)));
             FileOutputStream fileOutputStream = new FileOutputStream(resultFileName)) {

            while (true) {
                ZipEntry entry = zipInputStream.getNextEntry();
                if (entry == null) break;

                final int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize * bufferSize];

                for (int readBytes; (readBytes = zipInputStream.read(buffer, 0, bufferSize)) > -1; ) {
                    fileOutputStream.write(buffer, 0, readBytes);
                }
                fileOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
