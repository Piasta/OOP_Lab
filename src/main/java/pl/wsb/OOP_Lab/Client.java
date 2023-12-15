package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Client {
    private final transient DateTimeFormatter formatterDTTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");;
    private String firstName;
    private String lastName;
    private String clientId;
    private LocalDate creationDate;
    private boolean isPremium;

    public Client(String firstName, String lastName) {
        setNameFromUpper(firstName, lastName);
        this.creationDate = LocalDate.now();
        this.clientId = UUID.randomUUID().toString();
        this.isPremium = false;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getFullName() { return firstName + " " + lastName;}
    public String getClientId() {
        return clientId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setNameFromUpper(String firstName, String lastName) {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
    }

    public boolean getIsPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }
}