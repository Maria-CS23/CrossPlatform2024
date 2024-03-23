package version2;

import java.io.Serializable;

public abstract class Human implements Serializable {
    protected String fullName;

    public Human(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}