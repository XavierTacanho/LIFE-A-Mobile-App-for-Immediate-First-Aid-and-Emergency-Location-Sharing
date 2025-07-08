-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: life
-- ------------------------------------------------------
-- Server version	5.7.31

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` int(11) NOT NULL AUTO_INCREMENT,
  `street` varchar(255) NOT NULL,
  `city` varchar(100) NOT NULL,
  `town_hall` varchar(100) NOT NULL,
  `postal_code` varchar(10) NOT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `contact_id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `courses_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `description` mediumtext NOT NULL,
  `duration` int(11) NOT NULL,
  PRIMARY KEY (`courses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency`
--

DROP TABLE IF EXISTS `emergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emergency` (
  `emergency_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `description` text,
  `status` enum('Pendente','Em Andamento','Concluída') NOT NULL DEFAULT 'Pendente',
  PRIMARY KEY (`emergency_id`),
  KEY `fk_emergency_user` (`user_id`),
  CONSTRAINT `fk_emergency_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency`
--

LOCK TABLES `emergency` WRITE;
/*!40000 ALTER TABLE `emergency` DISABLE KEYS */;
INSERT INTO `emergency` VALUES (5,17,'2025-06-13 16:19:43','Emergência reportada via botão SOS','Pendente'),(6,17,'2025-06-13 16:19:47','Emergência reportada via botão SOS','Pendente'),(7,17,'2025-06-13 16:20:04','Emergência reportada via botão SOS','Pendente'),(8,17,'2025-06-13 16:30:31','teste','Pendente'),(9,17,'2025-06-13 16:31:56','teste','Pendente'),(10,17,'2025-06-13 16:36:44','teste','Pendente'),(11,17,'2025-06-13 16:36:52','teste','Pendente'),(12,17,'2025-06-13 16:38:04','teste','Pendente'),(13,17,'2025-06-13 16:38:14','teste','Pendente'),(14,17,'2025-06-13 16:39:01','teste','Pendente'),(15,17,'2025-06-13 16:39:22','teste','Pendente'),(16,17,'2025-06-13 16:39:25','teste','Pendente'),(17,17,'2025-06-13 16:39:39','teste','Pendente'),(18,17,'2025-06-13 16:41:00','teste','Pendente'),(19,17,'2025-06-13 16:41:02','teste','Pendente'),(20,17,'2025-06-13 16:41:28','teste','Pendente'),(21,17,'2025-06-13 16:41:31','teste','Pendente'),(22,17,'2025-06-13 16:43:12','teste','Pendente'),(23,17,'2025-06-13 16:43:19','teste','Pendente'),(24,17,'2025-06-13 16:44:26','teste','Pendente'),(25,17,'2025-06-13 16:44:29','teste','Pendente'),(26,17,'2025-06-13 16:45:26','teste','Pendente'),(27,17,'2025-06-13 16:45:45','teste','Pendente'),(28,17,'2025-06-13 16:46:25','Emergência reportada via botão SOS','Pendente'),(29,17,'2025-06-13 17:40:42','Emergência reportada via botão SOS','Pendente'),(30,17,'2025-06-13 17:40:47','Emergência reportada via botão SOS','Pendente'),(31,17,'2025-06-13 17:40:51','Emergência reportada via botão SOS','Pendente'),(32,21,'2025-06-13 17:42:07','Emergência reportada via botão SOS','Pendente'),(33,17,'2025-06-16 09:37:03','Emergência reportada via botão SOS','Pendente'),(34,17,'2025-06-17 10:24:42','Emergência reportada via botão SOS','Pendente'),(35,17,'2025-06-17 16:23:26','Emergência reportada via botão SOS','Pendente'),(36,22,'2025-06-17 16:26:44','Emergência reportada via botão SOS','Pendente'),(37,22,'2025-06-17 16:27:19','Emergência reportada via botão SOS','Pendente'),(38,22,'2025-06-17 16:28:58','Emergência reportada via botão SOS','Pendente'),(39,17,'2025-06-18 16:24:39','Emergência reportada via botão SOS','Pendente');
/*!40000 ALTER TABLE `emergency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emergency_localization`
--

DROP TABLE IF EXISTS `emergency_localization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emergency_localization` (
  `localization_id` int(11) NOT NULL AUTO_INCREMENT,
  `emergency_id` int(11) NOT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL,
  `street_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`localization_id`),
  KEY `fk_localization_emergency` (`emergency_id`),
  CONSTRAINT `fk_localization_emergency` FOREIGN KEY (`emergency_id`) REFERENCES `emergency` (`emergency_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergency_localization`
--

LOCK TABLES `emergency_localization` WRITE;
/*!40000 ALTER TABLE `emergency_localization` DISABLE KEYS */;
INSERT INTO `emergency_localization` VALUES (1,27,40.000000,40.000000,'teste rua'),(2,28,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(3,29,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(4,30,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(5,31,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(6,32,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(7,33,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(8,34,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(9,35,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(10,36,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(11,37,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(12,38,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal'),(13,39,40.393127,-7.541690,'R. dos Amieiros Verdes 6260, 6260 Manteigas, Portugal');
/*!40000 ALTER TABLE `emergency_localization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_history`
--

DROP TABLE IF EXISTS `medical_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_history` (
  `history_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `conditions` text,
  `allergies` text,
  `medications` text,
  PRIMARY KEY (`history_id`),
  KEY `fk_medical_history_user` (`user_id`),
  CONSTRAINT `fk_medical_history_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_history`
--

LOCK TABLES `medical_history` WRITE;
/*!40000 ALTER TABLE `medical_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `medical_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (11,'asd','asd','2025-05-02','asd','asd','$2y$10$/J9FxSt9lxy.KQ2GJtLlzOBPYxzUvJlJ9iBogLZFVHld7SCFa1UYG','2025-05-02 15:54:05'),(12,'ola','ola','2025-05-02','ola','ola','$2y$10$FbJwH/Vb83J3d5NjcHPiWO4WMuCkcDu/4.J0jZkINVAyLRi6T4/xu','2025-05-02 16:43:44'),(13,'xavier','tacanho','2001-10-10','xavier','xavier.email.pt','$2y$10$KXn6q9qbNrU8XCrFpJeBpO.qz3.6Q5FBMuetAccOpbvLYG2hfcKpS','2025-05-02 16:44:48'),(14,'Diogo','Santos','2001-06-28','diogosantos','Diogo.Santosarrobaemail.pt','$2y$10$7VK9qjMSTQFXNO4NY3vWoe8B64d6dnECT1qv1y65QTroCJF9SqvW2','2025-05-12 16:42:44'),(15,'Gabriel','Rasteiro','2002-12-23','gr','gabriel.rasteiroemail.com','$2y$10$Xo/ixYnOuFnjznv6P8JP0uPCMesZ3F7qrud5MJInEVcluVX2HpqhK','2025-05-12 18:23:22'),(16,'Ze','Morais','2001-01-12','Morais','MoraisarrobaMorais','$2y$10$eEeMAAllTY8UX18z3ZPlYulm1rmcVWPXui/Ipt9ow60Ja3jzRF/IK','2025-05-13 17:31:57'),(17,'Bia','Coelho','2025-05-13','Bia','xd','$2y$10$WtSdLQqQKmJeEQ2UA4PCS.4UwX2CwXKXtt5R6JVGEiyRh6cjdNLtK','2025-05-13 17:57:46'),(18,'Manteigas','Serra da Estrela','2025-05-07','Estrela','Serra','$2y$10$o4Uvy2/0cGQZ4y0xMtGOiug.GgxSUFES9sOQa8Jv5HueLgyfyHlLu','2025-05-19 10:12:50'),(19,'olaa','oaa','2025-05-19','aaa','aaa','$2y$10$Nk5GaLmdCVXn42tVsL4v6.tTJd5a3An0qut14rInmXCmtkvYLouk.','2025-05-19 14:26:51'),(20,'porto','porto','2025-05-08','porto','das','$2y$10$gLOcPlGbAT8uI8la7D5Sq.DknbH3SM3i9uF/XTeIBRapDybsy0rNK','2025-05-20 11:29:48'),(21,'Luis','Santos','2025-06-13','Luis','LS@Ola.com','$2y$10$KmKqF5DjFROO3vzKeKwHBOxKKBqZ1fIX699ATSZ3OD1qoA9gxzw/u','2025-06-13 17:41:53'),(22,'Miguel','Varela','2025-06-05','ENtorseKing','Migueldsandsaasdmosadpsandiosa','$2y$10$VllmTn9Y.Jnb83fZUDC//.StPNdSDAISwTq7gmzXd.QdGGiulLApS','2025-06-17 16:24:47');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `user_address_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  `address_type` enum('Home','Office') DEFAULT NULL,
  PRIMARY KEY (`user_address_id`),
  KEY `fk_user_address_user` (`user_id`),
  KEY `fk_user_address_address` (`address_id`),
  CONSTRAINT `fk_user_address_address` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_address_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_contact`
--

DROP TABLE IF EXISTS `user_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_contact` (
  `user_contact_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `contact_type` enum('Personal','Office') DEFAULT NULL,
  PRIMARY KEY (`user_contact_id`),
  KEY `fk_user_contact_user` (`user_id`),
  KEY `fk_user_contact_contact` (`contact_id`),
  CONSTRAINT `fk_user_contact_contact` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_contact_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_contact`
--

LOCK TABLES `user_contact` WRITE;
/*!40000 ALTER TABLE `user_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_courses`
--

DROP TABLE IF EXISTS `user_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_courses` (
  `user_courses_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `courses_id` int(11) NOT NULL,
  `completion_date` date DEFAULT NULL,
  PRIMARY KEY (`user_courses_id`),
  KEY `fk_user_courses_user` (`user_id`),
  KEY `fk_user_courses_courses` (`courses_id`),
  CONSTRAINT `fk_user_courses_courses` FOREIGN KEY (`courses_id`) REFERENCES `courses` (`courses_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_courses_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_courses`
--

LOCK TABLES `user_courses` WRITE;
/*!40000 ALTER TABLE `user_courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_courses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-23  1:05:41
