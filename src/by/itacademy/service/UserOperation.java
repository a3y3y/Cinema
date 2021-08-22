package by.itacademy.service;

import by.itacademy.entity.Ticket;
import by.itacademy.entity.user.Manager;
import by.itacademy.entity.user.User;
import by.itacademy.entity.user.Viewer;
import by.itacademy.exception.OperationException;
import by.itacademy.repository.sql.RepositorySql;
import by.itacademy.util.log.Log;
import by.itacademy.util.log.security.Password;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.stream.Collectors;


public class UserOperation {
    private RepositorySql rep;

    public UserOperation(RepositorySql rep) {
        this.rep = rep;
    }

    public String userCheck(String login, String password, String access) {
        try {
            User user = rep.userDao.readAll().stream().filter(n -> n.getLogin().equals(login) &&
                    n.getAccess().equals(access)).findFirst().get();
            byte[] salt = Password.getByteFromString(user.getSalt());
            byte[] encryptedPassword = Password.getByteFromString(user.getPassword());
            if (Password.authenticate(password, encryptedPassword, salt))
                return "You have passed verification";
        } catch (OperationException | UnsupportedEncodingException |
                NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return "Can't login, contact the administration for more info";
        }catch (NoSuchElementException e){
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return "User with this login, password and access doesn't exist";
        }
        return "User with this login, password and access doesn't exist";
    }

    public boolean userDuplicateLoginCheck(String login) {
        try {
            if (rep.userDao.readAll().stream().anyMatch(n -> n.getLogin().equals(login))) {
                return false;
            } else return true;
        } catch (OperationException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        }
        return true;
    }

    public User getUser(String login) {
        User user = null;
        try {
            user = rep.userDao.readAll().stream().filter(n -> n.getLogin().equals(login))
                    .findFirst().get();
        } catch (OperationException | NoSuchElementException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        }
        return user;
    }

    public String addUser(String login, String password, String access) {
        try {
            User user = null;
            byte[] salt = Password.generateSalt();
            byte[] encryptedPassword = Password.getEncryptedPassword(password, salt);
            String encPasswordString = Password.getStringFromByte(encryptedPassword);
            String saltString = Password.getStringFromByte(salt);
            if (access.equals("viewer")) {
                user = new Viewer(login, encPasswordString);
                user.setSalt(saltString);
            }
            if (access.equals("manager")) {
                user = new Manager(login, encPasswordString);
                user.setSalt(saltString);
            }

            if (rep.userDao.create(user)) {
                return "User have been registered";
            }
            return "User hasn't been created";
        } catch (OperationException | NullPointerException | NoSuchAlgorithmException |
                InvalidKeySpecException | UnsupportedEncodingException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return "Can't create user";
        }
    }

    public String deleteUser(String login) {
        try {
            if (rep.userDao.delete(getUser(login))) {
                List<Ticket> ticketList =  rep.ticketDao.readAll().stream()
                        .filter(n->n.getViewer().equals(login))
                        .map(n->{n.setViewer(" ");n.setAvailability("available");return n;})
                        .collect(Collectors.toList());
                for (Ticket ticket:ticketList) {
                    rep.ticketDao.update(String.valueOf(ticket.getId()),ticket);
                }
                Log.logger.info("User " + login + " has been deleted");
                return "User has been deleted";
            }
        } catch (OperationException | NullPointerException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return "User doesn't exist";
        }
        Log.logger.info("User " + login + " doesn't exist");
        return "User doesn't exist";
    }

    public String updateUser(String login, String newLogin, String newPassword) {
        User user = getUser(login);
        if (user == null) {
            Log.logger.info("User " + login + " doesn't exist");
            return "User doesn't exist";
        }
        if (user.getAccess().equals("admin")) {
            Log.logger.info("User " + login + " is admin, it can't be edited");
            return "This user is admin, you can't edit it";
        } else {
            try {
                byte[] salt = Password.generateSalt();
                byte[] encryptedPassword = Password.getEncryptedPassword(newPassword, salt);
                String encPasswordString = Password.getStringFromByte(encryptedPassword);
                String saltString = Password.getStringFromByte(salt);
                user.setLogin(newLogin);
                user.setPassword(encPasswordString);
                user.setSalt(saltString);
                rep.userDao.update(login, user);
            } catch (OperationException | NoSuchAlgorithmException | InvalidKeySpecException |
                    UnsupportedEncodingException e) {
                Log.logger.log(Level.WARNING, e.getMessage(), e);
            }
        }
        Log.logger.info("User " + login + " has been updated");
        return "User has been updated";
    }

    public boolean checkViewer(String login) {
        try {
            rep.userDao.readAll().stream().filter(n -> n.getLogin().equals(login) &&
                    n.getAccess().equals("viewer")).findFirst().get();
            return true;
        } catch (OperationException | NoSuchElementException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
            return false;
        }
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        try {
            userList = rep.userDao.readAll().stream().filter(n -> n.getAccess().equals("viewer") ||
                    n.getAccess().equals("manager")).collect(Collectors.toList());
        } catch (OperationException e) {
            Log.logger.log(Level.WARNING, e.getMessage(), e);
        }
        return userList;
    }
}