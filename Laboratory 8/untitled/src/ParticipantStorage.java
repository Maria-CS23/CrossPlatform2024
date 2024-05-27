import java.util.ArrayList;
import java.util.List;

public class ParticipantStorage {
    private List<Participant> participants;

    public ParticipantStorage() {
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}