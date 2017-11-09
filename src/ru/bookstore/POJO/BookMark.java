package ru.bookstore.POJO;

import java.util.UUID;

/**
 * Created by Johnny D on 07.10.2014.
 */
public class BookMark {
    private long id = 0;
    private long client_id = 0;
    private long book_id = 0;



    private int mark;

    public BookMark(long client_id, long book_id, int mark) {
        setID();
        setBook_id(book_id);
        setClient_id(client_id);
        setMark(mark);
    }

    public BookMark(long id, long client_id, long book_id, int mark) {
        setID(id);
        setBook_id(book_id);
        setClient_id(client_id);
        setMark(mark);
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }


    public void setID() {
        UUID uuid = UUID.randomUUID();
        id = -uuid.getLeastSignificantBits();
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
