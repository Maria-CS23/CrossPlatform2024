package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class BookReader extends Human implements Externalizable {
    private int registrationNumber;
    private ArrayList<Book> receivedBooks;

    public BookReader() {}

    public BookReader(String fullName, int registrationNumber, ArrayList<Book> receivedBooks) {
        super(fullName);
        this.registrationNumber = registrationNumber;
        this.receivedBooks = receivedBooks;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public ArrayList<Book> getReceivedBooks() {
        return receivedBooks;
    }

    public void setReceivedBooks(ArrayList<Book> receivedBooks) {
        this.receivedBooks = receivedBooks;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(fullName);
        out.writeInt(registrationNumber);
        out.writeObject(receivedBooks);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        fullName = (String) in.readObject();
        registrationNumber = in.readInt();
        receivedBooks = (ArrayList<Book>) in.readObject();
    }

    @Override
    public String toString() {
        StringBuilder booksString = new StringBuilder();
        for (Book book : receivedBooks) {
            booksString.append(book.getTitle()).append(", ");
        }
        if (booksString.length() > 0) {
            booksString.delete(booksString.length() - 2, booksString.length());
        }
        return "\nBook reader: " +
                "\n" + fullName +
                "\nRegistration number: " + registrationNumber +
                "\nReceived books: " + booksString.toString();
    }
}