package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WarehouseService implements WarehouseInterface {

    private ClientService clientService;

    public WarehouseService(ClientService clientService) {
        this.clientService = clientService;
    }
    public void warehouseServiceRun(){
        Scanner input = new Scanner(System.in);
        String userChoice;
        boolean exitService = false;
        while (!exitService) {
            displayMainMenu();
            userChoice = input.next();

            switch (userChoice) {
                case "1":
                    handleAddMetalIngot(input);
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
    public void addMetalIngot(String clientId, SupportedMetalType metalType, double mass)
            throws ClientNotFoundException, ProhibitedMetalTypeException, FullWarehouseException {
        Client client = clientService.findClientById(clientId);
        try {
            if (client == null){
                throw new ClientNotFoundException(clientId);
            }
            if (!client.getIsPremium()) {
                boolean allowedMetal;
                allowedMetal = isAllowedForPremiumOnly(metalType);
                if (!allowedMetal){
                    throw new ProhibitedMetalTypeException(metalType, clientId);
                }
            }
        } catch (ClientNotFoundException ex) {
            System.out.println(ex);
        } catch (ProhibitedMetalTypeException ex) {
            System.out.println(ex);
        }
    }

    private boolean isAllowedForPremiumOnly(SupportedMetalType metalType){
        SupportedMetalType[] premiumMetalTypes = {
                SupportedMetalType.GOLD,
                SupportedMetalType.PLATINUM
        };

        for (SupportedMetalType premiumType : premiumMetalTypes){
            if (premiumType.equals(metalType)) {
                return false;
            }
        }
        return true;
    }

    private void displayMetalTypes() {
        SupportedMetalType[] metalTypes = SupportedMetalType.values();
        for (int i = 0; i < metalTypes.length; i++) {
            System.out.println(i + " - " + metalTypes[i].name());
        }
    }

    private SupportedMetalType selectedMetalType() {
        Scanner input = new Scanner(System.in);
        displayMetalTypes();

        int userChoice;
        System.out.println("Choose index of metal to select.");
        do {
            while (!input.hasNextInt()) {
                System.out.println("Invalid choice. Try again.");
                System.out.println("Choose index of metal to select.");
                input.next();
            }
            userChoice = input.nextInt();
        } while (userChoice < 0 || userChoice >= SupportedMetalType.values().length);

        return SupportedMetalType.values()[userChoice];
    }

    private void handleAddMetalIngot(Scanner input){
        if (!clientService.clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientService.clientsList.size(); i++){
                    clientService.displayClient(i);
                }
                System.out.println("Select index of client to add metal ingot.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientService.clientsList.get(internalUserChoice);
                SupportedMetalType metalType = selectedMetalType();
                addMetalIngot(client.getClientId(), metalType, 50.00);
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
    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId) {
        return null;
    }

    @Override
    public double getTotalVolumeOccupiedByClient(String clientId) {
        //v = mass/density
        return 0;
    }

    @Override
    public List<SupportedMetalType> getStoredMetalTypesByClient(String clientId) {
        return null;
    }

    private void displayMainMenu() {
        System.out.println("#############################");
        System.out.println("Select what you want to do.");
        System.out.println("1 - set metal ingot to client.");
        System.out.println();
        System.out.println("0 - back.");
    }
}
