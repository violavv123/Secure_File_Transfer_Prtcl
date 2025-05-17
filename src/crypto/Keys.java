package crypto;

import java.io.File;
import java.nio.file.Files;
import java.security.*;
import java.util.Base64;

public class Keys {
    public static KeyPair getKeyPair(String publicPath, String privatePath) throws Exception {
        File publicKey = new File(publicPath);
        File privateKey = new File(privatePath);

        if (publicKey.exists() && privateKey.exists()) {
            byte[] publicKBytes = Base64.getDecoder().decode(Files.readString(publicKey.toPath()));
            byte[] privateKBytes = Base64.getDecoder().decode(Files.readString(privateKey.toPath()));
            return new KeyPair(RSA.getPublicKeyFromBytes(publicKBytes), RSA.getPrivateKeyFromBytes(privateKBytes));
        }

        KeyPair kp = RSA.generateKeyPair();
        Files.writeString(publicKey.toPath(), Base64.getEncoder().encodeToString(kp.getPublic().getEncoded()));
        Files.writeString(privateKey.toPath(), Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()));
        return kp;
    }
}
