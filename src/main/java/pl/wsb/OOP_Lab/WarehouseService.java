package pl.wsb.OOP_Lab;

import java.util.*;

public class WarehouseService implements WarehouseInterface {

    private Map<String, List<WarehouseStock>>  warehouseMap;
    private ClientService clientService;
    public WarehouseService(ClientService clientService) {
        this.clientService = clientService;
        this.warehouseMap = new HashMap<>();
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
                case "2":
                    handleGetStoredMetalTypesByClient(input);
                    break;
                case "3":
                    selectStockForAllClients();
                    break;
                case "4":
                    handleGetMetalTypesToMassStoredByClient(input);
                    break;
                case "5":
                    handleGetTotalVolumeOccupiedByClient(input);
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

            WarehouseStock warehouseStock = new WarehouseStock(metalType, mass);
            List<WarehouseStock> stocks = warehouseMap.getOrDefault(clientId, new ArrayList<>());

            double totalMass = 0.0;
            double maxCapacity = client.getIsPremium() ? 1500.0 : 700.0;

            totalMass = getTotalMassOccupiedByClient(clientId);

            if (totalMass + mass > maxCapacity) {
                throw new FullWarehouseException(clientId);
            }

            boolean clientHaveThatMetalOnStock = false;
            for (WarehouseStock stock : stocks) {
                if (stock.getMetalType() == metalType) {
                    stock.setMass(stock.getMass() + mass);
                    clientHaveThatMetalOnStock = true;
                    break;
                }
            }
            if (!clientHaveThatMetalOnStock) {
                stocks.add(warehouseStock);
            }
            warehouseMap.put(clientId, stocks);
            System.out.println("Metal added to stock successfully for client ID: " + clientId);
        } catch (ClientNotFoundException ex) {
            System.out.println(ex);
        } catch (ProhibitedMetalTypeException ex) {
            System.out.println(ex);
        } catch (NumberFormatException ex) {
            System.out.println("Value is not int type. Try again.");
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
                double mass = 0;
                try {
                    System.out.println("Insert mass which you want to store.");
                    mass = Double.parseDouble(input.next());
                } catch (Exception isNotDouble) {
                    System.out.println("Value is not double type. Try again.");
                }
                if (mass > 0){
                    addMetalIngot(client.getClientId(), metalType, mass);
                }
                if (mass <= 0)
                    System.out.println("Value is too low. Try again.");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Value is not int type. Try again.");
            } catch (NullPointerException ex) {
                System.out.println(ex);
            } catch (FullWarehouseException ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("There is no clients to display. Create client at first.");
        }
    }

    @Override
    public Map<SupportedMetalType, Double> getMetalTypesToMassStoredByClient(String clientId) {
        List<WarehouseStock> stocks = warehouseMap.get(clientId);
        Map<SupportedMetalType, Double> metalTypesToMass = new HashMap<>();
        for (WarehouseStock stock : stocks) {
            SupportedMetalType type = stock.getMetalType();
            double mass = stock.getMass();
            metalTypesToMass.put(type, metalTypesToMass.getOrDefault(type, 0.0) + mass);
        }
        return metalTypesToMass;
    }
    private void handleGetMetalTypesToMassStoredByClient(Scanner input) {
        if (!clientService.clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientService.clientsList.size(); i++) {
                    clientService.displayClient(i);
                }
                System.out.println("Select client index to get mass for metal types by client.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientService.clientsList.get(internalUserChoice);
                System.out.println(getMetalTypesToMassStoredByClient(client.getClientId()));

            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Value is not int type. Try again.");
            } catch (NullPointerException ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("There is no data to display. Create stock for client at first.");
        }
    }

    @Override
    public double getTotalVolumeOccupiedByClient(String clientId) {
        List<WarehouseStock> stocks = warehouseMap.get(clientId);
        double totalVolume = 0.0;
        for (WarehouseStock stock : stocks) {
            SupportedMetalType type = stock.getMetalType();
            double mass = stock.getMass();
            double density = type.getDensity();
            double volume = mass / density;
            totalVolume += volume;
        }
        return totalVolume;
    }

    public double getTotalMassOccupiedByClient(String clientId) {
        try {
            List<WarehouseStock> stocks = warehouseMap.get(clientId);
            double totalMass = 0.0;
            for (WarehouseStock stock : stocks) {
                double mass = stock.getMass();
                totalMass += mass;
            }
            return totalMass;
        } catch (NullPointerException ex) {
            return 0.0;
        }
    }
    private void handleGetTotalVolumeOccupiedByClient(Scanner input) {
        if (!clientService.clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientService.clientsList.size(); i++){
                    clientService.displayClient(i);
                }
                System.out.println("Select client index to get total volume occupied by client.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientService.clientsList.get(internalUserChoice);
                double volume = getTotalVolumeOccupiedByClient(client.getClientId());
                System.out.println("Total volume occupied by client is: " + volume);
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Value is not int type. Try again.");
            } catch (NullPointerException ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("There is no data to display. Create stock for client at first.");
        }
    }

    @Override
    public List<SupportedMetalType> getStoredMetalTypesByClient(String clientId) {
        List<WarehouseStock> stocks = warehouseMap.get(clientId);
        List<SupportedMetalType> storedMetalTypes = new ArrayList<>();
        for (WarehouseStock stock : stocks) {
            storedMetalTypes.add(stock.getMetalType());
        }
        return storedMetalTypes;
    }

    private void handleGetStoredMetalTypesByClient(Scanner input) {
        if (!clientService.clientsList.isEmpty()) {
            try{
                for (int i = 0; i < clientService.clientsList.size(); i++){
                    clientService.displayClient(i);
                }
                System.out.println("Select client index to get stored metal types by client.");
                int internalUserChoice = Integer.parseInt(input.next());
                Client client = clientService.clientsList.get(internalUserChoice);
                System.out.println(getStoredMetalTypesByClient(client.getClientId()));

            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Invalid choice. Try again.");
            } catch (ClientNotFoundException ex) {
                System.out.println("Client not found.");
            } catch (NumberFormatException ex) {
                System.out.println("Value is not int type. Try again.");
            } catch (NullPointerException ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("There is no data to display. Create stock for client at first.");
        }
    }

    private void selectStockForAllClients(){
        for (Map.Entry<String, List<WarehouseStock>> entry : warehouseMap.entrySet()) {
            String clientId = entry.getKey();
            List<WarehouseStock> stocks = entry.getValue();

            System.out.println("Client ID: " + clientId);

            for (WarehouseStock stock : stocks) {
                SupportedMetalType metalType = stock.getMetalType();
                double mass = stock.getMass();

                System.out.println("Metal Type: " + metalType);
                System.out.println("Mass: " + mass);
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("#############################");
        System.out.println("Select what you want to do.");
        System.out.println("1 - set metal ingot to client.");
        System.out.println("2 - to select stored metal types by client.");
        System.out.println("3 - to select stock for all clients.");
        System.out.println("4 - to get metal types to mass stored by client.");
        System.out.println("5 - to get volume occupied by client.");
        System.out.println();
        System.out.println("0 - back.");
    }
}