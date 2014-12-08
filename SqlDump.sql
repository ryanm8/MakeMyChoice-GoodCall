CREATE DATABASE  IF NOT EXISTS `makemychoicedb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `makemychoicedb`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: makemychoicedb
-- ------------------------------------------------------
-- Server version	5.6.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Poster_ID` int(11) NOT NULL,
  `Question_ID` int(11) NOT NULL,
  `TimeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Comment_Text` varchar(1024) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `idComments_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,1,25,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(2,1,26,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(3,2,1,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(4,2,11,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(5,3,21,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(6,3,22,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(7,4,23,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(8,4,25,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(9,5,27,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(10,5,29,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(11,6,33,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(12,6,31,'2014-12-07 22:46:01','This is a comment, there are many like it, but this one is mine'),(13,1,1,'2014-12-07 22:46:01','Brian Made a Comment'),(14,2,1,'2014-12-07 22:46:01','John doe Made a comment'),(15,3,1,'2014-12-07 22:46:01','Koorosh made a comment'),(16,4,1,'2014-12-07 22:46:01','Derik Made a comment'),(17,5,1,'2014-12-07 22:46:01','Ryan Miller made a comment'),(18,6,1,'2014-12-07 22:46:01','Ryan Mc made a comment'),(19,7,1,'2014-12-07 22:46:01','Chris Made a comment');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Title` varchar(45) NOT NULL,
  `Description` varchar(1024) NOT NULL,
  `Asker_ID` int(11) NOT NULL,
  `Left_Option_Description` varchar(256) NOT NULL,
  `Right_Option_Description` varchar(256) NOT NULL,
  `Number_Left_Votes` int(11) NOT NULL,
  `Number_Right_Votes` int(11) NOT NULL,
  `Due_Date` datetime NOT NULL,
  `TimeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OpenClosed` enum('Open','Closed') NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `idQuestion_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Test Question 1','Test Question Description',2,'Left Option','Right Option',10,11,'2014-12-15 00:00:00','2014-12-07 22:10:36','Open'),(11,'TestQuestion 2','Test Question Description',2,'Left Option 2','Right Option 2',2,2,'2014-12-15 00:00:00','2014-12-07 22:22:45','Closed'),(12,'TestQuestion 3','Test Question Description',2,'Left Option 3','Right Option 3',3,3,'2014-12-15 00:00:00','2014-12-07 22:22:45','Open'),(13,'TestQuestion 4','Test Question Description',2,'Left Option 4','Right Option 4 ',4,4,'2014-12-15 00:00:00','2014-12-07 22:22:45','Closed'),(14,'TestQuestion 5','Test Question Description',2,'Left Option 5','Right Option 5',5,5,'2014-12-15 00:00:00','2014-12-07 22:22:45','Open'),(15,'TestQuestion 6','Test Question Description',2,'Left Option 6','Right Option 6',6,6,'2014-12-15 00:00:00','2014-12-07 22:22:45','Closed'),(16,'TestQuestion 7','Test Question Description',2,'Left Option 7','Right Option 7',7,7,'2014-12-15 00:00:00','2014-12-07 22:22:45','Open'),(17,'TestQuestion 8','Test Question Description',2,'Left Option 8','Right Option 8',8,8,'2014-12-15 00:00:00','2014-12-07 22:22:45','Closed'),(18,'TestQuestion 9','Test Question Description',2,'Left Option 9','Right Option 9',9,9,'2014-12-15 00:00:00','2014-12-07 22:22:45','Open'),(19,'TestQuestion 10','Test Question Description',2,'Left Option 10','Right Option 10',10,10,'2014-12-15 00:00:00','2014-12-07 22:22:45','Closed'),(20,'TestQuestion 11','Test Question Description',2,'Left Option 11','Right Option 11',11,11,'2014-12-15 00:00:00','2014-12-07 22:22:45','Open'),(21,'Ryan Mc\'s Open Question','Ryan Mc\'s Open Description',6,'Ryan Mc\'s Left Option Open','Ryan Mc\'s Right Option Open',60,60,'2014-12-15 00:00:00','2014-12-07 22:30:32','Open'),(22,'Ryan Mc\'s Closed Question','Ryan Mc\'s Closed Description',6,'Ryan Mc\'s Left Option Closed','Ryan Mc\'s Left Option Closed',61,61,'2014-12-15 00:00:00','2014-12-07 22:30:32','Closed'),(25,'Brian\'s Open Question','Brian\'s Open Description',1,'Brian\'s Left Option Open','Brian\'s Right Option Open',10,10,'2014-12-15 00:00:00','2014-12-07 22:30:32','Open'),(26,'Brian\'s Closed Question','Brian\'s Closed Description',1,'Brian\'s Left Option Closed','Brian\'s Right Option Closed',11,11,'2014-12-15 00:00:00','2014-12-07 22:30:32','Closed'),(27,'Koorosh\'s Open Question','Koorosh\'s Open Description',3,'Koorosh\'s Left Option Open','Koorosh\'s Right Option Open',30,30,'2014-12-15 00:00:00','2014-12-08 02:49:07','Open'),(28,'Koorosh\'s Closed Question','Koorosh\'s Closed Description',3,'Koorosh\'s Option Closed','Koorosh\'s Right Option Closed',31,31,'2014-12-15 00:00:00','2014-12-08 02:49:07','Closed'),(29,'Derik\'s Open Question','Derik\'s Open Description',4,'Derik\'s Left Option Open','Derik\'s Right Option Open',40,40,'2014-12-15 00:00:00','2014-12-08 02:49:07','Open'),(30,'Derik\'s Closed Question','Derik\'s Closed Description',4,'Derik\'s Option Closed','Derik\'s Right Option Closed',41,41,'2014-12-15 00:00:00','2014-12-08 02:49:07','Closed'),(31,'Ryan\'s Open Question','Ryan\'s Open Description',5,'Ryan\'s Left Option Open','Ryan\'s Right Option Open',50,50,'2014-12-15 00:00:00','2014-12-08 02:49:07','Open'),(32,'Ryan\'s Closed Question','Ryan\'s Closed Description',5,'Ryan\'s Option Closed','Ryan\'s Right Option Closed',51,51,'2014-12-15 00:00:00','2014-12-08 02:49:07','Closed'),(33,'Chris\'s Open Question','Chris\'s Open Description',7,'Chris\'s Left Option Open','Chris\'s Right Option Open',60,60,'2014-12-15 00:00:00','2014-12-08 02:49:07','Open'),(34,'Chris\'s Closed Question','Chris\'s Closed Description',7,'Chris\'s Option Closed','Chris\'s Right Option Closed',61,61,'2014-12-15 00:00:00','2014-12-08 02:49:07','Closed');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(45) NOT NULL,
  `First_Name` varchar(45) NOT NULL,
  `Last_Name` varchar(45) NOT NULL,
  `Cell_Number` varchar(45) NOT NULL,
  `PID` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'bggreen@vt.edu','Brian','Green','7577841756','bggreen','password'),(2,'bggreen08@gmail.com','John','Doe','7577841756','test','password'),(3,'km419@vt.edu','Koorosh','Massoudi','7033007611','km419','password'),(4,'derikc7@vt.edu','Derik','Carden','1234567890','derikc7','password'),(5,'ryan1025@vt.edu','Ryan','Miller','2404097494','ryan1025','password'),(6,'ryanm8@vt.edu','Ryan','McCloskey','1234567890','ryanm8','password'),(7,'chrisvt@vt.edu','Chris','Hoffman','1234567890','chrisvt','password');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `votedon`
--

DROP TABLE IF EXISTS `votedon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `votedon` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_ID` int(11) NOT NULL,
  `Question_ID` int(11) NOT NULL,
  `LeftRight` enum('Left','Right') NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `votedon`
--

LOCK TABLES `votedon` WRITE;
/*!40000 ALTER TABLE `votedon` DISABLE KEYS */;
/*!40000 ALTER TABLE `votedon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'makemychoicedb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-07 23:37:01
