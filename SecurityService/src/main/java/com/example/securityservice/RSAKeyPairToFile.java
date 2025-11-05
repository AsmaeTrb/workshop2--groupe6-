package com.example.securityservice;

import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAKeyPairToFile {

    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            String publicKeyPEM =
                    "-----BEGIN PUBLIC KEY-----\n" +
                            Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(publicKey.getEncoded()) +
                            "\n-----END PUBLIC KEY-----\n";

            String privateKeyPEM =
                    "-----BEGIN PRIVATE KEY-----\n" +
                            Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(privateKey.getEncoded()) +
                            "\n-----END PRIVATE KEY-----\n";

            writeToFile("src/main/resources/keys/publicKey.pem", publicKeyPEM);
            writeToFile("src/main/resources/keys/privateKey.pem", privateKeyPEM);

            System.out.println("✅ Clés générées dans src/main/resources/keys/");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
