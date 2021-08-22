package by.itacademy.controller;

import by.itacademy.service.Service;
import by.itacademy.util.log.Log;

import java.util.Scanner;

public abstract class MenuOperation {

    private Service ser;

    public MenuOperation(Service ser) {
        this.ser = ser;
    }

    Scanner sc = new Scanner(System.in);
    static String userSavedLogin;

    public boolean logIn(String access) {
        System.out.println("Enter your login");
        String login = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();
        String s = (ser.userOperation.userCheck(login, password, access));
        System.out.println(s);
        if (s.equals("You have passed verification")) {
            MainMenu.userSavedLogin = login;
            if (access.equals("manager")) {
                Log.createNewFilehandlerForUser(login);
                Log.logger.info("User " + userSavedLogin + " has successfully logged in");
                return true;
            }
            if (access.equals("viewer")) {
                Log.createNewFilehandlerForUser(login);
                Log.logger.info("User " + userSavedLogin + " has successfully logged in");
                return true;
            }
            if (access.equals("admin")) {
                Log.createNewFilehandlerForUser(login);
                Log.logger.info("User " + userSavedLogin + " has successfully logged in");
                return true;
            }
        }
        return false;
    }

    public void register(String access) {
        System.out.println("Create your login");
        String login = sc.nextLine();
        System.out.println("Create your password");
        String password = sc.nextLine();
        if (login.equals("") || password.equals("")) {
            System.out.println("Your login or password is empty");
        } else if (ser.userOperation.userDuplicateLoginCheck(login)) {
            System.out.println(ser.userOperation.addUser(login, password, access));
            Log.logger.info("User " + login + " has been successfully registered");
        } else {
            System.out.println("This login has been already taken");
        }
    }

    public void editMovieShow() {
        showListOfEvents();
        Log.logger.info("\"Edit movie show\" menu has been opened");
        System.out.println("Enter movie show id you want to edit");
        String id = sc.nextLine();
        Log.logger.info("Movie show with id = " + id + " has been chosen");
        if (ser.movieShowOperation.checkMovieShow(id)) {
            System.out.println("Enter new movie title");
            String movie = sc.nextLine();
            System.out.println("Enter new showtime:");
            System.out.println("Year");
            String year = sc.nextLine();
            System.out.println("Month");
            String month = sc.nextLine();
            System.out.println("Day");
            String day = sc.nextLine();
            System.out.println("Hours");
            String hour = sc.nextLine();
            System.out.println("Minutes");
            String minute = sc.nextLine();
            try {
                System.out.println(ser.movieShowOperation.editMovieShow(Integer.parseInt(id), Integer.parseInt(year)
                        , Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour)
                        , Integer.parseInt(minute), movie));
            } catch (NumberFormatException e) {
                Log.logger.info("Incorrect date or id has been entered");
                System.out.println("Your showtime date or id is incorrect");
            }
        } else {
            Log.logger.info("Wrong id has been entered");
            System.out.println("Wrong movie show id");
        }
    }

    public void deleteMovieShow() {
        showListOfEvents();
        Log.logger.info("\"Delete movie show\" menu has been opened");
        System.out.println("Enter movie show id you want to delete");
        String id = sc.nextLine();
        Log.logger.info("Movie show with id = " + id + " has been chosen");
        try {
            System.out.println(ser.movieShowOperation.deleteMovieShow(Integer.parseInt(id)));
        } catch (NumberFormatException e) {
            Log.logger.info("Incorrect id has been entered");
            System.out.println("Incorrect movie show id");
        }
    }

    public void addMovieShow() {
        Log.logger.info("\"Add movie show\" menu has been opened");
        System.out.println("Enter movie title");
        String movie = sc.nextLine();
        System.out.println("Enter showtime:");
        System.out.println("Year");
        String year = sc.nextLine();
        System.out.println("Month");
        String month = sc.nextLine();
        System.out.println("Day");
        String day = sc.nextLine();
        System.out.println("Hours");
        String hour = sc.nextLine();
        System.out.println("Minutes");
        String minute = sc.nextLine();
        System.out.println(ser.movieShowOperation.createMovieShow(movie, year, month, day, hour, minute));
    }

    public void editUser() {
        showUsers();
        Log.logger.info("\"Edit user\" menu has been opened");
        System.out.println("\nEnter user login you want to edit");
        String login = sc.nextLine();
        Log.logger.info("User " + login + "is being edited");
        System.out.println("Enter new login");
        String newLogin = sc.nextLine();
        System.out.println("Enter new password");
        String newPassword = sc.nextLine();
        System.out.println(ser.userOperation.updateUser(login, newLogin, newPassword));
    }

    public void deleteUser() {
        showUsers();
        Log.logger.info("\"Delete user\" menu has been opened");
        System.out.println("\nEnter user login you want to delete");
        String login = sc.nextLine();
        System.out.println(ser.userOperation.deleteUser(login));
    }

    public void refundTicket(String login) {
        if (ser.userOperation.checkViewer(login)) {
            try {
                showViewerTickets(login);
                System.out.println("Enter ticket id you want to refund");
                String ticketId = sc.nextLine();
                Log.logger.info("User is trying to refund ticket number = " + ticketId);
                System.out.println(ser.ticketOperation.refundTicket(login, Integer.parseInt(ticketId)));
                Log.logger.info("Ticket refund has been successful");
            } catch (NumberFormatException e) {
                Log.logger.info("Wrong ticket id has been entered");
                System.out.println("Wrong ticket id");
            }
        } else {
            Log.logger.info("User " + login + " doesn't exist");
            System.out.println("There is no viewer with such login");
        }
    }

    public void buyTicket(String login) {
        showListOfEvents();
        Log.logger.info("\"Buy ticket\" menu has been entered");
        System.out.println("Enter movieShow id to see all available tickets");
        String movieShowId = sc.nextLine();
        Log.logger.info("Movie show with id = " + movieShowId + " has been chosen");
        if (ser.userOperation.checkViewer(login)) {
            if (ser.movieShowOperation.checkMovieShow(movieShowId) == false) {
                Log.logger.info("Movie show with id = " + movieShowId + " doesn't exist");
                System.out.println("Movie show with such id doesn't exist");
            } else {
                try {
                    ser.ticketOperation.getTicketListForMovieShow(Integer.parseInt(movieShowId)).stream()
                            .forEach(n -> System.out.println("id = " + n.getId() + ", " +
                                    n.getMovieShow() + ", seat = " + n.getSeat() + ", price = " +
                                    n.getPrice() + ", " + n.getAvailability()));
                    System.out.println("Enter ticket id you want to buy");
                    String ticketId = sc.nextLine();
                    Log.logger.info("Ticket with id = " + ticketId + " has been chosen");
                    System.out.println(ser.ticketOperation.buyTicket(login
                            , Integer.parseInt(ticketId)
                            , ser.ticketOperation.getTicketListForMovieShow(Integer.parseInt(movieShowId))));
                    Log.logger.info("Ticket for movie show " + movieShowId + " with id = " + ticketId +
                            " has been successfully bought");
                } catch (NumberFormatException e) {
                    Log.logger.info("Incorrect movie show or ticket id");
                    System.out.println("Incorrect movie show or ticket id");
                }
            }
        } else {
            Log.logger.info("Incorrect movie show or ticket id");
            System.out.println("There is no viewer with such login");
        }
    }

    public void showListOfEvents() {
        ser.movieShowOperation.getMovieShowList().stream().forEach(System.out::println);
    }

    public void showViewerTickets(String login) {
        ser.ticketOperation.getTicketListForViewer(login).stream()
                .forEach(n -> System.out.println("id = " + n.getId() + ", " + n.getMovieShow() +
                        ", seat = " + n.getSeat() + ", price = " + n.getPrice()));
    }

    public void showViewers() {
        ser.userOperation.getUserList().stream().filter(n -> n.getAccess().equals("viewer"))
                .forEach(System.out::println);
        Log.logger.info("\"Show all viewers\" menu has been opened");
    }

    public void showUsers() {
        ser.userOperation.getUserList().forEach(System.out::println);
        Log.logger.info("\"Show all users\" menu has been opened");
    }
}
