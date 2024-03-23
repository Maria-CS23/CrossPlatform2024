package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class Library implements Externalizable {
    private String name;
    private ArrayList<BookStore> bookStores;
    private ArrayList<BookReader> registeredReaders;

    public Library() {}

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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(bookStores);
        out.writeObject(registeredReaders);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        bookStores = (ArrayList<BookStore>) in.readObject();
        registeredReaders = (ArrayList<BookReader>) in.readObject();
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