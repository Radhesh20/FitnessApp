package PackageFitness;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Services class for login and registering purpose
public class Service{
    //"loginUser" method definition to check login details
    public static boolean loginUser(String username, String password){
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try(
                Connection conn = DataBase.connect();
                PreparedStatement stmt = conn != null ? conn.prepareStatement(sql) : null
        ) {
            assert stmt != null;
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //"registerUser" method definition to register new user details
    public static void registerUser(User user){
        String sql = "INSERT INTO User (username, password, name, age, gender, weight, height, goal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = DataBase.connect();
                PreparedStatement stmt = conn != null ? conn.prepareStatement(sql) : null
        ){
            assert stmt != null;
            stmt.setString(1, user.username);
            stmt.setString(2, user.password);
            stmt.setString(3, user.name);
            stmt.setInt(4, user.age);
            stmt.setString(5, user.gender);
            stmt.setDouble(6, user.weight);
            stmt.setDouble(7, user.height);
            stmt.setString(8, user.goal);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //getting user weight for calculating calories burned
    public static double getUserWeight(String username) {
        String sql = "SELECT weight FROM User WHERE username = ?";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("weight");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    //method to calculate calories burned
    public static double calculateCalories(String type, int duration, double distance, double weight) {
        double met = switch (type.toLowerCase()) {
            case "running" -> 10.0 + (distance * 0.5);
            case "walking" -> 3.8;
            case "cycling" -> 7.5 + (distance * 0.2);
            case "swimming" -> 8.0;
            default -> 5.0;
        };
        return (met * 3.5 * weight / 200) * duration;
    }
    //fetching activity history from db for displaying purposes
    public static List<Activity> getActivityHistory(String username) {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM Activity WHERE username = ?";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    activities.add(new Activity(
                            rs.getString("username"),
                            rs.getString("type"),
                            rs.getInt("duration"),
                            rs.getDouble("distance"),
                            rs.getDouble("calories")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
    //fetching workout plans from db for displaying purposes
    public static List<Workout> getWorkoutPlans(String username) {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT planid, planname, description FROM workoutplans WHERE username = ?";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int planId = rs.getInt("planid");
                    String planname = rs.getString("planname");
                    String description = rs.getString("description");

                    List<Integer> exerciseIds = getExercisesForWorkout(planId);
                    workouts.add(new Workout(username, exerciseIds, planname, description));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workouts;
    }

    private static List<Integer> getExercisesForWorkout(int planId) {
        List<Integer> exerciseIds = new ArrayList<>();
        String sql = "SELECT exerciseid FROM workout_exercises WHERE planid = ?";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, planId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    exerciseIds.add(rs.getInt("exerciseid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseIds;
    }

    public static List<Exercise> getExercisePlans() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercise ";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                //stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    exercises.add(new Exercise(
                            rs.getInt("exerciseID"),
                            rs.getString("username"),
                            //rs.getInt("exerciseid"),
                            rs.getString("exercise"),
                            rs.getInt("resttime")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }
    private static void insertWorkoutExercises(int planId, List<Integer> exerciseIds) {
        String sql = "INSERT INTO workout_exercises (planid, exerciseid) VALUES (?, ?)";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int exerciseId : exerciseIds) {
                    stmt.setInt(1, planId);
                    stmt.setInt(2, exerciseId);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //insert new workout plans for inputting into db
    public static void addWorkout(Workout workout) {
        String sql = "INSERT INTO workoutplans (username, planname, description) VALUES (?, ?, ?)";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, workout.getUsername());
                stmt.setString(2, workout.getPlanname());
                stmt.setString(3, workout.getDescription());
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int planId = generatedKeys.getInt(1);
                    insertWorkoutExercises(planId, workout.getExerciseIds());
                    System.out.println("Workout plan added successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean addExercise(Exercise exercise) {
        String sql = "INSERT INTO exercise (username, exercise, resttime) VALUES (?, ?, ?)";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                assert stmt != null;
                stmt.setString(1, exercise.getUsername());
                stmt.setString(2, exercise.getName());
                stmt.setInt(3, exercise.getRestTime());

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;  // Return true if successful

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //insert new activities for tracking purpose into db
    public static void trackActivity(Activity activity) {
        String sql = "INSERT INTO Activity (username, type, duration, distance, calories) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, activity.username);
                stmt.setString(2, activity.type);
                stmt.setInt(3, activity.duration);
                stmt.setDouble(4, activity.distance);
                stmt.setDouble(5, activity.calories);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setFitnessGoal(String username, String goalType, int goalValue, Date deadline) {
        String sql = "INSERT INTO fitness_goals (username, goal_type, goal_value, deadline) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, goalType);
                stmt.setInt(3, goalValue);
                stmt.setDate(4, new Date(deadline.getTime()));
                stmt.executeUpdate();
                System.out.println("Goal set successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendReminders() {
        String sql = "SELECT username, goal_type, goal_value, deadline FROM fitness_goals WHERE deadline >= NOW() AND status = 'in_progress'";
        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String goalType = rs.getString("goal_type");
                    int goalValue = rs.getInt("goal_value");
                    System.out.println("Reminder for " + username + ": Keep pushing towards your " + goalType + " goal (" + goalValue + ")!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        String sql = "SELECT username, total_points FROM leaderboard ORDER BY total_points DESC";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    leaderboard.add(new LeaderboardEntry(rs.getString("username"), rs.getInt("total_points")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }

    public static void updateLeaderboard(String username, int points) {
        String sql = "INSERT INTO leaderboard (username, total_points) VALUES (?, ?) ON DUPLICATE KEY UPDATE total_points = total_points + ?";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setInt(2, points);
                stmt.setInt(3, points);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void sendFriendRequest(String sender, String receiver) {
        String sql = "INSERT INTO friends (user1, user2, status) VALUES (?, ?, 'pending')";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, sender);
                stmt.setString(2, receiver);
                stmt.executeUpdate();
                System.out.println("Friend request sent to " + receiver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<FriendRequest> getFriendRequests(String username) {
        List<FriendRequest> requests = new ArrayList<>();
        String sql = "SELECT user1 FROM friends WHERE user2 = ? AND status = 'pending'";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    requests.add(new FriendRequest(rs.getString("user1"), username));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static void acceptFriendRequest(String username, String friendUsername) {
        String sql = "UPDATE friends SET status = 'accepted' WHERE user1 = ? AND user2 = ? AND status = 'pending'";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, friendUsername);
                stmt.setString(2, username);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Friend request from " + friendUsername + " accepted!");
                } else {
                    System.out.println("No pending request found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getFriendsList(String username) {
        List<String> friends = new ArrayList<>();
        String sql = "SELECT user2 FROM friends WHERE user1 = ? AND status = 'accepted' UNION SELECT user1 FROM friends WHERE user2 = ? AND status = 'accepted'";

        try (Connection conn = DataBase.connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, username);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    friends.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }



}
