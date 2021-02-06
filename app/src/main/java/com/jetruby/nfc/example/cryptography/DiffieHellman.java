package com.jetruby.nfc.example.cryptography;

import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {

    /*private KeyPairGenerator keyPairGenerator;
    private static KeyPair selfPair;*/

    private static BigInteger privateKey, publicKey, partialKey, fullKey;
    private static BigInteger receivedPublicKey, receivedPartialKey;

    public DiffieHellman() {
        Random random = new Random();
        privateKey = new BigInteger(256, random);
        publicKey = new BigInteger(256, random);

        /*KeyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(256);
        selfPair = keyPairGenerator.generateKeyPair();*/
    }

    public BigInteger generatePartialKey() {
        partialKey = publicKey.modPow(privateKey, receivedPublicKey);
        return partialKey;
    }

    public void generateFullKey() {
        fullKey = receivedPartialKey.modPow(privateKey, receivedPublicKey);
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getFullKey() {
        return fullKey;
    }

    public void setReceivedPublicKey(BigInteger receivedPublicKey) {
        if (!receivedPublicKey.equals(null) && !receivedPublicKey.equals("")) {
            this.receivedPublicKey = receivedPublicKey;
        }
    }

    public void setReceivedPartialKey(BigInteger receivedPartialKey) {
        if (!receivedPartialKey.equals(null) && !receivedPartialKey.equals("")) {
            this.receivedPartialKey = receivedPartialKey;
        }
    }
}
