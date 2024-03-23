package version1;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private ArrayList<Author> authors;
    private int yearOfPublication;
    private int editionNumber;

    public Book(String title, ArrayList<Author> authors, int yearOfPublication, int editionNumber) {
        this.title = title;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
        this.editionNumber = editionNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        StringBuilder authorsString = new StringBuilder();
        for (Author author : authors) {
            authorsString.append(author).append(", ");
        }
        if (authorsString.length() > 0) {
            authorsString.delete(authorsString.length() - 2, authorsString.length());
        }
        return "\nBook: " +
                "\nTitle: " + title +
                "\n" + authorsString.toString() +
                "\nYear of publication: " + yearOfPublication +
                "\nEdition number: " + editionNumber;
    }
}