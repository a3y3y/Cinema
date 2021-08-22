package by.itacademy.entity.user;

public class Admin extends User {
    private String access = "admin";

    public Admin(String login, String password) {
        super(login, password);
    }

    public Admin(int id, String login, String password, String access) {
        super(id, login, password, access);
    }

    public Admin(int id, String login, String password, String access, String salt) {
        super(id, login, password, access, salt);
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getAccess() {
        return access;
    }
}
