package com.project.cryptoapp.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class KeyStorage {

    public static void save(String filePath, byte[] key, byte[] iv) throws Exception {
        String data = Base64.getEncoder().encodeToString(key) + "\n" +
                      Base64.getEncoder().encodeToString(iv);
        Files.write(Path.of(filePath + ".key"), data.getBytes());
    }

    public static String[] load(String filePath) throws Exception {
        String content = Files.readString(Path.of(filePath + ".key"));
        return content.split("\n");
    }
}

