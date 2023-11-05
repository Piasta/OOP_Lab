package pl.wsb.OOP_Lab;

public class Main {
    public static void main(String[] args) {
        ClientDataCreator clientDataCreator = new ClientDataCreator();
        System.out.println(clientDataCreator.createNewClient("Piotr", "Piasta"));
    }
}