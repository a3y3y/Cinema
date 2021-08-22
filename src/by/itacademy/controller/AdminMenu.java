package by.itacademy.controller;

import by.itacademy.service.Service;
import by.itacademy.util.log.Log;

public class AdminMenu extends MenuOperation {

    public AdminMenu(Service ser) {
        super(ser);
    }

    public void run() {
        while (true) {
            Log.logger.info("\"Main menu\" has been opened");
            System.out.println("\nAdd user:          enter 1");
            System.out.println("Edit user:         enter 2");
            System.out.println("Delete user:       enter 3");
            System.out.println("Show all users:    enter 4");
            System.out.println("Edit movie show:   enter 5");
            System.out.println("Delete movie show: enter 6");
            System.out.println("Exit:              enter 7");

            String x = sc.nextLine();
            if (x.equals("1")) {
                System.out.println("Register manager: enter 1");
                System.out.println("Register viewer:  enter 2");
                x = sc.nextLine();
                if (x.equals("1")) {
                    register("manager");
                } else if (x.equals("2")) {
                    register("viewer");
                } else System.out.println("You should enter number 1 or 2");
            } else if (x.equals("2")) {
                editUser();
            } else if (x.equals("3")) {
                deleteUser();
            } else if (x.equals("4")) {
                showUsers();
            } else if (x.equals("5")) {
                editMovieShow();
            } else if (x.equals("6")) {
                deleteMovieShow();
            } else if (x.equals("7")) {
                Log.logger.info("User " + userSavedLogin + " has logged out");
                break;
            } else {
                System.out.println("You should enter number 1,2,3,4 or 5");
            }
        }
    }
}
