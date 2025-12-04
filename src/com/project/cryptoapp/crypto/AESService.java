package com.project.cryptoapp.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESService implements CryptoAlgorithm {

    private SecretKey key;
    private IvParameterSpec iv;
    private final int keySize;

    // For new encryption (fresh key)
    public AESService(int keySize) throws Exception {
        this.keySize = keySize;
        generateKey();
    }

    // For decryption (load existing key)
    public AESService(int keySize, String storedKey, String storedIv) {
        this.keySize = keySize;
        this.key = new SecretKeySpec(Base64.getDecoder().decode(storedKey), "AES");
        this.iv = new IvParameterSpec(Base64.getDecoder().decode(storedIv));
    }

    private void generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        this.key = keyGen.generateKey();

        byte[] ivBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        this.iv = new IvParameterSpec(ivBytes);
    }

    public byte[] getKeyBytes() { return key.getEncoded(); }
    public byte[] getIvBytes() { return iv.getIV(); }

    @Override
    public byte[] encrypt(byte[] input) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    @Override
    public byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encrypted);
    }

    @Override
    public String getName() {
        return "AES-" + keySize;
    }
}

