package ru.bookstore.User;

import ru.bookstore.DAO.BookDAO;
import ru.bookstore.DAO.BookMarkDAO;
import ru.bookstore.DAO.ClientDAO;
import ru.bookstore.DAO.HistoryDAO;
import ru.bookstore.POJO.Book;
import ru.bookstore.POJO.BookMark;
import ru.bookstore.POJO.Client;
import ru.bookstore.POJO.History;
import ru.bookstore.view.ConsoleView;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Johnny D on 28.10.2014.
 */
public class UserHelper {
    private ConsoleView consoleView;
//    Map<Book, BookMark> mapBooksAndMarks = new TreeMap<Book, BookMark>(new Comparator<Book>() {
//        @Override
//        public int compare(Book o1, Book o2) {
//            return o1.getName().compareTo(o2.getName());
//        }
//    });

    private Client currentClient;

    private UserCart usrCart = new UserCart();

    private ClientDAO clientAccessDB = new ClientDAO();
    private HistoryDAO clientHistoryDB = new HistoryDAO();
    private BookDAO clientBookDB = new BookDAO();
    private BookMarkDAO clientBookMarkDAO = new BookMarkDAO();

    public UserHelper(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    public boolean checkUserValidity(String login, String password) {
        currentClient = clientAccessDB.getClientByLogin(login);
        if (currentClient != null) {
            if (currentClient.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void addToCart(String name) {
        Book book = clientBookDB.getBookByName(name);
        usrCart.addToCart(book);
        consoleView.println("added");
    }

    public void removeFromCart(String name) {
        Book book = clientBookDB.getBookByName(name);
        if (book == null) {
            consoleView.println("There is no such book in cart");
        } else {
            usrCart.removeFromCart(book);
            consoleView.println("removed");
        }
    }

    public UserCart getUsrCart() {
        return usrCart;
    }

    public void buyBooks() {
        for (Book temp : usrCart.getNeededBooks()) {
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(calendar.getTimeInMillis());
            History newHistory = new History(currentClient.getID(), temp.getID(), date);
            clientHistoryDB.addNewHistoryNote(newHistory);
        }
    }

    public void changePassword(String oldPassword) {
        consoleView.print("Enter new password: ");
        String newPassword = consoleView.readPassword();
        clientAccessDB.changeClientPassword(currentClient, oldPassword, newPassword);
    }

    public void changeLogin(String oldPassword) {
        consoleView.print("Enter new login: ");
        String newLogin = consoleView.readLogin();
        clientAccessDB.changeLogin(currentClient, newLogin, oldPassword);
    }

    public boolean createUserSession() {
        String login;
        String password;
        consoleView.print("Login: ");
        login = consoleView.readLogin();
        consoleView.print("Password: ");
        password = consoleView.readPassword();
        if (checkUserValidity(login, password)) {
            consoleView.println("Success!");
            return true;
        } else {
            consoleView.println("Invalid username or password");
            return false;
        }
    }

    public void exitUserSession() {
        usrCart.clearCart();
        currentClient = null;
    }

    public List<Book> getClientBooks() {
        return clientBookDB.getClientBooks(currentClient.getID());
    }

    public List<Book> getAllBooks() {
        return clientBookDB.getAllBooks();
    }

    public void rateBook(String bookName, int mark) {
        long bookID = clientBookDB.getBookByName(bookName).getID();
        long clientID = currentClient.getID();
        if (clientBookMarkDAO.getBookMarkbyClientAndBookID(clientID, bookID) == null) {
            BookMark bm = new BookMark(clientID, bookID, mark);
            clientBookMarkDAO.addNewMark(bm);
        } else {
            consoleView.println("The mark is already exist!");
        }
    }


    public Client getCurrentClient() {
        return currentClient;
    }

}
