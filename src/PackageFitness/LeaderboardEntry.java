package PackageFitness;

public class LeaderboardEntry {
    String username;
    int totalPoints;

    public LeaderboardEntry(String username, int totalPoints) {
        this.username = username;
        this.totalPoints = totalPoints;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public String toString() {
        return username + " - " + totalPoints + " points";
    }
}
