package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ClientDataCreatorTest {

    private ClientDataCreator clientDataCreator;
    private LocalDateTime currentDateTime;
    private String clientId;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.clientDataCreator = new ClientDataCreator();
        this.currentDateTime = LocalDateTime.now();
        this.clientId = clientDataCreator.setClientId(currentDateTime);
    }

    @org.junit.jupiter.api.Test
    void createNewClient() {
        assertEquals(clientId, clientDataCreator.createNewClient("Piotr", "Piasta"));
    }

    @org.junit.jupiter.api.Test
    void getClientFullName() {
        assertEquals("Piotr Piasta", clientDataCreator.createNewClient("piotr", "piasta"));
        assertEquals("Piotr Piasta", clientDataCreator.createNewClient("piotr", "piasta"));
        assertEquals("Piotr Piasta", clientDataCreator.createNewClient("pIOTR", "pIASTA"));
    }

    @org.junit.jupiter.api.Test
    void getClientCreationDate() {
        LocalDate localDate = LocalDate.from(currentDateTime);
        LocalDate creationDate = clientDataCreator.setCreationDate(currentDateTime);
        assertEquals(localDate, creationDate);
    }

    @org.junit.jupiter.api.Test
    void isPremiumClientWhenNotPremium() {
        assertFalse(clientDataCreator.isPremiumClient(clientId));
    }

    @org.junit.jupiter.api.Test
    void isPremiumClientWhenIsPremium() {
        clientDataCreator.activatePremiumAccount(clientId);
        assertTrue(clientDataCreator.isPremiumClient(clientId));
    }

    @org.junit.jupiter.api.Test
    void isPremiumClientWhenIdNotExist() {
        String wrongClientId = clientId + "0";
        assertThrows(ClientNotFoundException.class, () -> {
            clientDataCreator.activatePremiumAccount(wrongClientId);
        });
    }
}
