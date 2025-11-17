-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qlchoto
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `giaodich`
--

DROP TABLE IF EXISTS `giaodich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `giaodich` (
  `maGD` varchar(5) NOT NULL,
  `maKH` varchar(5) DEFAULT NULL,
  `maNV` varchar(5) DEFAULT NULL,
  `maOTO` varchar(50) DEFAULT NULL,
  `tongtien` double DEFAULT NULL,
  `ngayGD` datetime DEFAULT NULL,
  `soLuong` int DEFAULT NULL,
  PRIMARY KEY (`maGD`),
  KEY `giaodich_khachhang_idx` (`maKH`),
  KEY `giaodich_oto_idx` (`maOTO`),
  CONSTRAINT `giaodich_oto` FOREIGN KEY (`maOTO`) REFERENCES `oto` (`maOTO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giaodich`
--

LOCK TABLES `giaodich` WRITE;
/*!40000 ALTER TABLE `giaodich` DISABLE KEYS */;
INSERT INTO `giaodich` VALUES ('GD020','KH012','NV006','CX5-24',829000000,'2025-04-01 09:30:00',1),('GD021','KH013','NV007','A4-24',3700000000,'2025-10-05 14:15:00',2),('GD022','KH014','NV006','OUTB24',2169000000,'2025-10-10 11:00:00',1),('GD023','KH012','NV007','MAZ3-24',739000000,'2025-04-05 16:45:00',1),('GD024','KH015','NV006','XC60-24',2320000000,'2025-11-13 10:20:00',1),('GD025','KH013','NV006','OUTB24',4338000000,'2025-02-02 00:11:21',2),('GD026','KH014','NV006','A4-24',1850000000,'2025-11-16 00:43:10',1);
/*!40000 ALTER TABLE `giaodich` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hangoto`
--

DROP TABLE IF EXISTS `hangoto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hangoto` (
  `maHang` varchar(15) NOT NULL,
  `tenHang` varchar(50) DEFAULT NULL,
  `quocGia` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`maHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hangoto`
--

LOCK TABLES `hangoto` WRITE;
/*!40000 ALTER TABLE `hangoto` DISABLE KEYS */;
INSERT INTO `hangoto` VALUES ('AUD','Audi','Đức'),('MAZ','Mazda','Nhật Bản'),('SUB','Subaru','Nhật Bản'),('VOL','Volvo','Thụy Điển');
/*!40000 ALTER TABLE `hangoto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `maKH` varchar(5) NOT NULL,
  `tenKH` varchar(45) DEFAULT NULL,
  `dcKH` varchar(100) DEFAULT NULL,
  `sdtKH` varchar(10) DEFAULT NULL,
  `tongChiTieu` decimal(15,2) DEFAULT '0.00',
  `soLanMua` int DEFAULT '0',
  PRIMARY KEY (`maKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` VALUES ('KH012','Trần Văn Mạnh','33 Trần Hưng Đạo, Q.5, TP. HCM','0909123456',0.00,0),('KH013','Lê Thị Thu Hà','25 Nguyễn Trãi, Q.Thanh Xuân, Hà Nội','0988765432',4338000000.00,1),('KH014','Bùi Chí Công','18 Lý Thường Kiệt, Q.10, TP. HCM','0918112233',1850000000.00,1),('KH015','Đặng Phương Nga','44 Võ Văn Tần, Q.3, TP. HCM','0902555888',0.00,0);
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `maNV` varchar(5) NOT NULL,
  `tenNV` varchar(45) DEFAULT NULL,
  `luongNV` varchar(45) DEFAULT NULL,
  `sdtNV` varchar(10) DEFAULT NULL,
  `chucVu` tinyint DEFAULT NULL,
  `tenDangNhap` varchar(45) DEFAULT NULL,
  `matKhau` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`maNV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
INSERT INTO `nhanvien` VALUES ('NV006','Phan Thanh Bình','18000000','0905123123',0,'nvbinh','123'),('NV007','Võ Thị Kim Chi','19500000','0908456456',0,'nvchi','123'),('NV008','Nguyễn Tấn Phát','100000000','0906761405',1,'adphat','1');
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oto`
--

DROP TABLE IF EXISTS `oto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oto` (
  `maOTO` varchar(50) NOT NULL,
  `tenOTO` varchar(50) DEFAULT NULL,
  `gia` decimal(18,2) DEFAULT NULL,
  `soLuotBan` int DEFAULT NULL,
  `loaiOTO` varchar(45) DEFAULT NULL,
  `soLuong` int DEFAULT NULL,
  `moTa` varchar(1000) DEFAULT NULL,
  `maHang` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`maOTO`),
  KEY `maHang` (`maHang`),
  CONSTRAINT `oto_ibfk_1` FOREIGN KEY (`maHang`) REFERENCES `hangoto` (`maHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oto`
--

LOCK TABLES `oto` WRITE;
/*!40000 ALTER TABLE `oto` DISABLE KEYS */;
INSERT INTO `oto` VALUES ('A4-24','Audi A4 45 TFSI',1850000000.00,3,'Sedan',4,'Sedan hạng sang của Đức.','AUD'),('CX5-24','Mazda CX-5 2.0L',829000000.00,1,'SUV',15,'SUV đô thị bán chạy, thiết kế Kodo.','MAZ'),('MAZ3-24','Mazda 3 1.5L Sport',739000000.00,1,'Hatchback',12,'Mẫu hatchback thể thao.','MAZ'),('OUTB24','Subaru Outback 2.5',2169000000.00,3,'SUV',5,'SUV gầm cao, an toàn 5 sao, dẫn động 4 bánh.','SUB'),('XC60-24','Volvo XC60 Ultimate',2320000000.00,1,'SUV',4,'SUV hạng sang an toàn nhất thế giới.','VOL');
/*!40000 ALTER TABLE `oto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-16 16:29:22
