package by.itacademy.repository.sql;


import by.itacademy.constant.Constant;
import by.itacademy.entity.user.Admin;
import by.itacademy.entity.user.Manager;
import by.itacademy.entity.user.User;
import by.itacademy.entity.user.Viewer;
import by.itacademy.exception.OperationException;

import by.itacademy.repository.Dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static by.itacademy.util.log.AbstractConnection.getConnection;

public class UserDao implements Dao<User> {
    private List<User> rep = new ArrayList<>();

    @Override
    public boolean create(User user) throws OperationException {
        try (PreparedStatement stmt = getConnection()
                .prepareStatement(Constant.USER_CREATE)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getAccess());
            stmt.setString(4, user.getSalt());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OperationException("Can't create such a user", e);
        }
        return false;
    }

    @Override
    public User update(String param, User user) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.USER_UPDATE)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, param);
            stmt.execute();
        } catch (SQLException e) {
            throw new OperationException("Can't update user", e);
        }
        return user;
    }

    @Override
    public User read(int id) throws OperationException {
        User user = null;
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.USER_READ)) {
            stmt.setInt(1, id);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            rs.next();
            if (rs.getString("access").equals("viewer")) {
                user = new Viewer(rs.getInt("id"), rs.getString("login")
                        , rs.getString("password")
                        , rs.getString("access"), rs.getString("salt"));
            }
            if (rs.getString("access").equals("admin")) {
                user = new Admin(rs.getInt("id"), rs.getString("login")
                        , rs.getString("password"), rs.getString("access")
                        , rs.getString("salt"));
            }
            if (rs.getString("access").equals("manager")) {
                user = new Manager(rs.getInt("id"), rs.getString("login")
                        , rs.getString("password"), rs.getString("access")
                        , rs.getString("salt"));
            }
            rs.close();
        } catch (SQLException | NullPointerException e) {
            throw new OperationException("This user doesn't exist", e);
        }
        return user;
    }


    @Override
    public boolean delete(User user) throws OperationException {
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.USER_DELETE)) {
            stmt.setString(1, user.getLogin());
            if (stmt.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OperationException("Can not delete user", e);
        }
        return false;
    }

    @Override
    public List<User> readAll() throws OperationException {
        List<User> list = new ArrayList<>();
        try (PreparedStatement stmt = getConnection().prepareStatement(Constant.USER_READ_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                if (rs.getString("access").equals("viewer")) {
                    User user = new Viewer(rs.getInt("id"), rs.getString("login"),
                            rs.getString("password"), rs.getString("access")
                            , rs.getString("salt"));
                    list.add(user);
                }
                if (rs.getString("access").equals("manager")) {
                    User user = new Manager(rs.getInt("id"), rs.getString("login"),
                            rs.getString("password"), rs.getString("access")
                            , rs.getString("salt"));
                    list.add(user);
                }
                if (rs.getString("access").equals("admin")) {
                    User user = new Admin(rs.getInt("id"), rs.getString("login"),
                            rs.getString("password"), rs.getString("access")
                            , rs.getString("salt"));
                    list.add(user);
                }
            }

        } catch (SQLException e) {
            throw new OperationException(e);
        }
        return list;
    }
}
