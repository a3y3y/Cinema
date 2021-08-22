package by.itacademy.service;

import by.itacademy.entity.MovieShow;
import by.itacademy.entity.Ticket;
import by.itacademy.exception.OperationException;
import by.itacademy.repository.sql.RepositorySql;
import by.itacademy.util.log.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class TicketOperation {
    private RepositorySql rep;

    public TicketOperation(RepositorySql rep) {
        this.rep = rep;
    }

    public String refundTicket(String login, int ticketId) {
        try {
            Ticket ticket = rep.ticketDao.readAll().stream().filter(n->n.getViewer().equals(login) &&
                    n.getId() == ticketId).findFirst().get();
            if(ticket.getAvailability().equals("unavailable")) {
                ticket.setViewer(" ");
                ticket.setAvailability("available");
                rep.ticketDao.update(String.valueOf(ticketId), ticket);
            }else return "User didn't buy this ticket";
        } catch (OperationException | NoSuchElementException e) {
            Log.logger.log(Level.WARNING,e.getMessage(),e);
            return "User doesn't have such a ticket";
        }
        return "Refund has been issued";
    }

    public String buyTicket(String login, int ticketId, List<Ticket> ticketList) {
        try {
            Ticket ticket = ticketList.stream().filter(n->n.getId() == ticketId).findFirst().get();
            if (ticket.getAvailability().equals("available")) {
                ticket.setViewer(login);
                ticket.setAvailability("unavailable");
                rep.ticketDao.update(String.valueOf(ticketId), ticket);
            } else {
                return "This ticket has been already bought";
            }
        } catch (OperationException | NoSuchElementException e) {
            Log.logger.log(Level.WARNING,e.getMessage(),e);
            return "Can't find ticket with such id";
        }
        return "You have bought ticket";
    }

    public List<Ticket> getTicketListForMovieShow(int movieShowId) {
        List<Ticket> ticketList = new ArrayList<>();
        MovieShow movieShow;
        try {
            movieShow = rep.movieShowDao.read(movieShowId);
            String s = movieShow.getMovie() + " " + movieShow.getShowTime();
            ticketList = rep.ticketDao.readAll().stream()
                    .filter(n -> n.getMovieShow().equals(s)).collect(Collectors.toList());
        } catch (OperationException | NullPointerException e) {
            Log.logger.log(Level.WARNING,e.getMessage(),e);
        }
        return ticketList;
    }

    public List<Ticket> getTicketListForViewer(String login) {
        List<Ticket> ticketList = new ArrayList<>();
        try {
            ticketList = rep.ticketDao.readAll().stream().filter(n -> n.getViewer().equals(login))
                    .collect(Collectors.toList());
        } catch (OperationException e) {
            Log.logger.log(Level.WARNING,e.getMessage(),e);
        }
        return ticketList;
    }

}

