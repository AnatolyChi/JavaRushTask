package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/* 
Загрузчик файлов
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://javarush.ru/testdata/secretPasswords.txt",
                Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords)) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        URL url = new URL(urlString); //создали урл
        String pathName = Paths.get(url.getFile()).getFileName().toString(); //имя файла урл
        InputStream in = url.openStream(); //открыли поток

        Path path = Files.createTempFile("temp", ".tmp"); //создаем временный файл
        Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING); //копируем во временный файл
        in.close(); //закрываем поток

        Path newPathName = downloadDirectory.resolve(pathName);
        Files.move(path, newPathName, StandardCopyOption.REPLACE_EXISTING);

        return newPathName;
    }
}
