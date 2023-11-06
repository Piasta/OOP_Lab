package pl.wsb.OOP_Lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClientDataCreatorTest {

    private ClientDataCreator clientDataCreator;
    private LocalDateTime currentDateTime;
    private String clientId;

    @BeforeEach
    void setUp() {
        this.clientDataCreator = new ClientDataCreator("Piotr", "Piasta");
        this.currentDateTime = LocalDateTime.now();
        this.clientId = clientDataCreator.setClientId(currentDateTime);
    }

    @Test
    void createNewClient() {
        assertEquals(clientId, clientDataCreator.createNewClient("Piotr", "Piasta"));
    }

    @Test
    void getClientFullName() {
        clientDataCreator.createNewClient("Piotr", "Piasta");
        assertEquals("Piotr Piasta", clientDataCreator.getClientFullName(clientId));

        clientDataCreator.createNewClient("piotr", "piasta");
        assertEquals("Piotr Piasta", clientDataCreator.getClientFullName(clientId));

        clientDataCreator.createNewClient("pIOTR", "pIASTA");
        assertEquals("Piotr Piasta", clientDataCreator.getClientFullName(clientId));
    }

    @Test
    void getClientCreationDate() {
        LocalDate localDate = LocalDate.from(currentDateTime);
        LocalDate creationDate = clientDataCreator.setCreationDate(currentDateTime);
        assertEquals(localDate, creationDate);
    }

    @Test
    void isPremiumClientWhenNotPremium() {
        assertFalse(clientDataCreator.isPremiumClient(clientId));
    }

    @Test
    void isPremiumClientWhenIsPremium() {
        clientDataCreator.activatePremiumAccount(clientId);
        assertTrue(clientDataCreator.isPremiumClient(clientId));
    }

    @Test
    void isPremiumClientWhenIdNotExist() {
        String wrongClientId = clientId + "0";
        assertThrows(ClientNotFoundException.class, () -> {
            clientDataCreator.activatePremiumAccount(wrongClientId);
        });
    }
}
