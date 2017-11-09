package ru.bookstore.User;

import ru.bookstore.POJO.Book;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import ru.bookstore.view.ConsoleView;

/**
 * Created by Johnny D on 04.11.2014.
 */
public class UserCart {
    private Logger logger = Logger.getLogger("UserCart");
    private List<Book> neededBooks = new ArrayList<Book>();

    public void addToCart(Book book) {
        neededBooks.add(book);
    }

    public void removeFromCart(Book book) {
        if (neededBooks.contains(book)) {
            neededBooks.remove(book);
        } else {
            logger.info("There is no such book to remove");
        }
    }

    public List<Book> getNeededBooks() {
        return neededBooks;
    }


    public void showCart(ConsoleView consoleView) {
        if (!neededBooks.isEmpty()) {
            consoleView.printListBooks(neededBooks);
        } else {
            consoleView.println("Cart is empty");
        }
    }

    public void clearCart() {
        neededBooks.clear();
    }
}
