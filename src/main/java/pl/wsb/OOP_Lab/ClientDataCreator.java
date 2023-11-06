package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientDataCreator implements Clients {
    private String fullName;
    private final LocalDateTime currentDateTime = LocalDateTime.now();
    private final DateTimeFormatter formatterDTTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private String clientId;
    private LocalDate creationDate;
    private boolean isPremium;

    @Override
    public String setClientId(LocalDateTime dateTime) {
        this.clientId = dateTime.format(formatterDTTM);
        return this.clientId;
    }

    @Override
    public LocalDate setCreationDate(LocalDateTime dateTime) {
        this.creationDate = LocalDate.from(dateTime);
        return this.creationDate;
    }

    @Override
    public String createNewClient(String firstName, String lastName) {
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.fullName = firstName + " " + lastName;

        setClientId(currentDateTime);
        setCreationDate(currentDateTime);
        return this.clientId;
    }

    @Override
    public String activatePremiumAccount(String clientId) {
        if(!this.clientId.equals(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
        this.isPremium = true;
        return "Premium activated for client: " + clientId;
    }

    @Override
    public String getClientFullName(String clientId) {
        if(!this.clientId.equals(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
        return this.fullName;
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) {
        if(!this.clientId.equals(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
        return this.creationDate;
    }

    @Override
    public boolean isPremiumClient(String clientId) { return isPremium; }

    @Override
    public int getNumberOfClients() {
        return 0;
    }

    @Override
    public int getNumberOfPremiumClients() {
        return 0;
    }
}
