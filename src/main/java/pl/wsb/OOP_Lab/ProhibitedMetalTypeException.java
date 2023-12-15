package pl.wsb.OOP_Lab;

public class ProhibitedMetalTypeException extends RuntimeException {
    public ProhibitedMetalTypeException(SupportedMetalType metalType, String clientId) {
        super("This is prohibited metal type: "  + metalType + " for Client ID: " + clientId);
    }
}
