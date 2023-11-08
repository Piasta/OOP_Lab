package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientService implements ClientsInterface {
    private final transient LocalDateTime currentDateTime;
    private final transient DateTimeFormatter formatterDTTM;
    private String fullName;
    private String clientId;
    private String creationDate;
    private boolean isPremium;

    public ClientService() {
        this.currentDateTime = LocalDateTime.now();
        this.formatterDTTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    }

    @Override
    public String createNewClient(String firstName, String lastName) {
        setFullName(firstName, lastName);
        setClientId(getCurrentDateTime());
        setCreationDate(getCurrentDateTime());
        return getClientId();
    }

    @Override
    public String activatePremiumAccount(String clientId) {
        if(!getClientId().equals(clientId)) {
            throw new ClientNotFoundException(clientId);
        }
        setPremium(true);
        return "Premium activated for client: " + getClientId();
    }

    @Override
    public String getClientFullName(String clientId) {
        if(!getClientId().equals(clientId)) {
            throw new ClientNotFoundException(getClientId());
        }
        return getFullName();
    }

    @Override
    public String getClientCreationDate(String clientId) {
        if(!getClientId().equals(clientId)) {
            throw new ClientNotFoundException(getClientId());
        }
        return getCreationDate();
    }

    @Override
    public boolean isPremiumClient(String clientId) { return getIsPremium(); }

    @Override
    public int getNumberOfClients() {
        return 0;
    }

    @Override
    public int getNumberOfPremiumClients() {
        return 0;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public DateTimeFormatter getFormatterDTTM() {
        return formatterDTTM;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String firstName, String lastName) {
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.fullName = firstName + " " + lastName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(LocalDateTime localDateTime) {
        this.clientId = localDateTime.format(getFormatterDTTM());
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime localDateTime) {
        this.creationDate = LocalDate.from(localDateTime).toString();
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }
}
