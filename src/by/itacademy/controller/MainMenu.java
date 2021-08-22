package by.itacademy.controller;
import by.itacademy.service.Service;

public class MainMenu extends MenuOperation implements Menu {

    private AdminMenu adminMenu;
    private ViewerMenu viewerMenu;
    private ManagerMenu managerMenu;


    public MainMenu(Service ser) {
        super(ser);
        this.adminMenu = new AdminMenu(ser);
        this.viewerMenu = new ViewerMenu(ser);
        this.managerMenu = new ManagerMenu(ser);
    }

    public void run() {
        while (true) {
            System.out.println("\nChoose Your Destiny:");
            System.out.println("Viewer:  enter 1");
            System.out.println("Manager: enter 2");
            System.out.println("Admin:   enter 3");
            System.out.println("Exit:    enter 4");
            String x = sc.nextLine();
            if (x.equals("1")) {
                System.out.println("Sign in:  enter 1");
                System.out.println("Register: enter 2");
                x = sc.nextLine();
                if (x.equals("1")) {
                    if (logIn("viewer")) {
                        viewerMenu.run();
                    }
                } else if (x.equals("2")) {
                    register("viewer");
                } else System.out.println("You should enter number 1 or 2");
            } else if (x.equals("2")) {
                if (logIn("manager")) {
                    managerMenu.run();
                }
            } else if (x.equals("3")) {
                if (logIn("admin")) {
                    adminMenu.run();
                }
            } else if (x.equals("4")) {
                break;
            } else {
                System.out.println("You should enter number 1,2 or 3");
            }
        }
    }
}