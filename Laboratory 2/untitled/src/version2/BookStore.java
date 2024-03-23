package version2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BookStore implements Serializable {
    private String name;
    private transient ArrayList<Book> books;

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

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(books.size());
        for (Book book : books) {
            out.writeObject(book.getTitle());
            out.writeObject(book.getAuthors());
            out.writeInt(book.getYearOfPublication());
            out.writeInt(book.getEditionNumber());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int size = in.readInt();
        books = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String title = (String) in.readObject();
            ArrayList<Author> authors = (ArrayList<Author>) in.readObject();
            int yearOfPublication = in.readInt();
            int editionNumber = in.readInt();
            Book book = new Book(title, authors, yearOfPublication, editionNumber);
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