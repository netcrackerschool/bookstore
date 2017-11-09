package ru.bookstore.view;

import org.apache.log4j.Logger;
import ru.bookstore.POJO.Book;
import ru.bookstore.POJO.BookMark;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Johnny D on 28.10.2014.
 */
public class ConsoleView {
    private PrintStream ps = null;
    private Scanner sc = null;
    private Logger logger = Logger.getLogger("ConsoleView");


    public ConsoleView(InputStream in, PrintStream ps) {
        this.ps = ps;
        sc = new Scanner(in);
    }

    public String[] readLine() {
        String s = sc.nextLine();
        String[] splitted = s.split("\\s+");
        return splitted;
    }

    public String readWholeLine() {
        return sc.nextLine();
    }

    public String readLogin() {
        String regex = "[\\w\\-_]+";
        String s = sc.nextLine();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return s;
        } else {
            logger.error("Incorrect input Login. Try again");
            return null;
        }
    }

    public int readInt() {
        return sc.nextInt();
    }


    public String readPassword() {
        String regex = "\\w{3,20}";
        String s = sc.nextLine();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return s;
        } else {
            logger.error("Incorrect input. Try again");
            return null;
        }
    }

    public void printListBooks(List<Book> list) {
        printf("%-25s", "Name");
        printf("%-25s", "Author");
        println();
        for (Book temp : list) {
            printf("%-25s", temp.getName());
            printf("%-25s", temp.getAuthor());
            println();
        }
    }

    public void printMapBooks(Map<Book, BookMark> map) {
        printf("%-25s", "Name");
        printf("%-25s", "Author");
        printf("%-25s", "Mark");
        println();
        for (Map.Entry<Book, BookMark> temp : map.entrySet()) {
            printf("%-25s", temp.getKey().getName());
            printf("%-25s", temp.getKey().getAuthor());
            if (temp.getValue() == null) {
                printf("%-25s", "none");
            } else {
                printf("%-25d", temp.getValue().getMark());
            }
            println();
        }
    }

    public void printListBooksWithMarks(List<Book> list) {
        printf("%-25s", "Name");
        printf("%-25s", "Author");
        printf("%-25s", "Genre");
        printf("%-25s", "Average Mark");
        println();
        for (Book temp : list) {
            printf("%-25s", temp.getName());
            printf("%-25s", temp.getAuthor());
            printf("%-25s", temp.getGenre());
            printf("%-25s", temp.getAvgMark());
            println();
        }
    }

    public void printClientBooks(List<Book> list) {
        printf("%-25s", "Name");
        printf("%-25s", "Author");
        printf("%-25s", "Date of buy");
        println();
        for (Book temp : list) {
            printf("%-25s", temp.getName());
            printf("%-25s", temp.getAuthor());
            printf("%-25s", temp.getDate().toString());
            println();
        }
    }

    public void println(String s) {
        ps.println(s);
    }

    public void println() {
        ps.println();
    }

    public void printf(String format, String s) {
        ps.printf(format, s);
    }

    public void printf(String format, long s) {
        ps.printf(format, s);
    }

    public void print(String s) {
        ps.print(s);
    }

}
