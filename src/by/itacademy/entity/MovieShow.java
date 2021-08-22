package by.itacademy.entity;


import by.itacademy.service.TicketOperation;


import java.time.LocalDateTime;
import java.util.List;

public class MovieShow {
    private int id;
    private String movie;
    private LocalDateTime showTime;
    private List ticketsList;

    public MovieShow() {
    }

    public MovieShow(String movie, LocalDateTime showTime) {
        this.movie = movie;
        this.showTime = showTime;
    }

    public MovieShow(int id, String movie, LocalDateTime showTime) {
        this.id = id;
        this.movie = movie;
        this.showTime = showTime;
    }

    public String getMovie() {
        return movie;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public int getId() {
        return id;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", movie = '" + movie + '\'' +
                ", " + showTime;
    }
}
