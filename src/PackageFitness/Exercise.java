package PackageFitness;

public class Exercise {
     String username;
     int exerciseId;
     String name;
     int restTime;

    public Exercise(int exerciseID, String username, String name, int restTime) {
        this.exerciseId=exerciseID;
        this.username = username;
        this.name = name;
        this.restTime = restTime;
    }
    public Exercise( String username, String name, int restTime) {
        this.username = username;
        this.name = name;
        this.restTime = restTime;
    }
    // Getters and setters for Exercise class properties
    public String getUsername() {
        return username;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getName() {
        return name;
    }

    public int getRestTime() {
        return restTime;
    }

    @Override
    public String toString() {
        return "Exercise ID: " + exerciseId + ", Name: " + name + ", Rest Time: " + restTime + " Minutes.";
    }
}
