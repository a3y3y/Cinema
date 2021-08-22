package by.itacademy.repository.sql;

import by.itacademy.constant.Constant;
import by.itacademy.entity.Ticket;
import by.itacademy.exception.OperationException;
import by.itacademy.repository.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.itacademy.util.log.AbstractConnection.getConnection;

public class TicketDao implements Dao<Ticket> {

    public boolean create(Ticket ticket) throws OperationException {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.TICKET_CREATE)) {
            stmt.setString(1, ticket.getViewer());
            stmt.setString(2, ticket.getMovieShow());
            stmt.setInt(3, ticket.getSeat());
            stmt.setInt(4, ticket.getPrice());
            stmt.setString(5, ticket.getAvailability());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OperationException("Can't create ticket", e);
        }
        return false;
    }

    @Override
    public Ticket update(String param, Ticket ticket) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.TICKET_UPDATE)) {
            stmt.setString(1, ticket.getViewer());
            stmt.setString(2, ticket.getAvailability());
            stmt.setString(3, ticket.getMovieShow());
            stmt.setInt(4, Integer.parseInt(param));
            stmt.execute();
        } catch (SQLException | NumberFormatException e) {
            throw new OperationException("Can't update ticket", e);
        }
        return ticket;
    }

    @Override
    public Ticket read(int id) throws OperationException {
        Ticket ticket = new Ticket();
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.TICKET_READ)) {
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            ticket.setId(rs.getInt("id"));
            ticket.setViewer(rs.getString("viewer"));
            ticket.setMovieShow(rs.getString("movieShow"));
            ticket.setSeat(rs.getInt("seat"));
            ticket.setPrice(rs.getInt("price"));
            ticket.setAvailability(rs.getString("availability"));
            rs.close();
        } catch (SQLException e) {
            throw new OperationException("This ticket doesn't exist", e);
        }
        return ticket;
    }

    @Override
    public boolean delete(Ticket ticket) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.TICKET_DELETE)) {
            stmt.setInt(1, ticket.getId());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OperationException("Can not delete ticket", e);
        }
        return false;
    }

    @Override
    public List<Ticket> readAll() throws OperationException {
        List<Ticket> list = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.TICKET_READ_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("id"), rs.getString("viewer")
                        , rs.getString("movieShow")
                        , rs.getInt("seat"), rs.getInt("price")
                        , rs.getString("availability"));
                list.add(ticket);
            }
        } catch (SQLException e) {
            throw new OperationException(e);
        }
        return list;
    }

}
