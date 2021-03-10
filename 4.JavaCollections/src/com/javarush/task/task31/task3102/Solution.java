package com.javarush.task.task31.task3102;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* 
Находим все файлы
*/

public class Solution extends SimpleFileVisitor<Path> {

    public static List<String> getFileTree(String root) throws IOException {
        List<String> list = new ArrayList<>();

        Stream<Path> stream = Files.walk(Paths.get(root));

        list = stream
                .filter(Files::isRegularFile)
                .map(path -> path.toAbsolutePath().toString())
                .collect(Collectors.toList());

//        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                if (!attrs.isDirectory()) {
//                    list.add(String.valueOf(file));
//                }
//                return FileVisitResult.CONTINUE;
//            }
//        });

        return list;
    }

    public static void main(String[] args) throws IOException {
        List<String> list;
        list = getFileTree("//Users//tolachikilev//Downloads//Aurorah");

        list.forEach(System.out::println);
    }
}
