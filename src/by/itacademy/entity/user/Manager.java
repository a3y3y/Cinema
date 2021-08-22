package by.itacademy.entity.user;

public class Manager extends User {
    private String access = "manager";

    public Manager(String login, String password) {
        super(login, password);
    }

    public Manager(int id, String login, String password, String access) {
        super(id, login, password, access);
    }

    public Manager(int id, String login, String password, String access, String salt) {
        super(id, login, password, access, salt);
    }

    public Manager() {
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
