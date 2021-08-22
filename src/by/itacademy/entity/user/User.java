package by.itacademy.entity.user;

public abstract class User {
        private int id;
        private String login;
        private String password;
        private String access;
        private String salt;



        public User(String login, String password) {
                this.login = login;
                this.password = password;
        }

        public User(int id, String login, String password, String access, String salt) {
                this.id = id;
                this.login = login;
                this.password = password;
                this.access = access;
                this.salt = salt;
        }

        public User(int id, String login, String password, String access) {
                this.id = id;
                this.login = login;
                this.password = password;
                this.access = access;
        }

        public User() {

        }

        @Override
        public String toString() {
                return access +
                        ", id = " + id +
                        ", login = '" + login + '\'';
        }

        public String getLogin() {
                return login;
        }

        public String getPassword() {
                return password;
        }

        public String getAccess() {
                return access;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getSalt() {
                return salt;
        }

        public void setSalt(String salt) {
                this.salt = salt;
        }
}
