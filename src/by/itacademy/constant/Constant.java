package by.itacademy.constant;

public class Constant {
    public static final String USER_CREATE = "INSERT INTO users (login, password, access, salt) VALUES (?,?,?,?)";
    public static final String USER_UPDATE = "UPDATE users SET login=?, password=? WHERE login=?";
    public static final String USER_READ = "SELECT * FROM users WHERE id=?";
    public static final String USER_DELETE = "DELETE FROM users WHERE login=?";
    public static final String USER_READ_ALL = "SELECT * FROM users";

    public static final String MOVIE_SHOW_CREATE = "INSERT INTO movie_shows (movie, showTime) VALUES (?,?)";
    public static final String MOVIE_SHOW_UPDATE = "UPDATE movie_shows SET movie=?, showTime=? WHERE id=?";
    public static final String MOVIE_SHOW_READ = "SELECT * FROM movie_shows WHERE id=?";
    public static final String MOVIE_SHOW_DELETE = "DELETE FROM movie_shows WHERE id=?";
    public static final String MOVIE_SHOW_READ_ALL = "SELECT * FROM movie_shows";

    public static final String TICKET_CREATE = "INSERT INTO tickets (viewer, movieShow, seat" +
            ", price, availability) VALUES (?,?,?,?,?)";
    public static final String TICKET_UPDATE = "UPDATE tickets SET viewer=?" +
            ", availability=?, movieShow=? WHERE id=?";
    public static final String TICKET_READ = "SELECT * FROM tickets WHERE id=?";
    public static final String TICKET_DELETE = "DELETE FROM tickets WHERE id=?";
    public static final String TICKET_READ_ALL = "SELECT * FROM tickets";
}
