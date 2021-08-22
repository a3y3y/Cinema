package by.itacademy.repository.sql;

import by.itacademy.constant.Constant;
import by.itacademy.entity.MovieShow;
import by.itacademy.exception.OperationException;
import by.itacademy.repository.Dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static by.itacademy.util.log.AbstractConnection.getConnection;

public class MovieShowDao implements Dao<MovieShow> {
    @Override
    public boolean create(MovieShow movieShow) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.MOVIE_SHOW_CREATE)) {
            stmt.setString(1, movieShow.getMovie());
            stmt.setString(2, String.valueOf(movieShow.getShowTime()));
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OperationException("Can't create such a movie show", e);
        }
        return false;
    }

    @Override
    public MovieShow update(String param, MovieShow movieShow) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.MOVIE_SHOW_UPDATE)) {
            stmt.setString(1, movieShow.getMovie());
            stmt.setString(2, String.valueOf(movieShow.getShowTime()));
            stmt.setInt(3, Integer.parseInt(param));
            stmt.execute();
        } catch (SQLException | NumberFormatException e) {
            throw new OperationException("Can't update movie show", e);
        }
        return movieShow;
    }


    @Override
    public MovieShow read(int id) throws OperationException {
        MovieShow movieShow;
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.MOVIE_SHOW_READ)) {
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            String[] time = rs.getString("showTime").split("-|T|:");
            int[] t = Arrays.stream(time).mapToInt(Integer::parseInt).toArray();
            movieShow = new MovieShow(rs.getInt("id"), rs.getString("movie")
                    , LocalDateTime.of(t[0], t[1], t[2], t[3], t[4]));
            rs.close();
        } catch (SQLException e) {
            throw new OperationException("This movie show doesn't exist", e);
        }
        return movieShow;
    }

    @Override
    public boolean delete(MovieShow movieShow) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.MOVIE_SHOW_DELETE)) {
            stmt.setInt(1, movieShow.getId());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OperationException("Can't delete movie show",e);
        }
        return false;
    }

    @Override
    public List<MovieShow> readAll() throws OperationException {
        List<MovieShow> list = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.MOVIE_SHOW_READ_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String[] time = rs.getString("showTime").split("-|T|:");
                int[] t = Arrays.stream(time).mapToInt(Integer::parseInt).toArray();
                MovieShow movieShow = new MovieShow(rs.getInt("id")
                        , rs.getString("movie")
                        , LocalDateTime.of(t[0], t[1], t[2], t[3], t[4]));
                list.add(movieShow);
            }
        } catch (SQLException e) {
            throw new OperationException(e);
        }
        return list;
    }
}
