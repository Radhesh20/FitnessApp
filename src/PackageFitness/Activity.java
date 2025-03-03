package PackageFitness;

public class Activity {
    String username, type;
    int duration;
    double distance, calories;
    public Activity(String username, String type, int duration, double distance, double calories) {
        this.username=username;
        this.type=type;
        this.duration=duration;
        this.distance=distance;
        this.calories=calories;
    }

    @Override
    public String toString() {
        return "Activity: " + type + ", Duration: " + duration + " min, Distance: " + distance + " km, Calories Burned: " + calories;
    }
}
