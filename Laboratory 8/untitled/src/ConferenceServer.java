import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConferenceServer extends Remote {
    int registerParticipant(Participant participant) throws RemoteException;
    String exportParticipantsToXML() throws RemoteException;
}