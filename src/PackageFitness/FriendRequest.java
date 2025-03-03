package PackageFitness;

public class FriendRequest {
    String sender;
    String receiver;

    public FriendRequest(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return sender + " sent you a friend request.";
    }
}
