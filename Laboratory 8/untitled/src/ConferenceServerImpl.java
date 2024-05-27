import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JTextArea;

public class ConferenceServerImpl extends UnicastRemoteObject implements ConferenceServer {
    private ParticipantStorage storage;
    private JTextArea textArea;

    protected ConferenceServerImpl(JTextArea textArea) throws RemoteException {
        this.storage = new ParticipantStorage();
        this.textArea = textArea;
    }

    @Override
    public int registerParticipant(Participant participant) throws RemoteException {
        storage.addParticipant(participant);
        updateTextArea();
        return storage.getParticipants().size();
    }

    @Override
    public String exportParticipantsToXML() throws RemoteException {
        try {
            return XMLUtils.toXML(storage.getParticipants());
        } catch (Exception e) {
            throw new RemoteException("Error exporting to XML", e);
        }
    }

    public void loadParticipants(List<Participant> participants) throws RemoteException {
        storage.setParticipants(participants);
        updateTextArea();
    }

    private void updateTextArea() {
        List<Participant> participants = storage.getParticipants();
        textArea.setText("");

        for (int i = 0; i < participants.size(); i++) {
            Participant participant = participants.get(i);
            textArea.append((i + 1) + ". Name: " + participant.getName() + ", ");
            textArea.append("Family Name: " + participant.getFamilyName() + ", ");
            textArea.append("Place of Work: " + participant.getPlaceOfWork() + ", ");
            textArea.append("Report Title: " + participant.getReportTitle() + ", ");
            textArea.append("Email: " + participant.getEmail() + "\n");
        }
    }
}