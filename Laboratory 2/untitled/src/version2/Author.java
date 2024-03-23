package version2;

public class Author extends Human {
    public Author(String fullName) {
        super(fullName);
    }

    @Override
    public String toString() {
        return "Author: " + super.toString();
    }
}
