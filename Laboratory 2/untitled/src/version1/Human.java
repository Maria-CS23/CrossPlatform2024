package version1;

import java.io.Serializable;

public abstract class Human implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String fullName;

    public Human(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameName() {
        return fullName;
    }

    public void setFullNameName(String name) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}