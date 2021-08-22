package by.itacademy.service;

import by.itacademy.repository.sql.RepositorySql;

public class Service {
    public MovieShowOperation movieShowOperation;
    public TicketOperation ticketOperation;
    public UserOperation userOperation;

    public Service(RepositorySql repository) {
        this.movieShowOperation = new MovieShowOperation(repository);
        this.ticketOperation = new TicketOperation(repository);
        this.userOperation = new UserOperation(repository);
    }
}
