package version1;

import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<BookStore> bookStores;
    private ArrayList<BookReader> registeredReaders;

    public Library(String name, ArrayList<BookStore> bookStores, ArrayList<BookReader> registeredReaders) {
        this.name = name;
        this.bookStores = bookStores;
        this.registeredReaders = registeredReaders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BookStore> getBookStores() {
        return bookStores;
    }

    public void setBookStores(ArrayList<BookStore> bookStores) {
        this.bookStores = bookStores;
    }

    public ArrayList<BookReader> getRegisteredReaders() {
        return registeredReaders;
    }

    public void setRegisteredReaders(ArrayList<BookReader> registeredReaders) {
        this.registeredReaders = registeredReaders;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Library name: ").append(name).append("\n");

        for (BookStore bookStore : bookStores) {
            stringBuilder.append(bookStore).append("\n");
        }

        stringBuilder.append("Registered readers:\n");
        for (BookReader reader : registeredReaders) {
            stringBuilder.append(reader).append("\n");
        }

        return stringBuilder.toString();
    }
}