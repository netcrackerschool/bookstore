package ru.bookstore.admin;

import ru.bookstore.DAO.BookDAO;
import ru.bookstore.DAO.BookMarkDAO;
import ru.bookstore.DAO.ClientDAO;
import ru.bookstore.DAO.HistoryDAO;
import ru.bookstore.POJO.Book;
import ru.bookstore.POJO.Client;
import ru.bookstore.view.ConsoleView;

import java.util.List;

/**
 * Created by Johnny D on 04.11.2014.
 */
public class Admin {

    private ConsoleView consoleView;
    private Client currentAdmin;

    protected ClientDAO clientAccessDB = new ClientDAO();
    protected BookDAO clientBookDB = new BookDAO();

    private Admin(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public Admin() {
    }

    public static Admin getInstance(ConsoleView consoleView) {
        return new Admin(consoleView);
    }

    public boolean createAdminSession() {
        String login;
        String password;
        consoleView.print("Login: ");
        login = consoleView.readLogin();
        consoleView.print("Password: ");
        password = consoleView.readPassword();
        if (checkAdminValidity(login, password)) {
            consoleView.println("Success!");
            return true;
        } else {
            consoleView.println("Invalid username or password");
            return false;
        }
    }

    public boolean checkAdminValidity(String login, String password) {
        currentAdmin = clientAccessDB.getClientByLogin(login);
        if ((currentAdmin != null) && (currentAdmin.getID() == (long) 1)) {
            if (currentAdmin.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Book> getAllBooks() {
        return clientBookDB.getAllBooks();
    }

    public void exitUserSession() {
        currentAdmin = null;
    }

    public void addNewBook(String name, String author, String genre) {
        Book newBook = new Book(name, author, genre);
        clientBookDB.addNewBook(newBook);
    }

    public void addNewClient(String name, String login, String password) {
        Client newClient = new Client(name, login, password);
        clientAccessDB.addNewClient(newClient);
    }

    public void deleteClient(String login) {
        long userID = clientAccessDB.getClientByLogin(login).getID();
        clientAccessDB.removeClient(userID);
    }

    public void deleteBook(String name) {
        long bookID = clientBookDB.getBookByName(name).getID();
        clientBookDB.removeBook(bookID);
    }

}
