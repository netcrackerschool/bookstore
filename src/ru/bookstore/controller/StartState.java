package ru.bookstore.controller;

import org.apache.commons.cli.CommandLine;
import ru.bookstore.User.UserHelper;
import ru.bookstore.admin.Admin;
import ru.bookstore.view.ConsoleView;

/**
 * Created by Johnny D on 02.11.2014.
 */
public class StartState implements State {

    private CommandLine cl = null;
    private UserHelper usrHelper = null;
    private ConsoleView consoleView;
    private Admin admin = null;

    private StartState() {
    }

    public static StartState getInstance() {
        return new StartState();
    }

    @Override
    public String toString() {
        return "Starting state";
    }

    @Override
    public void analyseCommands(Controller controller) {
        controller.setState(this);
        cl = controller.getCommandLine();
        consoleView = controller.getConsoleView();
        usrHelper = controller.getUsrHelper();
        if (cl.hasOption("help")) {
            controller.formatter.printHelp("BookStore", controller.getOptions());
            controller.setState(this);
            return;
        }

        if (cl.hasOption("state")) {
            consoleView.println(toString());
            return;
        }

        if (cl.hasOption("set")) {
            if (cl.getOptionValue("set").equalsIgnoreCase("user") && usrHelper.createUserSession()) {
                controller.setState(RunningState.getInstance());
                return;
            } else if (cl.getOptionValue("set").equalsIgnoreCase("admin")) {
                admin = Admin.getInstance(consoleView);
                if (admin.createAdminSession()) {
                    controller.setState(RunningAdminState.getInstance());
                    return;
                }
            } else {
                controller.setState(this);
                return;
            }
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
