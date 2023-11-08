package pl.wsb.OOP_Lab;

import java.util.Scanner;

public interface ClientsManagerInterface {

    /**
     * Get client first name from keyboard
     * @param keyboard First name input by user
     * @return First name of client to create
     */
    String inputFirstName(Scanner keyboard);

    /**
     * Get client last name from keyboard
     * @param keyboard Last name input by user
     * @return Last name of client to create
     */
    String inputLastName(Scanner keyboard);
}
