package pl.wsb.OOP_Lab;

public class FullWarehouseException extends Exception {
    public FullWarehouseException(String clientId) {
        super("Not enough space in warehouse for client: " + clientId);
    }
}
