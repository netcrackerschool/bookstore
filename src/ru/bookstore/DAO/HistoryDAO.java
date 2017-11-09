package ru.bookstore.DAO;

import org.apache.log4j.Logger;
import ru.bookstore.POJO.History;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Johnny D on 21.10.2014.
 */
public class HistoryDAO extends BookStoreAccess {
    private static final Logger logger = Logger.getLogger("HistoryDAO");

    private static String REQUEST_BY_ID = "SELECT * from HISTORY where ID = ?";
    private static String REQUEST_BY_CLIENT_ID = "SELECT * FROM HISTORY WHERE CLIENT_ID = ?";
    private static String REQUEST_BY_BOOK_ID = "SELECT * FROM HISTORY WHERE BOOK_ID = ?";
    private static String REQUEST_BY_DATE = "SELECT * FROM HISTORY  WHERE BUY_DATE = ?";
    private static String REQUEST_INSERT = "INSERT INTO HISTORY (ID, BOOK_ID, CLIENT_ID, BUY_DATE) VALUES(?,?,?,?)";
    private static String DELETE_BY_USER_ID = "DELETE FROM HISTORY WHERE CLIENT_ID=?";
    private static String DELETE_BY_BOOK_ID = "DELETE FROM HISTORY WHERE BOOK_ID=?";

    private static PreparedStatement getHistoryByIDStmt;

    static {
        try {
            getHistoryByIDStmt = con.prepareStatement(REQUEST_BY_ID);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising of getting History by ID", e);
        }
    }

    private static PreparedStatement getHistoryByClientIDStmt;

    static {
        try {
            getHistoryByClientIDStmt = con.prepareStatement(REQUEST_BY_CLIENT_ID);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising of getHistory by Client_id: ", e);
        }
    }

    private static PreparedStatement getHistoryByBookIDStmt;

    static {
        try {
            getHistoryByBookIDStmt = con.prepareStatement(REQUEST_BY_BOOK_ID);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising of getHistory by Book_id: ", e);
        }
    }

    private static PreparedStatement getHistoryByDate;

    static {
        try {
            getHistoryByDate = con.prepareStatement(REQUEST_BY_DATE);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising of get History by mark", e);
        }
    }

    private static PreparedStatement insertHistory;

    static {
        try {
            insertHistory = con.prepareStatement(REQUEST_INSERT);
        } catch (SQLException e) {
            logger.error("SQL Exception in initialising of get History by mark", e);
        }
    }

    private static PreparedStatement deleteByBookID;

    static {
        try {
            deleteByBookID = con.prepareStatement(DELETE_BY_BOOK_ID);
        } catch (SQLException e) {
            logger.error("SQL Exception in init of deleting bookmark by bookID", e);
        }
    }

    private static PreparedStatement deleteByUserID;

    static {
        try {
            deleteByUserID = con.prepareStatement(DELETE_BY_USER_ID);
        } catch (SQLException e) {
            logger.error("SQL Exception in init of deleting bookmark by userID", e);
        }
    }


    public History getHistoryByID(long id) {
        History newHistory = null;
        long clientID;
        long bookID;
        Date buyDate;

        ResultSet resultSet;
        try {
            getHistoryByIDStmt.setLong(1, id);
            resultSet = getHistoryByIDStmt.executeQuery();
            resultSet.next();
            clientID = resultSet.getLong("CLIENT_ID");
            bookID = resultSet.getLong("BOOK_ID");
            buyDate = resultSet.getDate("BUY_DATE");
            newHistory = new History(clientID, bookID, buyDate);
        } catch (SQLException e) {
            logger.info("There is no history with id: " + id);
        }
        return newHistory;
    }

    public List<History> getHistoryByClientID(long client_id) {
        List<History> listHistory = new ArrayList<History>();
        History neededHistory = null;
        try {
            getHistoryByClientIDStmt.setLong(1, client_id);
            ResultSet results = getHistoryByClientIDStmt.executeQuery();
            while (results.next()) {
                neededHistory = getHistoryByID(results.getLong("ID"));
                listHistory.add(neededHistory);
            }
        } catch (SQLException e) {
            logger.debug("There are no Histories of client with id: " + client_id);
        }
        return listHistory;
    }

    public List<History> getHistoryByBookID(long book_id) {
        List<History> listHistory = new ArrayList<History>();
        try {
            getHistoryByBookIDStmt.setLong(1, book_id);
            ResultSet results = getHistoryByBookIDStmt.executeQuery();
            while (results.next()) {
                History neededBook;
                neededBook = getHistoryByID(results.getLong("ID"));
                listHistory.add(neededBook);
            }
        } catch (SQLException e) {
            logger.debug("There is no bookMark with book_id: " + book_id);
        }
        return listHistory;
    }

    public List<History> getHistoryByDate(Date buyDate) {
        ArrayList<History> listHistory = new ArrayList<History>();
        try {
            getHistoryByDate.setDate(1, buyDate);
            ResultSet results = getHistoryByDate.executeQuery();
            while (results.next()) {
                History neededHistory;
                neededHistory = getHistoryByID(results.getLong("ID"));
                listHistory.add(neededHistory);
            }
        } catch (SQLException e) {
            logger.debug("There is no bookMark with mark: " + buyDate);
        }
        return listHistory;
    }

    public void addNewHistoryNote(History newHistory) {
        History ifNoteExists = getHistoryByID(newHistory.getID());
        if (ifNoteExists == null) {
            try {
                insertHistory.setLong(1, newHistory.getID());
                insertHistory.setLong(2, newHistory.getBook_id());
                insertHistory.setLong(3, newHistory.getClient_id());
                insertHistory.setDate(4, newHistory.getBuyDate());
                insertHistory.execute();
            } catch (SQLException e) {
                logger.error("SQL request insert error", e);
            }
        } else {
            logger.debug("Mark already exists with ID: " + newHistory.getID());
        }
    }



//    public void deleteByUserID(long userID) {
//        try {
//            deleteByUserID.setLong(1, userID);
//            deleteByUserID.execute();
//        } catch (SQLException e) {
//            logger.error("SQL arror in deleting deleteByUserID() method in HistoryDAO", e);
//        }
//    }
//
//    public void deleteByBookID(long bookID) {
//        try {
//            deleteByBookID.setLong(1, bookID);
//            deleteByBookID.execute();
//        } catch (SQLException e) {
//            logger.error("SQL arror in deleting deleteByBookID() method in HistoryDAO", e);
//        }
//    }
}
