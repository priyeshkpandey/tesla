CREATE DATABASE  IF NOT EXISTS `test_automation_framework` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test_automation_framework`;
-- MySQL dump 10.13  Distrib 5.6.22, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: test_automation_framework
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `data_set`
--

DROP TABLE IF EXISTS `data_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set` (
  `data_set_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `tc_id` bigint(7) DEFAULT NULL,
  `is_execute` bit(1) DEFAULT b'0',
  PRIMARY KEY (`data_set_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_set`
--

LOCK TABLES `data_set` WRITE;
/*!40000 ALTER TABLE `data_set` DISABLE KEYS */;
INSERT INTO `data_set` VALUES (1,1,'');
/*!40000 ALTER TABLE `data_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_source`
--

DROP TABLE IF EXISTS `data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_source` (
  `data_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `data_set_id` bigint(10) DEFAULT NULL,
  `step_seq` bigint(7) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_source`
--

LOCK TABLES `data_source` WRITE;
/*!40000 ALTER TABLE `data_source` DISABLE KEYS */;
INSERT INTO `data_source` VALUES (2,1,1,'http://google.com'),(3,1,2,'selenium');
/*!40000 ALTER TABLE `data_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `object_action`
--

DROP TABLE IF EXISTS `object_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `object_action` (
  `obj_action_id` bigint(7) NOT NULL AUTO_INCREMENT,
  `screen_name` varchar(100) DEFAULT NULL,
  `obj_name` varchar(100) DEFAULT NULL,
  `action` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`obj_action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `object_action`
--

LOCK TABLES `object_action` WRITE;
/*!40000 ALTER TABLE `object_action` DISABLE KEYS */;
INSERT INTO `object_action` VALUES (1,'HomePage','launch','openUrl'),(2,'GoogleSearch','searchBox','input');
/*!40000 ALTER TABLE `object_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `results`
--

DROP TABLE IF EXISTS `results`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `results` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `tc_id` bigint(8) DEFAULT NULL,
  `step_seq` bigint(7) DEFAULT NULL,
  `data_set_id` bigint(7) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `executed_at` varchar(45) DEFAULT NULL,
  `release` varchar(45) DEFAULT NULL,
  `test_phase` varchar(45) DEFAULT NULL,
  `code_drop` varchar(45) DEFAULT NULL,
  `environment` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `results`
--

LOCK TABLES `results` WRITE;
/*!40000 ALTER TABLE `results` DISABLE KEYS */;
/*!40000 ALTER TABLE `results` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `run_order`
--

DROP TABLE IF EXISTS `run_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `run_order` (
  `id` bigint(7) NOT NULL AUTO_INCREMENT,
  `tc_id` bigint(7) NOT NULL,
  `is_execute` bit(1) DEFAULT b'0',
  `description` varchar(200) DEFAULT NULL,
  `ds_id` bigint(10) NOT NULL,
  `obj_repo_name` varchar(100) NOT NULL,
  `app_url` varchar(500) NOT NULL,
  `run_seq` bigint(7) NOT NULL,
  `app_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `run_order`
--

LOCK TABLES `run_order` WRITE;
/*!40000 ALTER TABLE `run_order` DISABLE KEYS */;
INSERT INTO `run_order` VALUES (1,1,'','url',1,'test_repo','http://google.com',1,'WEB'),(2,2,'','homepage',2,'test_repo','',2,'WEB');
/*!40000 ALTER TABLE `run_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_script`
--

DROP TABLE IF EXISTS `test_script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_script` (
  `id` bigint(7) NOT NULL AUTO_INCREMENT,
  `step_seq` bigint(5) NOT NULL,
  `tc_id` bigint(7) NOT NULL,
  `oa_id` bigint(5) NOT NULL,
  `on_fail` varchar(50) DEFAULT NULL,
  `on_pass` varchar(50) DEFAULT NULL,
  `is_screenshot` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_script`
--

LOCK TABLES `test_script` WRITE;
/*!40000 ALTER TABLE `test_script` DISABLE KEYS */;
INSERT INTO `test_script` VALUES (1,1,1,1,NULL,NULL,'\0'),(2,2,1,2,NULL,NULL,'\0');
/*!40000 ALTER TABLE `test_script` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-15 23:18:36
