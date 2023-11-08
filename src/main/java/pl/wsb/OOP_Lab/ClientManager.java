package pl.wsb.OOP_Lab;

import java.util.Scanner;

public class ClientManager extends JsonService implements ClientsManagerInterface {

    private String firstName;
    private String lastName;

    public ClientManager() {
        Scanner input = new Scanner(System.in);
        int choice;

        System.out.println("Select what you want to do.");
        System.out.println("1 - create new client.");

        choice = input.nextInt();

        if (choice == 1) {
            inputFirstName(input);
            inputLastName(input);
            ClientService clientCreator = new ClientService();
            clientCreator.createNewClient(firstName, lastName);
            objectToJson(clientCreator);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    @Override
    public String inputFirstName(Scanner keyboard) {
        System.out.print("Enter client first name: ");
        this.firstName = keyboard.next();
        return firstName;
    }

    @Override
    public String inputLastName(Scanner keyboard) {
        System.out.print("Enter client last name: ");
        this.lastName = keyboard.next();
        return lastName;
    }
}
