package com.project.cryptoapp.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static byte[] readFile(String path) throws Exception {
        return Files.readAllBytes(Path.of(path));
    }

    public static void writeFile(String path, byte[] data) throws Exception {
        Files.write(Path.of(path), data);
    }
}

