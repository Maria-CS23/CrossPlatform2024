package exercise2.updWork;

import java.io.Serializable;
import java.net.InetAddress;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private InetAddress address;
    private int port;

    public User() {
    }

    public User(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "User[address=" + address + ", port=" + port + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return address.equals(other.address) && port == other.port;
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + port;
        return result;
    }
}