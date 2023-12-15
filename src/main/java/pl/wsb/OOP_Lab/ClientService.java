package pl.wsb.OOP_Lab;

import java.lang.reflect.Array;
import java.nio.channels.ScatteringByteChannel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientService implements ClientsInterface {
    private String firstName;
    private String lastName;
    private boolean isPremium;
    private String clientId;
    private List<Client> clientsList = new ArrayList<>();

    public ClientService() {
        Scanner input = new Scanner(System.in);
        String userChoice;

        do {
            displayMainMenu();
            userChoice = input.next();

            switch (userChoice) {
                case "1":
                    handleCreateNewClient(input);
                    break;
                case "2":
                    handleActivatePremiumAccount(input);
                    break;
                case "3":
                    displayClientFullNames();
                    break;
                case "4":
                    handleGetClientCreationDate(input);
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        } while (true);
    }

    private void displayMainMenu() {
        System.out.println("#############################");
        System.out.println("Select what you want to do.");
        System.out.println("1 - create new client.");
        System.out.println("2 - activate existed client to premium.");
        System.out.println("3 - get full name of client.");
        System.out.println("4 - get client creation date.");
        System.out.println();
        System.out.println("0 - Exit.");
    }
    private Client findClientById(String clientId) {
        for (Client client : clientsList){
            if (client.getClientId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }

    public String inputFirstName(Scanner keyboard) {
        System.out.print("Enter client first name: ");
        this.firstName = keyboard.next();
        return firstName;
    }

    public String inputLastName(Scanner keyboard) {
        System.out.print("Enter client last name: ");
        this.lastName = keyboard.next();
        return lastName;
    }

    @Override
    public String createNewClient(String firstName, String lastName) {
        Client client = new Client(firstName, lastName);
        clientsList.add(client);
        this.clientId = client.getClientId();
        System.out.println("Client with ID: " + clientId + " created successfully.");
        return client.getClientId();
    }

    private void handleCreateNewClient(Scanner input) {
        boolean validChoice = false;

        inputFirstName(input);
        inputLastName(input);
        createNewClient(this.firstName, this.lastName);

        do {
            System.out.println("Select:");
            System.out.println("1 - if you want to set this account as FREE.");
            System.out.println("2 - if you want to set this account as PREMIUM.");

            String internalUserChoice = input.next();
            switch (internalUserChoice) {
                case "1":
                    System.out.println("This account is set as free successfully.");
                    validChoice = true;
                    break;
                case "2":
                    activatePremiumAccount(this.clientId);
                    validChoice = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (!validChoice);
    }

    @Override
    public String activatePremiumAccount(String clientId) {
        Client client = findClientById(clientId);

        if (client != null){
            client.setPremium(true);
            System.out.println("Premium activated successfully for client with ID: " + clientId);
            return clientId;
        } else {
            throw new ClientNotFoundException(clientId);
        }
    }

    private void handleActivatePremiumAccount(Scanner input){
        boolean isAnyNonPremium = false;
        for (int i = 0; i < clientsList.size(); i++) {
            Client client = clientsList.get(i);
            if (!client.isPremium()) {
                isAnyNonPremium = true;
                System.out.println("Index: " + i);
                System.out.println("First Name: " + client.getFirstName());
                System.out.println("Last Name: " + client.getLastName());
                System.out.println("Client ID: " + client.getClientId());
                System.out.println("------------");
            }
        }
        if (isAnyNonPremium == true) {
            try {
                System.out.println("Select index of account which you want to set as premium.");
                String internalUserChoice = input.next();
                Client client = clientsList.get(Integer.parseInt(internalUserChoice));
                this.clientId = client.getClientId();
                activatePremiumAccount(this.clientId);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            }

        } else {
            System.out.println("There is no accounts without premium status.");
        }
    }

    @Override
    public String getClientFullName(String clientId) {
        Client client = findClientById(clientId);
        if(!client.getClientId().equals(clientId)) {
            throw new ClientNotFoundException(client.getClientId());
        }
        System.out.println(client.getFullName());
        return client.getFullName();
    }

    private void displayClientFullNames(){
        if (clientsList.size() > 0) {
            for (int i = 0; i < clientsList.size(); i++) {
                Client client = clientsList.get(i);
                this.clientId = client.getClientId();
                getClientFullName(this.clientId);
            }
        } else {
            System.out.println("There is no clients to display.");
        }
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) {
        Client client = findClientById(clientId);
        if(!client.getClientId().equals(clientId)) {
            throw new ClientNotFoundException(client.getClientId());
        }
        System.out.println(client.getCreationDate());
        return client.getCreationDate();
    }

    private void handleGetClientCreationDate(Scanner input){
        if (clientsList.size() > 0) {
            try{
                for (int i = 0; i < clientsList.size(); i++) {
                    Client client = clientsList.get(i);

                    System.out.println("Index: " + i);
                    System.out.println("First Name: " + client.getFirstName());
                    System.out.println("Last Name: " + client.getLastName());
                    System.out.println("Client ID: " + client.getClientId());
                    System.out.println("------------");
                }
                String internalUserChoice = input.next();
                System.out.println("Select index of client to display date.");
                Client client = clientsList.get(Integer.parseInt(internalUserChoice));
                this.clientId = client.getClientId();
                getClientCreationDate(this.clientId);
            } catch (IndexOutOfBoundsException ex){
                System.out.println("Invalid choice. Try again.");
            }
        }
        else {
            System.out.println("There is no clients to display.");
        }
    }

    @Override
    public boolean isPremiumClient(String clientId) { return false; }

    @Override
    public int getNumberOfClients() {
        return 0;
    }

    @Override
    public int getNumberOfPremiumClients() {
        return 0;
    }
}
