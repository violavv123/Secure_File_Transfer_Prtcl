package server;

import crypto.AES;
import crypto.RSA;
import crypto.HashUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.util.HashMap;

public class server {

    private static final int PORT = 8080;
    private static final String PUBLIC_KEY_FILE = "public.key";
    

    public void start() {
        try {
            // Gjeneron qelsin RSA
            KeyPair keyPair = RSA.generateKeyPair();
            savePublicKey(keyPair.getPublic());

            // Fillon serveri
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Secure File Transfer Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected, receiving data...");
                handleClient(clientSocket, keyPair.getPrivate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePublicKey(PublicKey publicKey) throws IOException {
        byte[] keyBytes = RSA.getPublicKeyBytes(publicKey);
        String base64Key = java.util.Base64.getEncoder().encodeToString(keyBytes);

        try (FileWriter writer = new FileWriter(PUBLIC_KEY_FILE)) {
            writer.write(base64Key);
        }
    }

    private void handleClient(Socket socket, PrivateKey privateKey) {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            HashMap<String, byte[]> data = (HashMap<String, byte[]>) ois.readObject();

            byte[] encryptedAESKey = data.get("encryptedAESKey");
            byte[] encryptedData = data.get("encryptedData");
            byte[] ivBytes = data.get("iv");
            byte[] hashBytes = data.get("hash");

            // Dekripton AES key me RSA
            byte[] aesKeyBytes = RSA.decrypt(privateKey, encryptedAESKey);
            SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");

            // Dekripton të dhënat me AES
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            byte[] decryptedData = AES.decrypt(encryptedData, aesKey, iv);

            // Ruan fajllin
            File outputFile = new File("received_file.txt");
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(decryptedData);
            }

            // Verifikon hash-in
            String expectedHash = new String(hashBytes);
            boolean isVerified = HashUtils.verifyFileHash(outputFile, expectedHash);
            if (isVerified) {
                System.out.println("File integrity and authenticity verified.");
            } else {
                System.out.println("WARNING: File integrity check FAILED!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
