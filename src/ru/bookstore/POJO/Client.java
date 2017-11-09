package ru.bookstore.POJO;

import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * Created by Johnny D on 07.10.2014.
 */
public class Client {
    private long id = 0;
    private String name = null;
    private String login = null;
    private String password = null;
    private int hash = 0;
    private static final Logger logger = Logger.getLogger("Client");

    public Client() {

    }

    public Client(String name, String login, String password) {
        setName(name);
        setLogin(login);
        setPassword(password);
        id = getID();
        hash = hashCode();
    }

    public Client(long id, String name, String login, String password) {
        setName(name);
        setLogin(login);
        setPassword(password);
        this.id = id;
        hash = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (!login.equals(client.login)) return false;
        if (!name.equals(client.name)) return false;
        if (!password.equals(client.password)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    public void setID() {
        UUID uuid = UUID.randomUUID();
        id = -uuid.getLeastSignificantBits();
    }

    public long getID() {
        if (id == 0) {
            setID();
        }
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if ((password != null) && (this.password == null)) {
            this.password = password;
        } else if (password == null) {
            throw new NullPointerException();
        } else {
            logger.debug("Password was settled early");
        }
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true;
        } else {
            return false;
        }
    }

    public boolean changeLogin(String newLogin, String password) {
        if (this.password.equals(password)) {
            this.login = newLogin;
            return true;
        } else {
            return false;
        }
    }
}
