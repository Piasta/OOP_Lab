package pl.wsb.OOP_Lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
    private ClientService clientService;
    private LocalDateTime currentDateTime;
    private String clientId;

    @BeforeEach
    void setUp() {
        this.clientService = new ClientService();
        this.currentDateTime = LocalDateTime.now();
        clientService.setClientId(currentDateTime);
        this.clientId = clientService.getClientId();
    }

    @Test
    void createNewClient() {
        assertEquals(clientId, clientService.createNewClient("Piotr", "Piasta"));
    }

    @Test
    void getClientFullName() {
        clientService.createNewClient("Piotr", "Piasta");
        assertEquals("Piotr Piasta", clientService.getClientFullName(clientId));

        clientService.createNewClient("piotr", "piasta");
        assertEquals("Piotr Piasta", clientService.getClientFullName(clientId));

        clientService.createNewClient("pIOTR", "pIASTA");
        assertEquals("Piotr Piasta", clientService.getClientFullName(clientId));
    }

    @Test
    void getClientCreationDate() {
        LocalDate localDate = LocalDate.from(currentDateTime);
        clientService.setCreationDate(currentDateTime);
        LocalDate creationDate = clientService.getCreationDate();
        assertEquals(localDate, creationDate);
    }

    @Test
    void isPremiumClientWhenNotPremium() {
        assertFalse(clientService.isPremiumClient(clientId));
    }

    @Test
    void isPremiumClientWhenIsPremium() {
        clientService.activatePremiumAccount(clientId);
        assertTrue(clientService.isPremiumClient(clientId));
    }

    @Test
    void isPremiumClientWhenIdNotExist() {
        String wrongClientId = clientId + "0";
        assertThrows(ClientNotFoundException.class, () -> clientService.activatePremiumAccount(wrongClientId));
    }
}
