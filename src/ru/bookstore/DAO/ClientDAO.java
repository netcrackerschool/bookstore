package ru.bookstore.DAO;

/**
 * Created by Johnny D on 07.10.2014.
 */

import org.apache.log4j.Logger;
import ru.bookstore.POJO.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO extends BookStoreAccess {

    private static final Logger logger = Logger.getLogger("ClientDAO");

    private static String REQUEST_BY_ID = "SELECT * from CLIENT where ID=?";
    private static String REQUEST_BY_LOGIN = "SELECT * FROM CLIENT WHERE LOGIN =?";
    private static String REQUEST_INSERT_CLIENT = "INSERT INTO CLIENT (ID, NAME, LOGIN, PASSWORD) VALUES(?,?,?,?)";
    private static String CHANGE_PASSWORD_REQUEST = "UPDATE CLIENT SET PASSWORD = ? WHERE ID= ?";
    private static String REMOVE_CLIENT_REQUEST = "DELETE FROM CLIENT WHERE ID=?";


    private static PreparedStatement getClientByIdStmt;

    static {
        try {
            getClientByIdStmt = con.prepareStatement(REQUEST_BY_ID);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising request by ID", e);
        }
    }

    private static PreparedStatement getClientByLoginStmt;

    static {
        try {
            getClientByLoginStmt = con.prepareStatement(REQUEST_BY_LOGIN);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising request by LOGIN", e);
        }
    }

    private static PreparedStatement insertClientStmt;

    static {
        try {
            insertClientStmt = con.prepareStatement(REQUEST_INSERT_CLIENT);
        } catch (SQLException e) {
            logger.error("SQL exception in initialising of request INSERT", e);
        }
    }

    private static PreparedStatement changePassword;

    static {
        try {
            changePassword = con.prepareStatement(CHANGE_PASSWORD_REQUEST);
        } catch (SQLException e) {
            logger.error("SQL exception in initialising of change password request", e);
        }
    }

    private static PreparedStatement removeClient;

    static {
        try {
            removeClient = con.prepareStatement(REMOVE_CLIENT_REQUEST);
        } catch (SQLException e) {
            logger.error("SQL exception in initialising of change password request", e);
        }
    }


    public Client getClientById(long id) {
        Client newClient = null;
        String name;
        String login;
        String password;
        ResultSet resultSet;
        try {
            getClientByIdStmt.setLong(1, id);
            resultSet = getClientByIdStmt.executeQuery();
            resultSet.next();
            name = resultSet.getString("NAME");
            login = resultSet.getString("LOGIN");
            password = resultSet.getString("PASSWORD");
            newClient = new Client(id, name, login, password);
        } catch (SQLException e) {
            logger.error("Cannot get client by ID: ", e);
        }
        return newClient;
    }

    public Client getClientByLogin(String login) {
        Client neededClient = null;

        try {
            getClientByLoginStmt.setString(1, login);
            ResultSet results = getClientByLoginStmt.executeQuery();
            if (results.next()) {
                neededClient = getClientById(results.getLong("ID"));
            } else {
                logger.info("There is no client with login: " + login);
            }
        } catch (SQLException e) {
            logger.info("Incorrect request. Check your input data. ", e);
        }
        return neededClient;
    }

    public Client addNewClient(Client newClient) {
        if (newClient == null) {
            logger.error("Client who must be add cannot be null");
            return null;
        }
        Client neededClient = getClientByLogin(newClient.getLogin());
        try {

            if (neededClient != null) {
                logger.info("This login's Client already exists");
                return neededClient;
            } else {
                try {
                    insertClientStmt.setLong(1, newClient.getID());
                    insertClientStmt.setString(2, newClient.getName());
                    insertClientStmt.setString(3, newClient.getLogin());
                    insertClientStmt.setString(4, newClient.getPassword());
                    insertClientStmt.execute();
                    logger.info("Client has been added");

                    return getClientById(newClient.getID());
                } catch (SQLException e) {
                    logger.error("SQL request insert error: ", e);
                }
            }
        } catch (NullPointerException e1) {
            logger.error("Null pointer client: ", e1);
        }
        return neededClient;

    }

    public void changeClientPassword(Client client, String oldPassword, String newPassword) {
        try {
            if (client.changePassword(oldPassword, newPassword)) {
                try {
                    changePassword.setString(1, newPassword);
                    changePassword.setLong(2, client.getID());
                    changePassword.executeUpdate();
                    logger.info("Password is changed");
                } catch (SQLException e) {
                    logger.debug("SQL EXCEPT in changing password\n", e);
                }
            } else {
                logger.info("Password has not changed. Password incorrect. Invalid access. Try again.");
            }
        } catch (NullPointerException e) {
            logger.error("Client cannot be null");
        }
    }

    public void changeLogin(Client client, String newLogin, String password) {
        try {
            if (client.changeLogin(newLogin, password)) {
                String sql = "UPDATE CLIENT SET LOGIN='" + client.getLogin() + "' WHERE ID=" +
                        client.getID();
                try {
                    PreparedStatement statement = con.prepareStatement(sql);
                    statement.executeUpdate();
                    logger.info("Login is changed");
                } catch (SQLException e) {
                    logger.debug("SQL EXCEPT in changing login\n", e);
                }
            } else {
                logger.info("Login has not changed. Password incorrect. Invalid access. Try again.");
            }
        } catch (NullPointerException e1) {
            logger.error("Client cannot be null");
        }
    }

    public boolean removeClient(long userID) {
        Client neededClient = getClientById(userID);

        try {

            if (neededClient == null) {
                logger.info("This userID does not exists");
                return true;
            } else {
                try {
                    removeClient.setLong(1, userID);
                    removeClient.execute();
                    logger.info("Client has been removed");
                    return true;
                } catch (SQLException e) {
                    logger.error("SQL request remove error: ", e);
                }
            }
        } catch (NullPointerException e1) {
            logger.error("Null pointer client: ", e1);
        }

        return false;
    }
}