package version1;

import java.io.Serializable;

public class Author extends Human implements Serializable {
    private static final long serialVersionUID = 1L;

    public Author(String fullName) {
        super(fullName);
    }

    @Override
    public String toString() {
        return "Author: " + super.toString();
    }
}