package com.jetruby.nfc.example.cryptography;

import java.math.BigInteger;
import java.security.*;
import java.util.Random;

public class DiffieHellman {

    /*private KeyPairGenerator keyPairGenerator;
    private static KeyPair selfPair;*/
    private BigInteger privateKey;
    private BigInteger publicKey;

    public DiffieHellman() {
        Random random = new Random();
        privateKey = new BigInteger(256, random);
        publicKey = new BigInteger(256, random);

        /*KeyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(256);
        selfPair = keyPairGenerator.generateKeyPair();*/
    }

    public BigInteger generatePartialKey(BigInteger receivedPublicKey) {
        BigInteger partialKey = publicKey.modPow(privateKey, receivedPublicKey);
        return partialKey;
    }

    public BigInteger generateFullKey(BigInteger receivedPublicKey, BigInteger partialKey) {
        BigInteger fullKey = partialKey.modPow(privateKey, receivedPublicKey);
        return fullKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }
}
