package com.project.cryptoapp.controller;

import com.project.cryptoapp.crypto.*;
import com.project.cryptoapp.model.EncryptionResult;
import com.project.cryptoapp.util.FileUtils;
import com.project.cryptoapp.util.KeyStorage;

public class EncryptionController {

    // -------------------- ENCRYPT --------------------
    public EncryptionResult encrypt(String filePath, String algorithmName) {
        try {
            long start = System.currentTimeMillis();

            byte[] inputBytes = FileUtils.readFile(filePath);

            String outputPath;
            byte[] encrypted;

            switch (algorithmName) {
                case "AES-128":
                case "AES-192":
                case "AES-256": {
                    int keySize = getKeySize(algorithmName);
                    AESService aes = new AESService(keySize);
                    encrypted = aes.encrypt(inputBytes);

                    outputPath = filePath + "." + algorithmName + ".enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, aes.getKeyBytes(), aes.getIvBytes());
                    break;
                }

                case "DES": {
                    DESService des = new DESService();
                    encrypted = des.encrypt(inputBytes);

                    outputPath = filePath + ".DES.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, des.getKeyBytes(), des.getIvBytes());
                    break;
                }

                case "3DES": {
                    TripleDESService tdes = new TripleDESService();
                    encrypted = tdes.encrypt(inputBytes);

                    outputPath = filePath + ".3DES.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, tdes.getKeyBytes(), tdes.getIvBytes());
                    break;
                }

                case "Blowfish": {
                    BlowfishService bf = new BlowfishService();
                    encrypted = bf.encrypt(inputBytes);

                    outputPath = filePath + ".Blowfish.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, bf.getKeyBytes(), bf.getIvBytes());
                    break;
                }

                case "IDEA": {
                    IDEAService idea = new IDEAService();
                    encrypted = idea.encrypt(inputBytes);

                    outputPath = filePath + ".IDEA.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, idea.getKeyBytes(), idea.getIvBytes());
                    break;
                }

                case "RC5": {
                    RC5Service rc5 = new RC5Service();
                    encrypted = rc5.encrypt(inputBytes);

                    outputPath = filePath + ".RC5.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, rc5.getKeyBytes(), rc5.getIvBytes());
                    break;
                }

                case "BEAR": {
                    BearService bear = new BearService();
                    encrypted = bear.encrypt(inputBytes);

                    outputPath = filePath + ".BEAR.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, bear.getKeyBytes(), bear.getIvBytes());
                    break;
                }

                case "LION": {
                    LionService lion = new LionService();
                    encrypted = lion.encrypt(inputBytes);

                    outputPath = filePath + ".LION.enc";
                    FileUtils.writeFile(outputPath, encrypted);
                    KeyStorage.save(outputPath, lion.getKeyBytes(), lion.getIvBytes());
                    break;
                }

                default:
                    return new EncryptionResult(false, 0,
                            "Algorithm not implemented: " + algorithmName, null);
            }

            long end = System.currentTimeMillis();

            return new EncryptionResult(
                    true,
                    end - start,
                    "Encryption successful using " + algorithmName,
                    outputPath
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new EncryptionResult(false, 0, "Error: " + e.getMessage(), null);
        }
    }

    // -------------------- DECRYPT --------------------
    public EncryptionResult decrypt(String filePath, String algorithmName) {
        try {
            long start = System.currentTimeMillis();

            byte[] encryptedBytes = FileUtils.readFile(filePath);

            String[] stored = KeyStorage.load(filePath);
            byte[] decrypted;
            String outputPath;

            switch (algorithmName) {
                case "AES-128":
                case "AES-192":
                case "AES-256": {
                    int keySize = getKeySize(algorithmName);
                    AESService aes = new AESService(keySize, stored[0], stored[1]);
                    decrypted = aes.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "DES": {
                    DESService des = new DESService(stored[0], stored[1]);
                    decrypted = des.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "3DES": {
                    TripleDESService tdes = new TripleDESService(stored[0], stored[1]);
                    decrypted = tdes.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "Blowfish": {
                    BlowfishService bf = new BlowfishService(stored[0], stored[1]);
                    decrypted = bf.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "IDEA": {
                    IDEAService idea = new IDEAService(stored[0], stored[1]);
                    decrypted = idea.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "RC5": {
                    RC5Service rc5 = new RC5Service(stored[0], stored[1]);
                    decrypted = rc5.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "BEAR": {
                    BearService bear = new BearService(stored[0], stored[1]);
                    decrypted = bear.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                case "LION": {
                    LionService lion = new LionService(stored[0], stored[1]);
                    decrypted = lion.decrypt(encryptedBytes);

                    outputPath = filePath.replace(".enc", ".dec");
                    FileUtils.writeFile(outputPath, decrypted);
                    break;
                }

                default:
                    return new EncryptionResult(false, 0,
                            "Algorithm not implemented: " + algorithmName, null);
            }

            long end = System.currentTimeMillis();

            return new EncryptionResult(
                    true,
                    end - start,
                    "Decryption successful using " + algorithmName,
                    outputPath
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new EncryptionResult(false, 0, "Error: " + e.getMessage(), null);
        }
    }

    // -------------------- HELPER --------------------

    private int getKeySize(String algorithmName) {
        return switch (algorithmName) {
            case "AES-128" -> 128;
            case "AES-192" -> 192;
            case "AES-256" -> 256;
            default -> throw new IllegalArgumentException("Unsupported AES algo: " + algorithmName);
        };
    }
}

