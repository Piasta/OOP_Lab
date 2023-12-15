package pl.wsb.OOP_Lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
    ClientService cs;
    String firstName;
    String lastName;
    String clientId;

    @BeforeEach
    void setUp() {
        this.cs = new ClientService();
        this.firstName = "Piotr";
        this.lastName = "Piasta";
        this.clientId = cs.createNewClient(firstName, lastName);
    }

    @Test
    void createNewClient() {
        assertNotNull(clientId);
        assertEquals(1, cs.getNumberOfClients());
        cs.createNewClient(firstName, lastName);
        assertEquals(2, cs.getNumberOfClients());
        cs.createNewClient(firstName, lastName);
        assertEquals(3, cs.getNumberOfClients());
    }

    @Test
    void activatePremiumAccount() {
        assertFalse(cs.isPremiumClient(clientId));
        cs.activatePremiumAccount(clientId);
        assertTrue(cs.isPremiumClient(clientId));
    }

    @Test
    void getClientFullName() {
        assertEquals("Piotr Piasta", cs.getClientFullName(clientId));

        clientId = cs.createNewClient("piotr", "piasta");
        assertEquals("Piotr Piasta", cs.getClientFullName(clientId));

        clientId = cs.createNewClient("pIOTR", "pIASTA");
        assertEquals("Piotr Piasta", cs.getClientFullName(clientId));
    }

    @Test
    void getClientCreationDate() {
        LocalDate creationDate = cs.getClientCreationDate(clientId);
        assertEquals(LocalDate.now(), creationDate);
    }

    @Test
    void isPremiumClient() {
        assertFalse(cs.isPremiumClient(clientId));
        cs.activatePremiumAccount(this.clientId);
        assertTrue(cs.isPremiumClient(this.clientId));
    }

    @Test
    void getNumberOfClients() {
        assertEquals(1, cs.getNumberOfClients());
        cs.createNewClient(firstName, lastName);
        assertEquals(2, cs.getNumberOfClients());
        cs.createNewClient(firstName, lastName);
        assertEquals(3, cs.getNumberOfClients());
    }

    @Test
    void getNumberOfPremiumClients() {
        cs.getNumberOfClients();
        int qtyOfPremium = cs.getNumberOfPremiumClients();
        assertEquals(0, qtyOfPremium);
        cs.activatePremiumAccount(clientId);
        cs.getNumberOfClients();
        qtyOfPremium = cs.getNumberOfPremiumClients();
        assertEquals(1, qtyOfPremium);
    }
}