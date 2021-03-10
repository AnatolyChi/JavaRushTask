package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* 
Что внутри папки?
*/

public class Solution {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String directory = reader.readLine();
        reader.close();

        Path path = Paths.get(directory);
        if (!Files.isDirectory(path)) {
            System.out.printf("%s - не папка", path.toString());
        } else {
            AtomicInteger folderCounter = new AtomicInteger();
            AtomicInteger fileCounter = new AtomicInteger();
            AtomicLong sizeCounter = new AtomicLong();

            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(path)) folderCounter.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    fileCounter.incrementAndGet();
                    sizeCounter.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.printf("Всего файлов - %d\n", fileCounter.get());
            System.out.printf("Всего папок - %d\n", folderCounter.get());
            System.out.printf("Общий размер - %d\n", sizeCounter.get());
        }
    }
}
