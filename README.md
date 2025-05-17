# Secure_File_Transfer_Prtcl
Faza e trete ne lenden Siguria e te Dhenave per grupin 15 (Secure File Transfer Protocol using Hybrid Cryptography
Ky projekt në Java ofron funksionalitete bazë për kriptografi duke përdorur bibliotekat standarde si `javax.crypto` dhe `java.security`. Përfshin module për enkriptim AES, enkriptim RSA dhe funksione hash-uese.

---

## 📁 Struktura e Projektit


---

## 🔐 Karakteristikat

### AES (Advanced Encryption Standard)
- Gjenerim i çelësit AES (256-bit)
- Gjenerim i IV (Initialization Vector)
- Enkriptim dhe dekriptim me `AES/CBC/PKCS5Padding`

### RSA (Rivest-Shamir-Adleman)
- Gjenerim i palës së çelësave RSA (2048-bit)
- Enkriptim dhe dekriptim me `OAEPWithSHA-256AndMGF1Padding`
- Konvertim midis byte array dhe çelësave publik RSA

### HashUtils
- Gjenerim i hash-it SHA-256 për:
    - Stringje
    - Skedarë
- Verifikim i hash-it të skedarëve
- Output në format hexadecimal

---

## ✅ Kërkesat

- Java 8 ose më i ri
- Nuk ka nevojë për biblioteka të jashtme

---

## 🛠 Shembuj Përdorimi

### Shembull AES

```java
SecretKey key = AES.generateAESKey();
IvParameterSpec iv = AES.generateIV();
byte[] encrypted = AES.encrypt("Përshëndetje".getBytes(), key, iv);
byte[] decrypted = AES.decrypt(encrypted, key, iv);


## Shembull RSA 
KeyPair keyPair = RSA.generateKeyPair();
byte[] encrypted = RSA.encrypt(keyPair.getPublic(), "Sekret".getBytes());
byte[] decrypted = RSA.decrypt(keyPair.getPrivate(), encrypted);

## Shembull Hash
String hash = HashUtils.generateStringHash("disa të dhëna");

