package by.itacademy.controller;

import by.itacademy.service.Service;
import by.itacademy.util.log.Log;

public class ViewerMenu extends MenuOperation implements Menu {

    public ViewerMenu(Service ser) {
        super(ser);
    }

    public void run() {
        while (true) {
            Log.logger.info("\"Main menu\" has been opened");
            System.out.println("\nList of events:    enter 1");
            System.out.println("Buy ticket:        enter 2");
            System.out.println("Check my tickets:  enter 3");
            System.out.println("Ticket refund:     enter 4");
            System.out.println("Exit to main menu: enter 5");

            String x = sc.nextLine();
            if (x.equals("1")) {
                showListOfEvents();
                Log.logger.info("User has checked list of events");
            } else if (x.equals("2")) {
                buyTicket(MainMenu.userSavedLogin);
            } else if (x.equals("3")) {
                showViewerTickets(MainMenu.userSavedLogin);
                Log.logger.info("User has checked tickets");
            } else if (x.equals("4")) {
                refundTicket(userSavedLogin);
            } else if (x.equals("5")) {
                Log.logger.info("User " + userSavedLogin + " has logged out");
                break;
            } else System.out.println("\nYou should enter number 1,2,3,4 or 5");
        }
    }
}
