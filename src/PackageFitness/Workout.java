package PackageFitness;

import java.util.List;

public class Workout {
    List<Integer> exerciseIds;  // Changed from int to List<Integer>
    String username, planname, description;

    public Workout(String username, List<Integer> exerciseIds, String planname, String description) {
        this.username = username;
        this.exerciseIds = exerciseIds;
        this.planname = planname;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public String getPlanname() {
        return planname;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getExerciseIds() {
        return exerciseIds;
    }

    @Override
    public String toString() {
        return "Workout: " + planname + ", Description: " + description + ", Exercises: " + exerciseIds;
    }
}
