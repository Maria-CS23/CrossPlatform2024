package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class BookStore implements Externalizable {
    private String name;
    private ArrayList<Book> books;

    public BookStore() {}

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
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(books.size());
        for (Book book : books) {
            book.writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        int bookCount = in.readInt();
        books = new ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            Book book = new Book();
            book.readExternal(in);
            books.add(book);
        }
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