package pl.wsb.OOP_Lab;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String clientId) {
        super("Client not found: " + clientId);
    }
}
