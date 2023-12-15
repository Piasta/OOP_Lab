package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientService implements ClientsInterface {
    private String firstName;
    private String lastName;
    private boolean isPremium;
    private String clientId;
    protected List<Client> clientsList = new ArrayList<>();

    public void clientServiceRun(){
        Scanner input = new Scanner(System.in);
        String userChoice;
        boolean exitService = false;
        while (!exitService) {
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
                    handleGetClientFullName(input);
                    break;
                case "4":
                    handleGetClientCreationDate(input);
                    break;
                case "5":
                    handleIsPremiumClient(input);
                    break;
                case "6":
                    getNumberOfClients();
                    break;
                case "7":
                    getNumberOfPremiumClients();
                    break;
                case "0":
                    exitService = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }
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
                displayClient(i);
            }
        }
        if (isAnyNonPremium) {
            try {
                System.out.println("Select index of account which you want to set as premium.");
                System.out.println("0 - if you want to exit.");
                int internalUserChoice = Integer.parseInt(input.next());
                if (!(internalUserChoice == 0)) {
                    Client client = clientsList.get(internalUserChoice);
                    this.clientId = client.getClientId();
                    activatePremiumAccount(this.clientId);
                }
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
        if(client == null) {
            throw new ClientNotFoundException(clientId);
        }
        String fullName = client.getFullName();
        System.out.println("The name of Client ID " + clientId + " is: " + fullName);
        return client.getFullName();
    }
    private void handleGetClientFullName(Scanner input){
        if (!clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientsList.size(); i++){
                    displayClient(i);
                }
                System.out.println("Select index of client to display full name.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientsList.get(internalUserChoice);
                getClientFullName(client.getClientId());
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            }
        } else {
            System.out.println("There is no clients to display.");
        }
    }


    @Override
    public LocalDate getClientCreationDate(String clientId) {
        Client client = findClientById(clientId);
        if(client == null) {
            throw new ClientNotFoundException(clientId);
        }
        LocalDate creationDate = client.getCreationDate();
        System.out.println("Client ID: " + clientId + " was created on: " + creationDate);
        return client.getCreationDate();
    }
    private void handleGetClientCreationDate(Scanner input){
        if (!clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientsList.size(); i++){
                    displayClient(i);
                }
                System.out.println("Select index of client to display full name.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientsList.get(internalUserChoice);
                getClientCreationDate(client.getClientId());
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            }
        } else {
            System.out.println("There is no clients to display.");
        }
    }


    @Override
    public boolean isPremiumClient(String clientId) {
        Client client = findClientById(clientId);
        if (client == null){
            throw new ClientNotFoundException(clientId);
        }
        this.isPremium = client.getIsPremium();
        if (isPremium) System.out.println("Client ID: " + clientId + " |IS PREMIUM");
        if (!isPremium) System.out.println("Client ID: " + clientId + " |IS NOT PREMIUM");
        return this.isPremium;
    }
    private void handleIsPremiumClient(Scanner input){
        if (!clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientsList.size(); i++){
                    displayClient(i);
                }
                System.out.println("Select index of client to display full name.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientsList.get(internalUserChoice);
                isPremiumClient(client.getClientId());
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            }
        } else {
            System.out.println("There is no clients to display.");
        }
    }

    @Override
    public int getNumberOfClients() {
        int clientsQty;
        clientsQty = clientsList.size();
        System.out.println(clientsQty + " clients exist.");
        return clientsQty;
    }
    @Override
    public int getNumberOfPremiumClients() {
        int premiumClientsQty = 0;
        for (Client client : clientsList) {
            if (client.getIsPremium()) premiumClientsQty ++;
        }
        System.out.println(premiumClientsQty + " premium clients exist.");
        return premiumClientsQty;
    }

    private void displayMainMenu() {
        System.out.println("#############################");
        System.out.println("Select what you want to do.");
        System.out.println("1 - create new client.");
        System.out.println("2 - activate existed client to premium.");
        System.out.println("3 - get full name of client.");
        System.out.println("4 - get client creation date.");
        System.out.println("5 - get info about premium status.");
        System.out.println("6 - get quantity of all clients.");
        System.out.println("7 - get quantity of premium clients.");
        System.out.println();
        System.out.println("0 - back.");
    }
    public void inputFirstName(Scanner keyboard) {
        System.out.print("Enter client first name: ");
        this.firstName = keyboard.next();
    }
    public void inputLastName(Scanner keyboard) {
        System.out.print("Enter client last name: ");
        this.lastName = keyboard.next();
    }
    public Client findClientById(String clientId) {
        for (Client client : clientsList){
            if (client.getClientId().equals(clientId)) {
                return client;
            }
        }
        return null;
    }
    protected void displayClient(int index){
        Client client = clientsList.get(index);
        System.out.println("Index: " + index);
        System.out.println("First Name: " + client.getFirstName());
        System.out.println("Last Name: " + client.getLastName());
        System.out.println("Client ID: " + client.getClientId());
        System.out.println("------------");
    }
}