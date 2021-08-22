package by.itacademy;

import by.itacademy.controller.MainMenu;
import by.itacademy.repository.sql.RepositorySql;
import by.itacademy.service.Service;

public class Main {
    public static void main(String[] args) {
        RepositorySql repositorySql = new RepositorySql();
        Service service = new Service(repositorySql);
        MainMenu mainMenu = new MainMenu(service);
        mainMenu.run();
    }
}