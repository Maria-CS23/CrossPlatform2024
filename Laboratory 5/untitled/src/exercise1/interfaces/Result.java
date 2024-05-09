package exercise1.interfaces;

import java.io.Serializable;

public interface Result extends Serializable {
    Object output();
    double scoreTime();
}