package ru.bookstore.controller;

import org.apache.commons.cli.*;
import ru.bookstore.DAO.ClientDAO;
import ru.bookstore.POJO.BookMark;
import ru.bookstore.POJO.Client;
import ru.bookstore.POJO.History;
import ru.bookstore.User.UserHelper;
import ru.bookstore.view.ConsoleView;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnny D on 28.10.2014.
 */

public class Controller {
    private Logger logger = Logger.getLogger("Controller.java");

    private ConsoleView consoleView = null;
    private Options options = new Options();
    private CommandLineParser parser = new BasicParser();
    private CommandLine cl = null;
    HelpFormatter formatter = new HelpFormatter();

    private UserHelper usrHelper = null;

    private State state = StartState.getInstance();


    public Controller() {
        this.consoleView = new ConsoleView(System.in, System.out);
        usrHelper = new UserHelper(this.consoleView);
        options.addOption("exit", false, "Leaving User's session");
        options.addOption("set", true, "set session");
        options.addOption("change", true, "Changing <arg = password> or <arg = username>");
        options.addOption("close", false, "Stop running an application");
        options.addOption("help", false, "Possible commands");
        options.addOption("state", false, "Current state");
        options.addOption("get", true, "get some info about <arg>");
        options.addOption("put", false, "put to cart");
        options.addOption("show", true, "show your <arg>=cart or <arg>=your buy's history");
        options.addOption("remove", false, "remove book from your cart");
        options.addOption("clear", false, "clear all your cart");
        options.addOption("buy", false, "buy all books in your cart");
        options.addOption("add", true, "adding something new");
        options.addOption("delete", true, "delete <arg = book> or <arg = client>");
        options.addOption("rate", false, "rate some book");

        formatter.printHelp("BookStore", options);
    }


    public UserHelper getUsrHelper() {
        return usrHelper;
    }

    public CommandLine getCommandLine() {
        return this.cl;
    }

    public ConsoleView getConsoleView() {
        return consoleView;
    }

    public Options getOptions() {
        return options;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void parseCommand() {
        try {
            System.out.print("BookStore: ");
            cl = parser.parse(this.options, consoleView.readLine());
            state.analyseCommands(this);
        } catch (ParseException e) {
            consoleView.println("Invalid command, try again");
            logger.info("Bad Command parser");
        }
    }

    public static void main(String[] args) {
        Controller cntr = new Controller();
        while (true) {
            cntr.parseCommand();
        }
    }
}
