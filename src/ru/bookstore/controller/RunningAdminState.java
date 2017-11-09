package ru.bookstore.controller;

import org.apache.commons.cli.CommandLine;
import ru.bookstore.POJO.Book;
import ru.bookstore.admin.Admin;
import ru.bookstore.view.ConsoleView;

import java.util.List;


/**
 * Created by Johnny D on 02.11.2014.
 */
public class RunningAdminState implements State {

    CommandLine cl = null;
    ConsoleView consoleView = null;
    Admin admin = null;

    private RunningAdminState() {
    }

    public static RunningAdminState getInstance() {
        return new RunningAdminState();
    }

    @Override
    public String toString() {
        return "Running state";
    }

    @Override
    public void analyseCommands(Controller controller) {
        cl = controller.getCommandLine();
        consoleView = controller.getConsoleView();
        if (cl.hasOption("help")) {
            controller.formatter.printHelp("BookStore", controller.getOptions());
            controller.setState(this);
            return;
        }

        if (cl.hasOption("add")) {
            admin = Admin.getInstance(consoleView);
            if (cl.getOptionValue("add").equalsIgnoreCase("client")) {
                consoleView.print("Name: ");
                String name = consoleView.readWholeLine();
                consoleView.print("Login: ");
                String login = consoleView.readLogin();
                consoleView.print("Password: ");
                String password = consoleView.readPassword();
                admin.addNewClient(name, login, password);
                consoleView.println("Client was added");
                return;
            }
            if (cl.getOptionValue("add").equalsIgnoreCase("book")) {
                consoleView.print("Name: ");
                String name = consoleView.readWholeLine();
                consoleView.print("Author: ");
                String author = consoleView.readWholeLine();
                consoleView.print("Genre: ");
                String genre = consoleView.readWholeLine();
                admin.addNewBook(name, author, genre);
                consoleView.println("Book was added!");
                return;
            }
        }

        if (cl.hasOption("get")) {
            if (cl.getOptionValue("get").equalsIgnoreCase("allbooks")) {
                admin = Admin.getInstance(consoleView);
                List<Book> listBook = admin.getAllBooks();
                consoleView.printListBooksWithMarks(listBook);
                return;
            } else {
                consoleView.print("Something's going wrong in getting books");
                return;
            }
        }


        if (cl.hasOption("delete")) {
            admin = Admin.getInstance(consoleView);
            if (cl.getOptionValue("delete").equalsIgnoreCase("client")) {
                consoleView.print("Login: ");
                String login = consoleView.readLogin();
                admin.deleteClient(login);
                consoleView.println("Client was deleted");
                return;
            }
            if (cl.getOptionValue("delete").equalsIgnoreCase("book")) {
                consoleView.print("Name: ");
                String name = consoleView.readWholeLine();
                admin.deleteBook(name);
                consoleView.println("Book was deleted!");
                return;
            }
        }

        if (cl.hasOption("state")) {
            consoleView.println(toString());
            return;
        }

        if (cl.hasOption("exit")) {
            controller.getUsrHelper().exitUserSession();
            admin.exitUserSession();
            controller.setState(StartState.getInstance());
            return;
        }

        if (cl.hasOption("close")) {
            System.exit(0);
        }

        if (true) {
            consoleView.println("Invalid command, try again");
            return;
        }
    }
}

