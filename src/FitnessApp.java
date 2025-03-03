import PackageFitness.*;

import java.sql.Date;
import java.util.*;

public class FitnessApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("----Fitness Tracker App----");
        System.out.println("Enter Username: ");
        String username = sc.next();
        System.out.println("Enter Password: ");
        String password = sc.next();

        if (Service.loginUser(username, password)) {
            System.out.println("Welcome back, " + username + "!");
        } else {
            System.out.println("No User Found!!\nRegistering... " + username);
            sc.nextLine();
            System.out.println("Enter your Name: ");
            String name = sc.nextLine();
            System.out.println("Enter your Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter your Gender: ");
            String gender = sc.next();
            System.out.println("Enter your Height (in cm): ");
            double height = sc.nextDouble();
            System.out.println("Enter your Weight (in kg): ");
            double weight = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter your Fitness Goal: ");
            String goal = sc.nextLine();

            User newUser = new User(username, password, name, age, gender, height, weight, goal);
            Service.registerUser(newUser);
            System.out.println("Registration successful!");
        }

        userMenu(username);
        sc.close();
    }

    public static void userMenu(String username) {
        Scanner sc = new Scanner(System.in);
        double userWeight = Service.getUserWeight(username);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Track an Activity");
            System.out.println("2. View Activity History");
            System.out.println("3. Add Workout Plan");
            System.out.println("4. View Workout Plans");
            System.out.println("5. Add Exercise");
            System.out.println("6. View Exercises");
            System.out.println("7. Set Fitness Goal");
            System.out.println("8. Send Remainders");
            System.out.println("9. Leaderboard");
            System.out.println("10. Friends");
            System.out.println("11. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Activity Type (Running, Walking, Cycling, Swimming): ");
                    String type = sc.next();
                    System.out.println("Enter Duration (minutes): ");
                    int duration = sc.nextInt();
                    double distance = 0;
                    if (type.equalsIgnoreCase("Running") || type.equalsIgnoreCase("Cycling")) {
                        System.out.println("Enter Distance (km): ");
                        distance = sc.nextDouble();
                    }
                    double calories = Service.calculateCalories(type, duration, distance, userWeight);
                    Activity activity = new Activity(username, type, duration, distance, calories);
                    Service.trackActivity(activity);
                    int pointsEarned = (int) (calories / 10);
                    Service.updateLeaderboard(username, pointsEarned);
                    System.out.println("Activity Logged Successfully! Calories Burned: " + calories);
                    System.out.println("You earned " + pointsEarned + " points!");
                    break;
                case 2:
                    List<Activity> activities = Service.getActivityHistory(username);
                    if (activities.isEmpty()) {
                        System.out.println("No activities found.");
                    } else {
                        System.out.println("Your Activity History:");
                        for (Activity act : activities) {
                            System.out.println(act);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter Plan Name: ");
                    String planName = sc.nextLine().trim();
                    System.out.println("Enter Plan Description: ");
                    String description = sc.nextLine().trim();

                    List<Exercise> exercises = Service.getExercisePlans();
                    if (exercises.isEmpty()) {
                        System.out.println("No exercises available.");
                        break;
                    }

                    System.out.println("Available Exercises:");
                    for (int i = 0; i < exercises.size(); i++) {
                        System.out.println((i + 1) + ". " + exercises.get(i).getName());
                    }

                    System.out.println("Enter exercise numbers (comma-separated): ");
                    String input = sc.nextLine().trim();
                    List<Integer> exerciseIds = new ArrayList<>();

                    try {
                        for (String indexStr : input.split(",")) {
                            int index = Integer.parseInt(indexStr.trim()) - 1;
                            exerciseIds.add(exercises.get(index).getExerciseId());
                        }
                    } catch (NumberFormatException | IndexOutOfBoundsException e) {
                        System.out.println("Invalid input.");
                        break;
                    }

                    Workout workout = new Workout(username, exerciseIds, planName, description);
                    Service.addWorkout(workout);
                    break;

                case 4:
                    List<Workout> workoutPlans = Service.getWorkoutPlans(username);
                    if (workoutPlans.isEmpty()) {
                        System.out.println("No Workout Plans found.");
                    } else {
                        System.out.println("Your Workout Plans:");
                        for (Workout work : workoutPlans) {
                            System.out.println(work);
                        }
                    }
                    System.out.println("Did you complete a workout? (yes/no)");
                    String completed = sc.nextLine().trim();
                    if (completed.equalsIgnoreCase("yes")) {
                        Service.updateLeaderboard(username, 100);
                        System.out.println("Congrats! You've earned 100 points.");
                    }
                    break;
                case 5:
                    System.out.println("Enter Exercise Name: ");
                    String exerciseName = sc.nextLine().trim();

                    System.out.println("Enter Rest Time (minutes): ");
                    int restTime;

                    try {
                        restTime = sc.nextInt();
                        sc.nextLine(); // Consume newline left-over
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number for rest time.");
                        sc.nextLine(); // Clear invalid input
                        break;
                    }

                    Exercise exercise = new Exercise( username, exerciseName, restTime);
                    boolean success = Service.addExercise(exercise);

                    if (success) {
                        System.out.println("Exercise added successfully!");
                    } else {
                        System.out.println("Failed to add exercise. Please try again.");
                    }
                    break;

                case 6:
                    List<Exercise> exerciseList = Service.getExercisePlans();
                    if (exerciseList.isEmpty()) {
                        System.out.println("No Exercises found.");
                    } else {
                        System.out.println("Your Exercises:");
                        for (Exercise ex : exerciseList) {
                            System.out.println(ex);
                        }
                    }
                    break;
                case 7:
                    System.out.println("Set Fitness Goal");
                    System.out.println("Enter Goal Type (weight, distance, calories, frequency): ");
                    String goalType = sc.next();
                    System.out.println("Enter Goal Value: ");
                    int goalValue = sc.nextInt();
                    System.out.println("Enter Deadline (YYYY-MM-DD): ");
                    String deadlineStr = sc.next();

                    try {
                        Date deadline = java.sql.Date.valueOf(deadlineStr);
                        Service.setFitnessGoal(username, goalType, goalValue, deadline);
                        Service.updateLeaderboard(username, 50);
                        System.out.println("Goal set! You've earned 50 points.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format.");
                    }
                    break;

                case 8:
                    System.out.println("Sending reminders...");
                    Service.sendReminders();
                    break;
                case 9:
                    System.out.println("Leaderboard:");
                    List<LeaderboardEntry> leaderboard = Service.getLeaderboard();
                    for (LeaderboardEntry entry : leaderboard) {
                        System.out.println(entry.getUsername() + " - " + entry.getTotalPoints() + " points");
                    }
                    break;

                case 10:
                    System.out.println("Manage Friends:");
                    System.out.println("1. Send Friend Request");
                    System.out.println("2. View Friend Requests");
                    System.out.println("3. Accept Friend Request");
                    System.out.println("4. View Friends List");
                    int friendChoice = sc.nextInt();
                    sc.nextLine();

                    switch (friendChoice) {
                        case 1:
                            System.out.println("Enter friend's username: ");
                            String friendUsername = sc.nextLine();
                            Service.sendFriendRequest(username, friendUsername);
                            break;

                        case 2:
                            List<FriendRequest> requests = Service.getFriendRequests(username);
                            for (FriendRequest request : requests) {
                                System.out.println(request.getSender() + " sent you a friend request.");
                            }
                            break;

                        case 3:
                            System.out.println("Enter username of the request to accept: ");
                            String acceptUsername = sc.nextLine();
                            Service.acceptFriendRequest(username, acceptUsername);
                            break;

                        case 4:
                            List<String> friends = Service.getFriendsList(username);
                            System.out.println("Your Friends:");
                            for (String friend : friends) {
                                System.out.println(friend);
                            }
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                    break;

                case 11:
                    System.out.println("Exiting Fitness Tracker. Stay healthy!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
