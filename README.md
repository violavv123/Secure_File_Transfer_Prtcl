# Protokoll i Sigurt për Transferimin e Skedarëve (SFTP) - Implementim në Java

## Përmbajtja
1. [Përshkrimi](#përshkrimi)
2. [Veçoritë](#veçoritë)
3. [Kërkesat](#kërkesat)
4. [Instalimi](#instalimi)
5. [Përdorimi](#përdorimi)
6. [Rezultatet](#rezultatet)
7. [Implementimi i Sigurisë](#implementimi-i-sigurisë)
8. [Struktura e Projektit](#struktura-e-projektit)



## Përshkrimi <a name="përshkrimi"></a>
Ky është një sistem për transferimin e sigurt të skedarëve duke përdorur kriptografi hibride (AES + RSA) me komponentët e mëposhtëm:
- Aplikacion klient për transferim të sigurt të skedarëve
- Aplikacion server për pranim dhe verifikim të skedarëve
- Mjete kriptografike për enkriptim dhe verifikim

## Veçoritë <a name="veçoritë"></a>

### Aplikacioni Klient
- Enkriptim i skedarëve me AES-256
- Shkëmbim çelësash me RSA-2048
- Verifikim integriteti me SHA-256
- Ndërfaqe e thjeshtë përmes komandave

### Aplikacioni Server
- Dekriptim i sigurt i skedarëve
- Verifikim i integritetit
- Mbështetje për lidhje të shumta njëkohësisht
- Menaxhim i sigurt i çelësave

## Kërkesat <a name="kërkesat"></a>
- Java JDK 11 ose më e lartë
- Maven (për ndërtim)
- OpenSSL (për gjenerimin e çelësave)

## Instalimi <a name="instalimi"></a>

1. Gjeneroni çelësat RSA:
```bash
openssl genrsa -out private.key 2048
openssl rsa -in private.key -pubout -out public.key
```
## Përdorimi <a name="përdorimi"></a>
### Udhezimet e Ekzekutimit 

1. Nisni serverin (në terminal të veçantë):
```bash
java -cp target/sftp.jar server.Server
``` 
2.Nisni klientin (në terminal të veçantë):
```bash
java -cp target/sftp.jar client.Client
```

## rezultatet 
### Në Server:
[INFO] Serveri u nis në portin 8080
[INFO] Lidhje e re nga klienti: 127.0.0.1
[SUKSES] Skedari u pranua dhe u verifikua
 
### Në Klient:
[STATUS] U lidh me serverin
[ENKRIPTIM] Skedari u enkriptua me sukses
[TRANSFERIM] Skedari u dërgua (100%)
[SUKSES] Transferimi u krye

## Implementimi i Sigurisë

Përdorimi i teknologjive të sigurisë në këtë projekt:

1. **Enkriptimi i të Dhënave**
   - Teknologjia: AES-256
   - Implementimi: Përdor modalitetin CBC me IV

2. **Shkëmbimi i Çelësave** 
   - Teknologjia: RSA-2048
   - Implementimi: Përdor mbushjen PKCS1

3. **Verifikimi i Integritetit**
   - Teknologjia: SHA-256
   - Implementimi: Gjeneron hash për verifikim të skedarëve

## Struktura e projektit

Sistemi i organizimit të fajllave:

- Dosja kryesore `src` përmban:
  - Nënpërkatësin `main` me:
    - Kodet Java në dosjen `java`:
      - Implementimi i klientit në `client/Client.java`
      - Implementimi i serverit në `server/Server.java`
      - Bibliotekat kriptografike në `crypto/` (AES.java, RSA.java, HashUtils.java)
    - Burimet në `resources/` (çelësat publik dhe privat)
  - Testet në dosjen `test/`

