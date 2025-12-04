package com.project.cryptoapp.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class LionService implements CryptoAlgorithm {

    private SecretKey key;
    private IvParameterSpec iv;

    // New encryption – Blowfish-based "LION"
    public LionService() throws Exception {
        generateKeyAndIv();
    }

    // Decryption
    public LionService(String storedKey, String storedIv) {
        this.key = new SecretKeySpec(Base64.getDecoder().decode(storedKey), "Blowfish");
        this.iv = new IvParameterSpec(Base64.getDecoder().decode(storedIv));
    }

    private void generateKeyAndIv() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("Blowfish");
        keyGen.init(128); // 128-bit Blowfish key
        this.key = keyGen.generateKey();

        byte[] ivBytes = new byte[8]; // Blowfish block size
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        this.iv = new IvParameterSpec(ivBytes);
    }

    public byte[] getKeyBytes() {
        return key.getEncoded();
    }

    public byte[] getIvBytes() {
        return iv.getIV();
    }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encrypted);
    }

    @Override
    public String getName() {
        return "LION (Blowfish-based)";
    }
}

