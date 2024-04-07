package Task4;

public class NameInfo {
    String name;
    String gender;
    int count;
    int rank;

    public NameInfo(String name, String gender, int count, int rank) {
        this.name = name;
        this.gender = gender;
        this.count = count;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "NameInfo{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", count=" + count +
                ", rank=" + rank +
                '}';
    }
}