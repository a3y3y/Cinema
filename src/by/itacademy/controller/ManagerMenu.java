package by.itacademy.controller;

import by.itacademy.service.Service;
import by.itacademy.util.log.Log;

public class ManagerMenu extends MenuOperation implements Menu {

    public ManagerMenu(Service ser) {
        super(ser);
    }

    public void run() {
        while (true) {
            Log.logger.info("\"Main menu\" has been opened");
            System.out.println("\nAdd movie show:           enter 1");
            System.out.println("Edit movie show:          enter 2");
            System.out.println("Delete movie show:        enter 3");
            System.out.println("Buy ticket for viewer:    enter 4");
            System.out.println("Refund ticket for viewer: enter 5");
            System.out.println("Show all viewers:         enter 6");
            System.out.println("Exit to main menu:        enter 7");

            String x = sc.nextLine();
            if (x.equals("1")) {
                addMovieShow();
            } else if (x.equals("2")) {
                editMovieShow();
            } else if (x.equals("3")) {
                deleteMovieShow();
            } else if (x.equals("4")) {
                System.out.println("Enter viewer login");
                String login = sc.nextLine();
                Log.logger.info("Operation for viewer " + login + " is being done");
                buyTicket(login);
            } else if (x.equals("5")) {
                System.out.println("Enter viewer login");
                String login = sc.nextLine();
                Log.logger.info("Operation refund ticket for viewer " + login + " is being done");
                refundTicket(login);
            } else if (x.equals("6")) {
                showViewers();
            } else if (x.equals("7")) {
                Log.logger.info("User " + userSavedLogin + " has logged out");
                break;
            } else {
                System.out.println("You should enter number 1,2,3,4,5 or 6");
            }
        }
    }
}
