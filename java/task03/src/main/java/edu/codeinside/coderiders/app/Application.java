package edu.codeinside.coderiders.app;

import edu.codeinside.coderiders.app.entity.Building;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 *
 */
public class Application {

    /**
     *
     */
    private static final Logger
            LOGGER = LogManager.getLogger(Class.class.getName());

    /**
     *
     */
    private Building building = null;

    /**
     *  Default constructor.
     */
    public Application() {
        LOGGER.info("Application.constructor");
        this.building = new Building();
        LOGGER.debug(new StringBuilder().append("Building created as: ").append(this.building));
        LOGGER.info("Application.init done.");
    }

    /**
     *
     */
    public void run() {
        LOGGER.info("Enter floor number or * for exit.");

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("You clicked at floor: ");
            String userInputString = scanner.nextLine();

            LOGGER.debug(new StringBuilder().append("You clicked at floor: ").append(userInputString));

            if (userInputString.equals("*")) {
                LOGGER.info("Got * , will exit now.");
                break;
            } else {
                LOGGER.info("Got something else...");
                try {
                    Integer floor = Integer.parseInt(userInputString);

                    LOGGER.debug(new StringBuilder("You clicked at floor: ").append(floor));
                    LOGGER.info("Got valid floor number (at least a number :), will proceed to call the elevator.");

                    Integer elevatorId = this.building.callElevator(floor);

                    if (elevatorId == null) {
                        System.out.format("Can't send elevator to %d floor, sorry.\n", floor);
                    } else {
                        System.out.format("Elevator %d arrived at %d floor.\n", elevatorId, floor);
                    }
                } catch (Exception e) {
                    System.out.println("Wrong input format. Please, try again.");
                    LOGGER.warn("Wrong input format. Please, try again.");
                }
            }
        }
    }
}
