package pl.wsb.OOP_Lab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ClientDataCreatorTest {

    private ClientDataCreator clientDataCreator;
    private String clientId;
    private LocalDateTime currentDateTime = LocalDateTime.now();
    private DateTimeFormatter formatterDTTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @BeforeEach
    void setUp() {
        clientDataCreator = new ClientDataCreator();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatterDTTM = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        this.clientId = currentDateTime.format(formatterDTTM);
    }

    @Test
    void getClientFullName() {
        clientDataCreator.createNewClient("piotr", "piasta");
        assertEquals("Piotr Piasta", clientDataCreator.getClientFullName(clientId));
    }

    @Test
    void getClientCreationDate() {
        clientDataCreator.createNewClient("piotr", "piasta");
        assertEquals(LocalDate.from(currentDateTime), clientDataCreator.getClientCreationDate(clientId));
    }

    @Test
    void isPremiumClient() {
    }
}