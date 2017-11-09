package ru.bookstore.DAO;

import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Johnny D on 06.10.2014.
 */
abstract class BookStoreAccess {
    private static final Logger logger = Logger.getLogger(BookStoreAccess.class);
    public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    public static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:ORADB10G";
    public static final String LOGIN = "VADIM";
    public static final String PASSWORD = "vadim";
    public static Connection con;

    static {
        try {
            Class.forName(DRIVER);
            try {
                con =  DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
            } catch (SQLException e) {
                logger.fatal("DataBase Access Error");
            }
        } catch (ClassNotFoundException e) {
            logger.fatal("Driver not Found");
        }
    }
}
