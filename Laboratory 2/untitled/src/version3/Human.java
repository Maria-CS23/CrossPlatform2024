package version3;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class Human implements Externalizable {
    protected String fullName;

    public Human() {}

    public Human(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = fullName;
    }

    public abstract void writeExternal(ObjectOutput out) throws IOException;

    public abstract void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;

    @Override
    public String toString() {
        return fullName;
    }
}