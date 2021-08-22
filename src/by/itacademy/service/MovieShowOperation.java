package by.itacademy.service;

import by.itacademy.entity.MovieShow;
import by.itacademy.entity.Ticket;
import by.itacademy.exception.OperationException;
import by.itacademy.repository.sql.RepositorySql;
import by.itacademy.util.log.Log;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MovieShowOperation {
    private RepositorySql rep;

    public MovieShowOperation(RepositorySql rep) {
        this.rep = rep;
    }

    public List<MovieShow> getMovieShowList() {
        List<MovieShow> movieShowList = new ArrayList<>();
        try {
            movieShowList = rep.movieShowDao.readAll();
        } catch (OperationException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        }
        return movieShowList;
    }

    public String createMovieShow(String movie, String year, String month, String day
            , String hour, String minute) {
        MovieShow movieShow;
        try {
            movieShow = new MovieShow(movie, LocalDateTime.of(Integer.parseInt(year)
                    , Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour),
                    Integer.parseInt(minute)));
        } catch (NumberFormatException | DateTimeException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            Log.logger.info("Incorrect showtime date has been entered");
            return "Your showtime date is incorrect";
        }
        try {
            if (rep.movieShowDao.create(movieShow)) {
                List<Ticket> ticketList = new ArrayList<>();
                for (int seat = 1; seat <= 15; seat++) {
                    Ticket ticket = new Ticket(movieShow.getMovie() + " " +
                            String.valueOf(movieShow.getShowTime()), seat);
                    if (seat <= 5) {
                        ticket.setPrice(8);
                    }
                    if (seat > 5 & seat <= 10) {
                        ticket.setPrice(6);
                    }
                    if (seat > 10 & seat <= 15) {
                        ticket.setPrice(4);
                    }
                    try {
                        rep.ticketDao.create(ticket);
                    } catch (OperationException e) {
                        e.getMessage();
                    }
                    ticketList.add(ticket);
                }
                Log.logger.info("Movie show with movie title = " + movieShow.getMovie() +
                        " and showtime = " + movieShow.getShowTime() + " has been created");
                return "MovieShow has been created";
            }
        } catch (OperationException | NullPointerException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        }
        Log.logger.info("Movie show hasn't been created");
        return "Can't create movie show";
    }

    public boolean checkMovieShow(String id) {
        try {
            Optional<MovieShow> ms = rep.movieShowDao.readAll().stream()
                    .filter(n -> n.getId() == Integer.parseInt(id)).findFirst();
            if (ms.isPresent()) {
                return true;
            }
        } catch (OperationException | NumberFormatException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return false;
        }
        return false;
    }

    public String editMovieShow(int id, int year, int month, int day, int hour
            , int minute, String movie) {
        try {
            MovieShow movieShow = getMovieShow(id);
            List<Ticket> ticketList = rep.ticketDao.readAll().stream().filter(n -> n.getMovieShow()
                    .equals(movieShow.getMovie() + " " + movieShow.getShowTime()))
                    .collect(Collectors.toList());
            movieShow.setShowTime(LocalDateTime.of(year, month, day, hour, minute));
            movieShow.setMovie(movie);
            rep.movieShowDao.update(String.valueOf(id), movieShow);
            List<Ticket> ticketList1 = ticketList.stream().map(n -> {
                n.setMovieShow(movie +
                        " " + String.valueOf(LocalDateTime.of(year, month, day, hour, minute)));
                return n;
            }).collect(Collectors.toList());
            for (Ticket ticket : ticketList1) {
                rep.ticketDao.update(String.valueOf(ticket.getId()), ticket);
            }
        } catch (OperationException | DateTimeException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        } catch (NoSuchElementException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return "Movie show with such an id doesn't exist";
        }
        Log.logger.info("Movie show with id = " + id + " has been edited successfully");
        return "Movie show has been edited successfully";
    }

    public String deleteMovieShow(int id) {
        try {
            MovieShow movieShow = getMovieShow(id);
            rep.movieShowDao.delete(movieShow);
            List<Ticket> ticketList = rep.ticketDao.readAll().stream().filter(n -> n.getMovieShow()
                    .equals(movieShow.getMovie() + ' ' + movieShow.getShowTime()))
                    .collect(Collectors.toList());
            for (Ticket ticket : ticketList) {
                rep.ticketDao.delete(ticket);
            }
        } catch (NullPointerException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return "Movie show with such an id doesn't exist";
        } catch (OperationException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        }
        Log.logger.info("Movie show with id = " + id + " has been deleted successfully");
        return "Movie show has been deleted successfully";
    }

    private MovieShow getMovieShow(int id) throws OperationException, NoSuchElementException {
        MovieShow movieShow = rep.movieShowDao.readAll().stream().filter(n -> n.getId() == id)
                .findFirst().get();
        return movieShow;
    }
}
