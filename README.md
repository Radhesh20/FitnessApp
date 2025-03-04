# Fitness Tracker (Console-Based Java Application)

## 📌 Overview

The **Fitness Tracker** is a console-based Java application designed to help users monitor their fitness activities, set goals, and track progress. It supports activity tracking, workout plans, reminders, and a leaderboard system.

## 🛠️ Features

- **User Authentication** (Register/Login)
- **Activity Tracking** (Running, Walking, Cycling, Swimming, etc.)
- **Workout Plans** (Create & View Personalized Workouts)
- **Exercise Management** (Add & View Exercises)
- **Fitness Goals** (Set & Track Goals)
- **Leaderboard System** (Earn Points for Activities & Workouts)
- **Friends System** (Send Requests, Accept Friends)
- **Reminders System** (Automated Notifications)

## 💻 Technologies Used

- **Java (JDK 8+)**
- **JDBC for Database Connectivity**
- **MySQL for Data Storage**
- **Scanner for User Input Handling**

## 🚀 Setup & Installation

### Prerequisites

Ensure you have the following installed on your system:

- Java Development Kit (JDK 8 or later)
- MySQL Database
- Any Java-compatible IDE (IntelliJ, Eclipse, VS Code)

### 1️⃣ Clone the Repository

```sh
 git clone https://github.com/Radhesh20/FitnessApp.git
 cd FitnessApp
```

### 2️⃣ Configure Database

1. Open MySQL and create a database:

```sql
CREATE DATABASE fitness_tracker;
```

2. Update database credentials in `Service.java` or configuration file:

```java
String url = "jdbc:mysql://localhost:3306/fitness_tracker";
String user = "your_username";
String password = "your_password";
```

3. Import the provided SQL file to set up tables:

```sh
mysql -u your_username -p fitness_tracker < FitnessApp.sql
```

## 🗄️ Database Schema

The application uses a MySQL database with the following tables:

### `users`
```sql
CREATE TABLE users (
  `username` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) UNIQUE,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### `activity`
```sql
CREATE TABLE activity (
  `username` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `calories` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### `exercise`
```sql
CREATE TABLE exercise (
  `exerciseid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `exercise` varchar(255) NOT NULL,
  `resttime` int DEFAULT NULL,
  PRIMARY KEY (`exerciseid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
```

### `goals`
```sql
CREATE TABLE goals (
  `goalid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `goaltype` varchar(50) NOT NULL,
  `goalvalue` varchar(50) NOT NULL,
  `deadline` DATE NOT NULL,
  PRIMARY KEY (`goalid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```

### `friends`
```sql
CREATE TABLE friends (
  `id` int NOT NULL AUTO_INCREMENT,
  `user1` varchar(20) NOT NULL,
  `user2` varchar(20) NOT NULL,
  `status` ENUM('pending', 'accepted') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
```

## 🎮 How to Use

1. **Run the Application** and Login/Register.
2. Use the **Menu Options** to:
   - Log an activity
   - View activity history
   - Add & view workout plans
   - Set fitness goals
   - Manage friends
   - View leaderboard
3. Exit the application when done.

## 🔥 Sample Commands

- **Tracking an Activity**

```
Choose an option:
1. Track an Activity
Enter Activity Type: Running
Enter Duration: 30
Enter Distance: 5
Activity Logged Successfully! Calories Burned: 300
```

- **Setting a Fitness Goal**

```
Enter Goal Type: weight
Enter Goal Value: 70kg
Enter Deadline: 2025-06-01
Goal set successfully!
```

## 📌 Future Enhancements

- Convert to **Web Application** (Spring Boot + React.js)
- Integrate **Mobile App Support** (Flutter)
- Add **AI-based Workout Recommendations**

## 📜 License

This project is licensed under the MIT License. See the [`LICENSE`](LICENSE) file for details.

## 📞 Contact

For any issues or suggestions, please open an issue on the [GitHub Repository](https://github.com/Radhesh20/FitnessApp/issues) or reach out via email at `radheshkumar2004@gmail.com`.

