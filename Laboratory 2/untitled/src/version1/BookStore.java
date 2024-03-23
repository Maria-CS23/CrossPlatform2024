package version1;

import java.io.Serializable;
import java.util.ArrayList;

public class BookStore implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Book> books;

    public BookStore(String name, ArrayList<Book> books) {
        this.name = name;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BookStore name: ").append(name).append("\n");

        for (Book book : books) {
            stringBuilder.append(book).append("\n");
        }

        return stringBuilder.toString();
    }
}