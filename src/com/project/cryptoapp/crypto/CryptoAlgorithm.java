package com.project.cryptoapp.crypto;

public interface CryptoAlgorithm {
    byte[] encrypt(byte[] input) throws Exception;
    byte[] decrypt(byte[] input) throws Exception;
    String getName();
}

