package by.itacademy.entity;

public class Ticket {
    private int id;
    private String viewer = " ";
    private String movieShow;
    private int seat;
    private int price;
    private String availability = "available";

    public Ticket(int id, String viewer, String movieShow, int seat, int price, String availability) {
        this.id = id;
        this.viewer = viewer;
        this.movieShow = movieShow;
        this.seat = seat;
        this.price = price;
        this.availability = availability;
    }

    public Ticket(String movieShow, int seat) {
        this.movieShow = movieShow;
        this.seat = seat;
    }

    public Ticket() {
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getViewer() {
        return viewer;
    }

    public int getSeat() {
        return seat;
    }

    public int getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }

    public String getMovieShow() {
        return movieShow;
    }

    public void setViewer(String viewer) {
        this.viewer = viewer;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setMovieShow(String movieShow) {
        this.movieShow = movieShow;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Ticket ( " +
                "id =" + id +
                ", movie show ='" + movieShow + '\'' +
                ", seat =" + seat +
                ", price =" + price +
                ", " + availability + " )";
    }
}
