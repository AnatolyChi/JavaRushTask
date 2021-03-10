package com.javarush.task.task31.task3105;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        String newFileName = Paths.get(args[0]).getFileName().toString(); //полный путь к файлу
        String newPathInArchive = "new/" + newFileName; //файл с директорией который нужно добавить в архив
        ZipEntry entry;
        Map<String, ByteArrayOutputStream> filesInArchive = new HashMap<>();

        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(args[1])); //открываем поток для чтения из архива
        while ((entry = zipIn.getNextEntry()) != null) { //пока следующий файл в архиве не равен нул
            if (entry.getName().endsWith(newFileName)) { //если суффикс этого файла архива равен newFileName
                newPathInArchive = entry.getName(); //
            } else { //иначе открываем поток записи и добавляем значения в мап
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int length;
                byte[] buffer = new byte[1024];
                while ((length = zipIn.read(buffer)) > 0) {
                    baos.write(buffer, 0, length);
                }
                filesInArchive.put(entry.getName(), baos);
            }
        }
        zipIn.close();

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(args[1]));
        zipOut.putNextEntry(new ZipEntry(newPathInArchive));
        Files.copy(Paths.get(args[0]), zipOut);
        for (Map.Entry<String, ByteArrayOutputStream> pair : filesInArchive.entrySet()) {
            zipOut.putNextEntry(new ZipEntry(pair.getKey()));
            pair.getValue().writeTo(zipOut);
        }
        zipOut.close();
    }
}
