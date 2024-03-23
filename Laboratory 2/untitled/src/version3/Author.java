package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Author extends Human implements Externalizable {
    public Author() {}

    public Author(String fullName) {
        super(fullName);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(fullName);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        fullName = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "Author: " + super.toString();
    }
}