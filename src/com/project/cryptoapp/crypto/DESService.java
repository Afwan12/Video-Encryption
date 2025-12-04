package com.project.cryptoapp.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DESService implements CryptoAlgorithm {

    private SecretKey key;
    private IvParameterSpec iv;

    // For new encryption (fresh key + IV)
    public DESService() throws Exception {
        generateKeyAndIv();
    }

    // For decryption (load existing key + IV from stored Base64)
    public DESService(String storedKey, String storedIv) {
        this.key = new SecretKeySpec(Base64.getDecoder().decode(storedKey), "DES");
        this.iv = new IvParameterSpec(Base64.getDecoder().decode(storedIv));
    }

    private void generateKeyAndIv() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56); // DES key size is 56 bits
        this.key = keyGen.generateKey();

        byte[] ivBytes = new byte[8]; // DES block size = 8 bytes
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
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encrypted);
    }

    @Override
    public String getName() {
        return "DES";
    }
}

