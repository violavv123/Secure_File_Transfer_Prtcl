package client;

import crypto.AES;
import crypto.RSA;
import crypto.HashUtils;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.nio.file.Files;
import java.util.HashMap;

public class Client {

    public void start() throws Exception {
        String filepath = "src/clientdata.txt";
        String publicKeyPath = "public.key";
        String serverHost = "localhost";
        int port = 8080;

        File file = new File(filepath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());

        SecretKey AESKey = AES.generateAESKey();
        IvParameterSpec iv = AES.generateIV();
        byte[] encryptedData = AES.encrypt(fileBytes, AESKey, iv);

        byte[] publicKeyBytes = Files.readAllBytes(new File(publicKeyPath).toPath());
        PublicKey serverPublicKey = RSA.getPublicKeyFromBytes(publicKeyBytes);
        byte[] encryptedAESKey = RSA.encrypt(serverPublicKey, AESKey.getEncoded());

        String fileHash = HashUtils.generateFileHash(file);
        byte[] fileHashBytes = fileHash.getBytes();

        HashMap<String, byte[]> hm = new HashMap<>();
        hm.put("encryptedAESKey", encryptedAESKey);
        hm.put("encryptedData", encryptedData);
        hm.put("iv", iv.getIV());
        hm.put("hash", fileHashBytes);

        try (Socket s = new Socket(serverHost, port); ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream())) {
            oos.writeObject(hm);
        }
        System.out.println("Data sent successfully using Hybrid Cryptography!");
    }
}
