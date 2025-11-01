-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: gestion_citas
-- ------------------------------------------------------
-- Server version	8.4.3

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
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id_cita` int NOT NULL AUTO_INCREMENT,
  `id_paciente` int NOT NULL,
  `id_medico` int NOT NULL,
  `fecha` date NOT NULL,
  `hora` varchar(255) DEFAULT NULL,
  `estado` varchar(20) NOT NULL,
  `motivo` varchar(250) DEFAULT NULL,
  `fecha_hora` datetime GENERATED ALWAYS AS (cast(concat(`fecha`,_utf8mb4' ',`hora`) as datetime)) STORED,
  `observaciones` text,
  `duracion` int NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `asistencia` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_cita`),
  UNIQUE KEY `UQ_cita_medico_fecha_hora` (`id_medico`,`fecha`,`hora`),
  KEY `IX_cita_medico_fh` (`id_medico`,`fecha_hora`),
  KEY `IX_cita_paciente_fh` (`id_paciente`,`fecha_hora`),
  CONSTRAINT `FK_cita_medico` FOREIGN KEY (`id_medico`) REFERENCES `medico` (`id_medico`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_cita_paciente` FOREIGN KEY (`id_paciente`) REFERENCES `paciente` (`id_paciente`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `CK_cita_estado` CHECK ((`estado` in (_utf8mb4'PROGRAMADA',_utf8mb4'CONFIRMADA',_utf8mb4'ATENDIDA',_utf8mb4'CANCELADA',_utf8mb4'NO_ASISTIO')))
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` (`id_cita`, `id_paciente`, `id_medico`, `fecha`, `hora`, `estado`, `motivo`, `observaciones`, `duracion`, `fecha_creacion`, `asistencia`) VALUES (1,1,1,'2025-10-29','09:00:00','ATENDIDA','Consulta general',NULL,30,NULL,NULL),(2,2,2,'2025-10-30','10:30:00','PROGRAMADA','Revisión cardíaca',NULL,45,NULL,NULL),(3,4,1,'2025-11-05','14:00:00','PROGRAMADA','Seguimiento',NULL,30,NULL,NULL),(4,6,3,'2025-11-01','11:00:00','PROGRAMADA','Odontología',NULL,60,NULL,NULL),(5,7,2,'2025-11-02','15:30:00','PROGRAMADA','Cardiología',NULL,45,NULL,NULL),(6,8,4,'2025-11-04','13:00:00','PROGRAMADA','Dermatología',NULL,40,NULL,NULL),(7,9,2,'2025-11-06','16:00:00','PROGRAMADA','Control cardiaco',NULL,30,NULL,NULL),(8,12,1,'2025-11-07','10:00:00','PROGRAMADA','Revisión general',NULL,30,NULL,NULL),(9,14,3,'2025-11-08','11:30:00','CONFIRMADA','Limpieza dental',NULL,50,NULL,NULL),(12,7,1,'2025-10-31','10:00','PROGRAMADA','Consulta general','Revisión de síntomas de gripe',30,NULL,NULL),(13,7,2,'2025-11-01','14:30','CONFIRMADA','Revisión dental','Limpieza dental y revisión de caries',45,NULL,NULL),(14,7,3,'2025-11-03','09:00','PROGRAMADA','Chequeo oftalmológico','Revisión de vista y medida de lentes',30,NULL,NULL),(15,7,1,'2025-11-05','11:00','CONFIRMADA','Seguimiento COVID','Evaluación post-enfermedad',20,NULL,NULL),(16,7,2,'2025-10-29','15:00','ATENDIDA','Consulta dermatológica','Tratamiento de acné',40,NULL,NULL),(17,7,3,'2025-10-30','16:00','CANCELADA','Análisis de sangre','Paciente canceló por emergencia',15,NULL,NULL),(18,18,1,'2025-10-31','10:00:00','CANCELADA','Consulta general','Revisión de síntomas',30,NULL,NULL),(19,18,2,'2025-11-01','14:30:00','CONFIRMADA','Revisión dental','Limpieza dental',45,NULL,NULL),(20,18,1,'2025-11-03','09:00:00','PROGRAMADA','Chequeo','Chequeo general',30,NULL,NULL),(21,18,2,'2025-11-05','11:00:00','CONFIRMADA','Seguimiento','Seguimiento post-consulta',20,NULL,NULL),(22,18,3,'2025-10-29','15:00:00','ATENDIDA','Consulta dermatológica','Tratamiento de acné',40,NULL,NULL),(23,18,1,'2025-11-01','15:51','CANCELADA','Test1','Test1',30,NULL,NULL),(26,18,1,'2025-11-06','09:00','PROGRAMADA','zzz','',30,NULL,_binary '\0'),(27,18,7,'2025-11-05','09:00','PROGRAMADA','zzzz','',30,NULL,_binary '\0'),(28,18,7,'2025-11-12','11:00','PROGRAMADA','zzzz','',30,NULL,_binary '\0'),(29,18,7,'2025-11-12','10:00','PROGRAMADA','zzzzz','',30,NULL,_binary '\0'),(30,20,7,'2025-11-04','09:00','PROGRAMADA','zzzzz','',30,NULL,_binary '\0');
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disponibilidad_medico`
--

DROP TABLE IF EXISTS `disponibilidad_medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disponibilidad_medico` (
  `id_disponibilidad` int NOT NULL AUTO_INCREMENT,
  `id_medico` int NOT NULL,
  `dia_semana` varchar(255) NOT NULL,
  `hora_inicio` time NOT NULL,
  `hora_fin` time NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_disponibilidad`),
  KEY `id_medico` (`id_medico`),
  CONSTRAINT `disponibilidad_medico_ibfk_1` FOREIGN KEY (`id_medico`) REFERENCES `medico` (`id_medico`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disponibilidad_medico`
--

LOCK TABLES `disponibilidad_medico` WRITE;
/*!40000 ALTER TABLE `disponibilidad_medico` DISABLE KEYS */;
INSERT INTO `disponibilidad_medico` VALUES (1,1,'LUNES','08:00:00','13:00:00',1),(2,1,'MARTES','14:00:00','19:00:00',1),(3,1,'JUEVES','08:00:00','13:00:00',1),(4,2,'LUNES','14:00:00','19:00:00',1),(5,2,'MIERCOLES','08:00:00','13:00:00',1),(6,2,'VIERNES','14:00:00','19:00:00',1),(7,1,'VIERNES','08:00:00','10:00:00',1),(8,1,'DOMINGO','08:00:00','10:00:00',1);
/*!40000 ALTER TABLE `disponibilidad_medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidad` (
  `id_especialidad` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `activo` int DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id_especialidad`),
  UNIQUE KEY `UQ_especialidad_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidad`
--

LOCK TABLES `especialidad` WRITE;
/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` VALUES (1,'Medicina General',1,'Atiende problemas de salud generales, diagnóstico y tratamiento básico y bueno.',NULL),(2,'Pediatría',1,'Especialista en cuidado de la salud de niños y recién nacidos',NULL),(5,'Cardiologia',1,'Diagnóstico y tratamiento de enfermedades del corazón y sistema cardiovascular',NULL),(7,'Dermatología',1,'Especialidad médica que se enfoca en el diagnóstico y tratamiento de enfermedades de la piel',NULL);
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `id_medico` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(12) NOT NULL,
  `nombres` varchar(80) NOT NULL,
  `apellidos` varchar(80) NOT NULL,
  `id_especialidad` int NOT NULL,
  `correo` varchar(120) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `activo` int DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_medico`),
  UNIQUE KEY `UQ_medico_dni` (`dni`),
  UNIQUE KEY `UK_2i15b0mkmfw98789395sl0hw9` (`email`),
  KEY `IX_medico_especialidad` (`id_especialidad`),
  KEY `FK88ukndsp99d9kgb7kg5oj0v22` (`id_usuario`),
  CONSTRAINT `FK88ukndsp99d9kgb7kg5oj0v22` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FK_medico_especialidad` FOREIGN KEY (`id_especialidad`) REFERENCES `especialidad` (`id_especialidad`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (1,'44556677','Dr. Juan','Pérezzz',1,'Test2@test.com','11111111',1,'Test2@test.com',2,'Chancay'),(2,'99887766','Ana','Lopez',2,'Ciro@clinic.pe','988333444',1,NULL,NULL,NULL),(3,'46923060','Cirilin','Vasquez',1,'ciro.vm92@gmail.com','933055687',1,NULL,NULL,NULL),(4,'12324545','Maruja','Malpartida',2,'undefined','931519851',1,'undefined',NULL,'undefined'),(7,'87654321','Carlos','Rodriguez',5,'carlos.rodriguez@test.com','987654321',1,'carlos.rodriguez@test.com',NULL,'Av. Test 456');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente` (
  `id_paciente` int NOT NULL AUTO_INCREMENT,
  `dni` varchar(12) NOT NULL,
  `nombres` varchar(80) NOT NULL,
  `apellidos` varchar(80) NOT NULL,
  `correo` varchar(120) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `direccion` varchar(200) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  `estado_civil` varchar(255) DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `activo` int NOT NULL DEFAULT '1',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_paciente`),
  UNIQUE KEY `UQ_paciente_dni` (`dni`),
  UNIQUE KEY `UK_18lohpcm5raj5wjr9t02t9axb` (`email`),
  KEY `IX_paciente_apellidos_nombres` (`apellidos`,`nombres`),
  KEY `FK1vx4fcl7eb0wbyvff1184dr0m` (`id_usuario`),
  CONSTRAINT `FK1vx4fcl7eb0wbyvff1184dr0m` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (1,'11112222','Ciro Jelsin','Vasquez Malpartida','juan.c@demo.com','933055687','2025-08-14 14:12:37','Benjamin vizquerra','juan carlos1@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(2,'22223333','María','García','maria@demo.com','999222333','2025-08-14 14:12:41',NULL,'maría2@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(4,'11113222','Juan','Pérez','juan@demo.com','999111222','2025-08-14 14:20:19',NULL,'juan4@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(6,'22423333','María','García','maria@demo.com','999222333','2025-08-14 14:20:24',NULL,'maría6@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(7,'46923060','Cirilin','Vasquez','ciro.vm92@gmail.com','933055687','2025-08-14 21:49:23',NULL,'cirilin7@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(8,'12457845','Max','Villafan','max@demo.com','123456789','2025-08-16 11:48:05',NULL,'max8@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(9,'15151515','Prueba','Prueba2','prueba@prueba.com','121123123','2025-08-16 16:37:33',NULL,'prueba9@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(12,'12568978','Ciro Jelsin','Vasquez Malpartida','prueba123@prueba.com','933055687','2025-08-16 21:10:32','Benjamin vizquerra','alex12@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(14,'987654321','Juliette','Vasquez','prueba1246578@gmail.com','159753456','2025-08-26 20:55:17',NULL,'juliette14@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(15,'78787878','Krissel','Vasquez','krissel@prueba.com','789456123','2025-08-26 21:47:28',NULL,'krissel15@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(16,'4444444','Pedro','Sanchez','pedro@email.com','999111222','2025-10-13 21:55:36','','pedro16@demo.com',NULL,NULL,NULL,NULL,1,'2025-10-31 21:16:24'),(18,'12345678','Test','Paciente',NULL,'123123123','2025-10-31 15:17:48','Calle Test 123','paciente@test.com','1990-01-01',7,'Soltero','M',1,'2025-10-31 21:16:24'),(20,'12121212','ziri','ziri',NULL,'123456987','2025-11-01 01:57:27',NULL,'ziri@test.com',NULL,10,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `role` varchar(20) NOT NULL,
  `nombres` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `activo` int DEFAULT NULL,
  `fecha_creacion` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','1234','admin@gestioncitas.com','ADMIN','Administrador','Sistema',1,'2025-10-29 20:37:17'),(2,'medico','1234','medico@gestioncitas.com','MEDICO','Dr. Juan','Pérez',1,'2025-10-29 21:22:28'),(7,'paciente','1234','paciente@test.com','PACIENTE','testPaciente','testPaciente',1,'2025-10-31 15:10:21'),(8,'zirelement','$2a$10$HEgN0moN/ICYaKMXDdm05uQV5675kVVP99fCF/FGhMfNiGi3ucBL.','ziro@test.com','PACIENTE','ziros','ziro',1,NULL),(9,'testuser','test123','testuser@test.com','PACIENTE','Test','User',1,NULL),(10,'ziro69','123456','ziri@test.com','PACIENTE','ziri','ziri',1,NULL);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vista_agenda_medica`
--

DROP TABLE IF EXISTS `vista_agenda_medica`;
/*!50001 DROP VIEW IF EXISTS `vista_agenda_medica`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_agenda_medica` AS SELECT 
 1 AS `id_cita`,
 1 AS `paciente`,
 1 AS `medico`,
 1 AS `especialidad`,
 1 AS `fecha`,
 1 AS `hora`,
 1 AS `fecha_hora`,
 1 AS `estado`,
 1 AS `motivo`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vista_agenda_medica`
--

/*!50001 DROP VIEW IF EXISTS `vista_agenda_medica`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_agenda_medica` AS select `c`.`id_cita` AS `id_cita`,concat_ws(' ',`p`.`nombres`,`p`.`apellidos`) AS `paciente`,concat_ws(' ',`m`.`nombres`,`m`.`apellidos`) AS `medico`,`e`.`nombre` AS `especialidad`,`c`.`fecha` AS `fecha`,`c`.`hora` AS `hora`,`c`.`fecha_hora` AS `fecha_hora`,`c`.`estado` AS `estado`,`c`.`motivo` AS `motivo` from (((`cita` `c` join `paciente` `p` on((`p`.`id_paciente` = `c`.`id_paciente`))) join `medico` `m` on((`m`.`id_medico` = `c`.`id_medico`))) left join `especialidad` `e` on((`e`.`id_especialidad` = `m`.`id_especialidad`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-01 12:14:07
