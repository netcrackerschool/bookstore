package ru.bookstore.POJO;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by Johnny D on 07.10.2014.
 */
public class History {
    private long id = 0;
    private long client_id = 0;
    private long book_id = 0;
    private Date buyDate = null;

    public History(long client_id, long book_id, Date buyDate) {
        setClient_id(client_id);
        setBook_id(book_id);
        setBuyDate(buyDate);
        setID();
    }

    public History(long id, long client_id, long book_id, Date buyDate) {
        setClient_id(client_id);
        setBook_id(book_id);
        setBuyDate(buyDate);
        setID(id);
    }

    public long getID() {
        return id;
    }

    public void setID() {
        UUID uuid = UUID.randomUUID();
        id = -uuid.getLeastSignificantBits();
    }

    public void setID(long id) {
        this.id = id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
