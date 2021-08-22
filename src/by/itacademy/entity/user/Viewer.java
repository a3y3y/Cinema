package by.itacademy.entity.user;

public class Viewer extends User {
    private String access = "viewer";

    public Viewer(String login, String password) {
        super(login, password);
    }

    public Viewer(int id, String login, String password, String access, String salt) {
        super(id, login, password, access,salt);
    }

    public Viewer(int id, String login, String password, String access) {
        super(id, login, password, access);
    }

    public Viewer() {
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
