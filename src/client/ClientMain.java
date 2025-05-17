package client;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Client klienti = new Client();
            klienti.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
