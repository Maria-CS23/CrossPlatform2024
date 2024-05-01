package tcpWork.models;

import java.util.ArrayList;

public class MetroCardBank {
    private final ArrayList<MetroCard> store;

    public MetroCardBank() {
        store = new ArrayList<>();
    }

    public ArrayList<MetroCard> getStore() {
        return store;
    }

    public int findMetroCard(String serNum) {
        for (int i = 0; i < store.size(); i++) {
            MetroCard card = store.get(i);
            if (card != null && card.getSerNum().equals(serNum)) {
                return i;
            }
        }
        return -1;
    }

    public int numCards() {
        return store.size();
    }

    public void addCard(MetroCard newCard) {
        if (findMetroCard(newCard.getSerNum()) != -1) {
            System.out.println("The card is already registered!");
            return;
        }

        store.add(newCard);
    }

    public boolean removeCard(String serNum) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    public boolean addMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            MetroCard card = store.get(index);
            card.setBalance(card.getBalance() + money);
            return true;
        }
        return false;
    }

    public boolean getMoney(String serNum, double money) {
        int index = findMetroCard(serNum);

        if (index == -1) return false;

        MetroCard card = store.get(index);
        if (card.getBalance() < money) return false;

        card.setBalance(card.getBalance() - money);
        return true;
    }

    public double getBalance(String serialNumber) {
        int index = findMetroCard(serialNumber);
        if (index != -1) {
            MetroCard card = store.get(index);
            return card.getBalance();
        }
        return -1;
    }

    public String displayCardInfo(String serNum) {
        StringBuilder info = new StringBuilder();
        int index = findMetroCard(serNum);
        if (index == -1) return "Card not found";

        MetroCard card = store.get(index);
        info.append("\nCard information:\n")
                .append(card.toString())
                .append("\n");

        User user = card.getUser();
        info.append("User information:\n")
                .append(user.toString())
                .append("\n");

        return info.toString();
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("List of MetroCards:");
        for (MetroCard c : store) {
            buf.append("\n\n").append(c);
        }
        return buf.toString();
    }
}