-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: fitnessapp
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `username` varchar(20) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `calories` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES ('zeus','running',60,2,571.725),('zeus','walking',78,0,256.75649999999996),('zeus','swimming',30,0,207.89999999999998),('griid','running',30,0.8,338.52);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercise` (
  `exerciseid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `exercise` varchar(255) NOT NULL,
  `resttime` int DEFAULT NULL,
  PRIMARY KEY (`exerciseid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercise`
--

LOCK TABLES `exercise` WRITE;
/*!40000 ALTER TABLE `exercise` DISABLE KEYS */;
INSERT INTO `exercise` VALUES (1,'zeus','DeadLifts',2),(2,'zeus','PushUps',2),(3,'zeus','Squats',5),(4,'zeus','Plank',3),(5,'zeus','Crunches',3);
/*!40000 ALTER TABLE `exercise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fitness_goals`
--

DROP TABLE IF EXISTS `fitness_goals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fitness_goals` (
  `goal_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `goal_type` enum('weight','distance','calories','frequency') NOT NULL,
  `goal_value` int NOT NULL,
  `deadline` date DEFAULT NULL,
  `status` enum('in_progress','completed','failed') DEFAULT 'in_progress',
  PRIMARY KEY (`goal_id`),
  KEY `username` (`username`),
  CONSTRAINT `fitness_goals_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fitness_goals`
--

LOCK TABLES `fitness_goals` WRITE;
/*!40000 ALTER TABLE `fitness_goals` DISABLE KEYS */;
INSERT INTO `fitness_goals` VALUES (1,'zeus','weight',65,'2025-03-01','in_progress'),(2,'zeus','weight',57,'2025-03-10','in_progress');
/*!40000 ALTER TABLE `fitness_goals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends`
--

DROP TABLE IF EXISTS `friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user1` varchar(50) NOT NULL,
  `user2` varchar(50) NOT NULL,
  `status` enum('pending','accepted','blocked') DEFAULT 'pending',
  PRIMARY KEY (`id`),
  KEY `user1` (`user1`),
  KEY `user2` (`user2`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`user1`) REFERENCES `user` (`username`) ON DELETE CASCADE,
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`user2`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends`
--

LOCK TABLES `friends` WRITE;
/*!40000 ALTER TABLE `friends` DISABLE KEYS */;
INSERT INTO `friends` VALUES (1,'zeus','griid','accepted'),(2,'Vickey','zeus','accepted'),(3,'griid','zeus','accepted');
/*!40000 ALTER TABLE `friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leaderboard`
--

DROP TABLE IF EXISTS `leaderboard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leaderboard` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `total_points` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  CONSTRAINT `leaderboard_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leaderboard`
--

LOCK TABLES `leaderboard` WRITE;
/*!40000 ALTER TABLE `leaderboard` DISABLE KEYS */;
INSERT INTO `leaderboard` VALUES (1,'zeus',50),(2,'griid',100);
/*!40000 ALTER TABLE `leaderboard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reminders`
--

DROP TABLE IF EXISTS `reminders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reminders` (
  `reminder_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `message` text NOT NULL,
  `reminder_date` datetime NOT NULL,
  PRIMARY KEY (`reminder_id`),
  KEY `username` (`username`),
  CONSTRAINT `reminders_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reminders`
--

LOCK TABLES `reminders` WRITE;
/*!40000 ALTER TABLE `reminders` DISABLE KEYS */;
/*!40000 ALTER TABLE `reminders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(8) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `height` double DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `goal` varchar(150) DEFAULT NULL,
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('zeus','zzz','yeswanth',20,'m',164.7,49.5,'build total body mass'),('Vickey','1234','Vickey',21,'m',180,54,'to grow muscle'),('griid','rad','radhesh',21,'m',160,62,'Build muscle');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workout_exercises`
--

DROP TABLE IF EXISTS `workout_exercises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workout_exercises` (
  `id` int NOT NULL AUTO_INCREMENT,
  `planid` int NOT NULL,
  `exerciseid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `planid` (`planid`),
  KEY `exerciseid` (`exerciseid`),
  CONSTRAINT `workout_exercises_ibfk_1` FOREIGN KEY (`planid`) REFERENCES `workoutplans` (`planid`) ON DELETE CASCADE,
  CONSTRAINT `workout_exercises_ibfk_2` FOREIGN KEY (`exerciseid`) REFERENCES `exercise` (`exerciseid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workout_exercises`
--

LOCK TABLES `workout_exercises` WRITE;
/*!40000 ALTER TABLE `workout_exercises` DISABLE KEYS */;
INSERT INTO `workout_exercises` VALUES (1,3,2),(2,3,3),(3,3,4);
/*!40000 ALTER TABLE `workout_exercises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workoutplans`
--

DROP TABLE IF EXISTS `workoutplans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workoutplans` (
  `planid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `planname` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`planid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workoutplans`
--

LOCK TABLES `workoutplans` WRITE;
/*!40000 ALTER TABLE `workoutplans` DISABLE KEYS */;
INSERT INTO `workoutplans` VALUES (1,'zeus','Plan 1','example desc'),(2,'zeus','Grow Muscle','I\'ll put some body muscle!'),(3,'zeus','Increase Mass','I\'ll increase my body mass!');
/*!40000 ALTER TABLE `workoutplans` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-03 20:16:48
