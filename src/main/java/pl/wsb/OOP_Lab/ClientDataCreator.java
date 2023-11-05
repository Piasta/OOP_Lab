package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ClientDataCreator implements Clients {
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDateTime currentDateTime = LocalDateTime.now();
    private DateTimeFormatter formatterDTTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private String clientId;
    private LocalDate creationDate;
    private boolean isPremium;

    @Override
    public String createNewClient(String firstName, String lastName) {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.fullName = this.firstName + " " + this.lastName;
        this.clientId = currentDateTime.format(formatterDTTM);
        this.creationDate = LocalDate.from(currentDateTime);
        return clientId;
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
        return fullName;
    }

    @Override
    public LocalDate getClientCreationDate(String clientId) {
        if(!this.clientId.equals(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
        return creationDate;
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