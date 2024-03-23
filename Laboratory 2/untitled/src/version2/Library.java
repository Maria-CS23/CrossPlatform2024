package version2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable {
    private String name;
    private transient ArrayList<BookStore> bookStores;
    private transient ArrayList<BookReader> registeredReaders;

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

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        out.writeInt(bookStores.size());
        for (BookStore bookStore : bookStores) {
            out.writeObject(bookStore);
        }

        out.writeInt(registeredReaders.size());
        for (BookReader reader : registeredReaders) {
            out.writeObject(reader);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        int numBookStores = in.readInt();
        bookStores = new ArrayList<BookStore>();
        for (int i = 0; i < numBookStores; i++) {
            BookStore bookStore = (BookStore) in.readObject();
            bookStores.add(bookStore);
        }

        int numReaders = in.readInt();
        registeredReaders = new ArrayList<BookReader>();
        for (int i = 0; i < numReaders; i++) {
            BookReader reader = (BookReader) in.readObject();
            registeredReaders.add(reader);
        }
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