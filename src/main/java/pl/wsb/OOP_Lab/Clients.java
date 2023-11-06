package pl.wsb.OOP_Lab;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Clients {

    /**
     * Sets and store a new clientId from date and time.
     * @param dateTime Actual date and time.
     */
    String setClientId(LocalDateTime dateTime);

    /**
     * Sets date of Client creation.
     * @param dateTime Actual date and time.
     * */
    LocalDate setCreationDate(LocalDateTime dateTime);

    /**
     * Creates a new client and stores their personal information.
     * @param firstName Client first name.
     * @param lastName Client last name.
     * @return Created user's identifier.
     */
    String createNewClient(String firstName, String lastName);

    /**
     * Sets the customer account as a premium account.
     * @param clientId Client identifier returned after its creation.
     * @return Client identifier returned after its creation.
     * @throws ClientNotFoundException Thrown when the client doesn't exist.
     */
    String activatePremiumAccount(String clientId);

    /**
     * @param clientId Client identifier returned after its creation.
     * @return Client full name consisting of client's first and last name e.g. "John Doe".
     * @throws ClientNotFoundException Thrown when the client doesn't exist.
     */
    String getClientFullName(String clientId);

    /**
     * @param clientId Client identifier returned after its creation.
     * @return LocalDate when the client was created.
     * @throws ClientNotFoundException Thrown when the client doesn't exist.
     */
    LocalDate getClientCreationDate(String clientId);

    boolean isPremiumClient(String clientId);

    int getNumberOfClients();

    int getNumberOfPremiumClients();
}
