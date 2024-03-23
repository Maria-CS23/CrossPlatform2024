package version2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VersionTwo {
    public static void serializeObject(String fileName, Object obj) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(obj);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deSerializeObject(String fileName) {
        Object obj = null;
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
            obj = is.readObject();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) {
        System.out.println("Serialization\n");

        Author author1 = new Author("Arthur Conan Doyle");
        Author author2 = new Author("Fyodor Dostoevsky");

        Book book1 = new Book("The Adventures of Sherlock Holmes", new ArrayList<>(List.of(author1)), 1892, 156);
        Book book2 = new Book("Crime and Punishment", new ArrayList<>(List.of(author2)), 1866, 294);

        ArrayList<Book> reader1Books = new ArrayList<>();
        reader1Books.add(book1);

        ArrayList<Book> reader2Books = new ArrayList<>();
        reader2Books.add(book2);

        BookStore bookStore = new BookStore("Book Store", new ArrayList<>(List.of(book1, book2)));

        BookReader reader1 = new BookReader("Margarita Tsvetkova", 78, reader2Books);
        BookReader reader2 = new BookReader("Andrey Vinogradov", 39, reader1Books);

        ArrayList<BookReader> readers = new ArrayList<>(List.of(reader1, reader2));

        Library library = new Library("Library", new ArrayList<>(List.of(bookStore)), readers);

        System.out.println(library);

        serializeObject("library.ser", library);

        Library deserializedLibrary = (Library) deSerializeObject("library.ser");
        System.out.println("Deserialization\n");
        System.out.println(deserializedLibrary);
    }
}