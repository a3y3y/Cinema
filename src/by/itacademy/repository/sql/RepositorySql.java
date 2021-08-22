package by.itacademy.repository.sql;

public class RepositorySql {
    public UserDao userDao;
    public TicketDao ticketDao;
    public MovieShowDao movieShowDao;

    public RepositorySql() {
        this.userDao = new UserDao();
        this.ticketDao = new TicketDao();
        this.movieShowDao = new MovieShowDao();
    }
}
