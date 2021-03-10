package com.javarush.task.task31.task3101;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* 
Проход по дереву файлов
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        List<Path> paths = new ArrayList<>();
        File pathFile = new File(args[0]); //путь к директории
        File nameFile = new File(args[1]); //полное имя файла

        File newFileName = new File(nameFile.getParent() + "/allFilesContent.txt"); //корректное имя файла
        FileUtils.renameFile(nameFile, newFileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(newFileName, true)) {
            Files.walkFileTree(pathFile.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                    File file1 = file.toFile();
//                    if (file1.isFile()) {
//                        if (file1.length() <= 50) {
//                            paths.add(file);
//                        }
                    byte[] content = Files.readAllBytes(file);
                    if (!attrs.isDirectory() && content.length <= 50) {
                        paths.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            paths.sort(Comparator.comparing(Path::getFileName));

            for (Path path : paths) {
                FileInputStream inputStream = new FileInputStream(path.toFile());
                while (inputStream.available() > 0) {
                    fileOutputStream.write(inputStream.read());
                }
                fileOutputStream.write(System.lineSeparator().getBytes());
                fileOutputStream.flush();
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
