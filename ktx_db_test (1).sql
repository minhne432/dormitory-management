-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2025 at 06:36 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ktx_db_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `applications`
--

CREATE TABLE `applications` (
  `application_id` bigint(20) NOT NULL,
  `approval_date` date DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` enum('approved','completed','pending','rejected') DEFAULT NULL,
  `submission_date` date DEFAULT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `dorm_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  `dormitory_area` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `applications`
--

INSERT INTO `applications` (`application_id`, `approval_date`, `note`, `status`, `submission_date`, `approved_by`, `dorm_id`, `student_id`, `dormitory_area`) VALUES
(1, '2025-04-24', NULL, 'approved', '2025-04-24', 1, 1, 4, NULL),
(2, '2025-04-24', NULL, 'approved', '2025-04-24', 1, 1, 5, NULL),
(3, '2025-04-24', NULL, 'approved', '2025-04-24', 1, 1, 6, NULL),
(4, '2025-04-24', NULL, 'approved', '2025-04-24', 1, 1, 7, NULL),
(5, '2025-04-24', NULL, 'approved', '2025-04-24', 1, 1, 8, NULL),
(6, '2025-04-24', NULL, 'approved', '2025-04-24', 1, 1, 10, NULL),
(7, NULL, NULL, 'pending', '2025-04-25', NULL, 1, 21, NULL),
(8, NULL, NULL, 'pending', '2025-04-24', NULL, 1, 22, NULL),
(9, NULL, NULL, 'pending', '2025-04-23', NULL, 1, 23, NULL),
(10, NULL, NULL, 'pending', '2025-04-22', NULL, 1, 24, NULL),
(11, NULL, NULL, 'pending', '2025-04-21', NULL, 1, 25, NULL),
(12, '2025-04-26', 'Khu', 'approved', '2025-04-26', 1, NULL, 20, 'KTX Khu A');

-- --------------------------------------------------------

--
-- Stand-in structure for view `approved_applications`
-- (See below for the actual view)
--
CREATE TABLE `approved_applications` (
`application_id` bigint(20)
,`student_id` bigint(20)
,`full_name` varchar(100)
,`date_of_birth` date
,`gender` varchar(20)
,`department` varchar(100)
,`student_class` varchar(50)
,`phone_number` varchar(50)
,`email` varchar(100)
,`address` varchar(255)
,`submission_date` date
,`status` enum('approved','completed','pending','rejected')
,`approved_by` bigint(20)
,`approval_date` date
,`dormitory_area` varchar(255)
,`note` varchar(255)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `approved_student_service_requests`
-- (See below for the actual view)
--
CREATE TABLE `approved_student_service_requests` (
`student_id` bigint(20)
,`full_name` varchar(100)
,`email` varchar(100)
,`gender` varchar(20)
,`phone_number` varchar(50)
,`student_class` varchar(50)
,`department` varchar(100)
,`date_of_birth` date
,`address` varchar(255)
,`room_id` bigint(20)
,`assigned_date` date
,`room_end_date` date
,`registration_id` bigint(20)
,`service_start_date` date
,`service_end_date` date
,`service_status` enum('pending','approved','rejected')
,`actual_quantity` int(11)
,`service_name` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `billing_schedules`
--

CREATE TABLE `billing_schedules` (
  `schedule_id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `schedule_time` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bills`
--

CREATE TABLE `bills` (
  `bill_id` bigint(20) NOT NULL,
  `bill_type` enum('phòng','điện-nước','dịch-vụ') DEFAULT NULL,
  `billing_period` varchar(255) DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `issue_date` date DEFAULT NULL,
  `status` enum('overdue','paid','unpaid') DEFAULT 'unpaid',
  `total_amount` double DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `paid_date` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bills`
--

INSERT INTO `bills` (`bill_id`, `bill_type`, `billing_period`, `due_date`, `issue_date`, `status`, `total_amount`, `room_id`, `student_id`, `created_date`, `paid_date`) VALUES
(9, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 1, NULL, '2024-10-18 10:00:00.000000', '2024-10-22 09:00:00.000000'),
(10, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 2, NULL, '2024-10-18 10:00:01.000000', '2024-10-23 10:00:00.000000'),
(11, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 3, NULL, '2024-10-18 10:00:02.000000', '2024-10-24 11:00:00.000000'),
(12, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 4, NULL, '2024-10-18 10:00:03.000000', '2024-10-25 12:00:00.000000'),
(13, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 1, NULL, '2024-11-18 10:00:00.000000', '2024-11-22 09:00:00.000000'),
(14, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 2, NULL, '2024-11-18 10:00:01.000000', '2024-11-23 10:00:00.000000'),
(15, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 3, NULL, '2024-11-18 10:00:02.000000', '2024-11-24 11:00:00.000000'),
(16, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 4, NULL, '2024-11-18 10:00:03.000000', '2024-11-25 12:00:00.000000'),
(17, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 1, NULL, '2024-12-18 10:00:00.000000', '2024-12-22 09:00:00.000000'),
(18, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 2, NULL, '2024-12-18 10:00:01.000000', '2024-12-23 10:00:00.000000'),
(19, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 3, NULL, '2024-12-18 10:00:02.000000', '2024-12-24 11:00:00.000000'),
(20, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 4, NULL, '2024-12-18 10:00:03.000000', '2024-12-25 12:00:00.000000'),
(21, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 1, NULL, '2025-01-18 10:00:00.000000', '2025-01-22 09:00:00.000000'),
(22, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 2, NULL, '2025-01-18 10:00:01.000000', '2025-01-23 10:00:00.000000'),
(23, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 3, NULL, '2025-01-18 10:00:02.000000', '2025-01-24 11:00:00.000000'),
(24, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 4, NULL, '2025-01-18 10:00:03.000000', '2025-01-25 12:00:00.000000'),
(25, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 1, NULL, '2025-02-18 10:00:00.000000', '2025-02-22 09:00:00.000000'),
(26, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 2, NULL, '2025-02-18 10:00:01.000000', '2025-02-23 10:00:00.000000'),
(27, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 3, NULL, '2025-02-18 10:00:02.000000', '2025-02-24 11:00:00.000000'),
(28, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 4, NULL, '2025-02-18 10:00:03.000000', '2025-02-25 12:00:00.000000'),
(29, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 4, '2024-11-01 08:00:00.000000', '2024-11-05 09:00:00.000000'),
(30, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 5, '2024-11-01 08:00:01.000000', '2024-11-06 09:00:00.000000'),
(31, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 6, '2024-11-01 08:00:02.000000', '2024-11-07 09:00:00.000000'),
(32, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 7, '2024-11-01 08:00:03.000000', '2024-11-08 09:00:00.000000'),
(33, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 8, '2024-11-01 08:00:04.000000', '2024-11-09 09:00:00.000000'),
(34, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 10, '2024-11-01 08:00:05.000000', '2024-11-10 09:00:00.000000'),
(35, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 4, '2024-12-01 08:00:00.000000', '2024-12-05 09:00:00.000000'),
(36, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 5, '2024-12-01 08:00:01.000000', '2024-12-06 09:00:00.000000'),
(37, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 6, '2024-12-01 08:00:02.000000', '2024-12-07 09:00:00.000000'),
(38, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 7, '2024-12-01 08:00:03.000000', '2024-12-08 09:00:00.000000'),
(39, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 8, '2024-12-01 08:00:04.000000', '2024-12-09 09:00:00.000000'),
(40, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 10, '2024-12-01 08:00:05.000000', '2024-12-10 09:00:00.000000'),
(41, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 4, '2025-01-01 08:00:00.000000', '2025-01-05 09:00:00.000000'),
(42, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 5, '2025-01-01 08:00:01.000000', '2025-01-06 09:00:00.000000'),
(43, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 6, '2025-01-01 08:00:02.000000', '2025-01-07 09:00:00.000000'),
(44, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 7, '2025-01-01 08:00:03.000000', '2025-01-08 09:00:00.000000'),
(45, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 8, '2025-01-01 08:00:04.000000', '2025-01-09 09:00:00.000000'),
(46, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 10, '2025-01-01 08:00:05.000000', '2025-01-10 09:00:00.000000'),
(47, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 4, '2025-02-01 08:00:00.000000', '2025-02-05 09:00:00.000000'),
(48, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 5, '2025-02-01 08:00:01.000000', '2025-02-06 09:00:00.000000'),
(49, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 6, '2025-02-01 08:00:02.000000', '2025-02-07 09:00:00.000000'),
(50, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 7, '2025-02-01 08:00:03.000000', '2025-02-08 09:00:00.000000'),
(51, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 8, '2025-02-01 08:00:04.000000', '2025-02-09 09:00:00.000000'),
(52, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 10, '2025-02-01 08:00:05.000000', '2025-02-10 09:00:00.000000'),
(53, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 4, '2025-03-01 08:00:00.000000', '2025-03-05 09:00:00.000000'),
(54, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 5, '2025-03-01 08:00:01.000000', '2025-03-06 09:00:00.000000'),
(55, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 6, '2025-03-01 08:00:02.000000', '2025-03-07 09:00:00.000000'),
(56, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 7, '2025-03-01 08:00:03.000000', '2025-03-08 09:00:00.000000'),
(57, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 8, '2025-03-01 08:00:04.000000', '2025-03-09 09:00:00.000000'),
(58, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 10, '2025-03-01 08:00:05.000000', '2025-03-10 09:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `bill_items`
--

CREATE TABLE `bill_items` (
  `bill_item_id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `bill_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  `registration_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unit_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill_items`
--

INSERT INTO `bill_items` (`bill_item_id`, `amount`, `bill_id`, `service_id`, `registration_id`, `quantity`, `unit_price`) VALUES
(11, 150000, 9, 3, NULL, 50, 3000),
(12, 70000, 9, 4, NULL, 10, 7000),
(13, 150000, 10, 3, NULL, 50, 3000),
(14, 70000, 10, 4, NULL, 10, 7000),
(15, 150000, 11, 3, NULL, 50, 3000),
(16, 70000, 11, 4, NULL, 10, 7000),
(17, 150000, 12, 3, NULL, 50, 3000),
(18, 70000, 12, 4, NULL, 10, 7000),
(19, 150000, 13, 3, NULL, 50, 3000),
(20, 70000, 13, 4, NULL, 10, 7000),
(21, 150000, 14, 3, NULL, 50, 3000),
(22, 70000, 14, 4, NULL, 10, 7000),
(23, 150000, 15, 3, NULL, 50, 3000),
(24, 70000, 15, 4, NULL, 10, 7000),
(25, 150000, 16, 3, NULL, 50, 3000),
(26, 70000, 16, 4, NULL, 10, 7000),
(27, 150000, 17, 3, NULL, 50, 3000),
(28, 70000, 17, 4, NULL, 10, 7000),
(29, 150000, 18, 3, NULL, 50, 3000),
(30, 70000, 18, 4, NULL, 10, 7000),
(31, 150000, 19, 3, NULL, 50, 3000),
(32, 70000, 19, 4, NULL, 10, 7000),
(33, 150000, 20, 3, NULL, 50, 3000),
(34, 70000, 20, 4, NULL, 10, 7000),
(35, 150000, 21, 3, NULL, 50, 3000),
(36, 70000, 21, 4, NULL, 10, 7000),
(37, 150000, 22, 3, NULL, 50, 3000),
(38, 70000, 22, 4, NULL, 10, 7000),
(39, 150000, 23, 3, NULL, 50, 3000),
(40, 70000, 23, 4, NULL, 10, 7000),
(41, 150000, 24, 3, NULL, 50, 3000),
(42, 70000, 24, 4, NULL, 10, 7000),
(43, 150000, 25, 3, NULL, 50, 3000),
(44, 70000, 25, 4, NULL, 10, 7000),
(45, 150000, 26, 3, NULL, 50, 3000),
(46, 70000, 26, 4, NULL, 10, 7000),
(47, 150000, 27, 3, NULL, 50, 3000),
(48, 70000, 27, 4, NULL, 10, 7000),
(49, 150000, 28, 3, NULL, 50, 3000),
(50, 70000, 28, 4, NULL, 10, 7000),
(51, 40000, 29, 2, 12, 2, 20000),
(52, 40000, 30, 2, 13, 2, 20000),
(53, 40000, 31, 2, 14, 2, 20000),
(54, 40000, 32, 2, 15, 2, 20000),
(55, 40000, 33, 2, 16, 2, 20000),
(56, 40000, 34, 2, 17, 2, 20000),
(57, 60000, 35, 2, 18, 3, 20000),
(58, 60000, 36, 2, 19, 3, 20000),
(59, 60000, 37, 2, 20, 3, 20000),
(60, 60000, 38, 2, 21, 3, 20000),
(61, 60000, 39, 2, 22, 3, 20000),
(62, 60000, 40, 2, 23, 3, 20000),
(63, 80000, 41, 2, 24, 4, 20000),
(64, 80000, 42, 2, 25, 4, 20000),
(65, 80000, 43, 2, 26, 4, 20000),
(66, 80000, 44, 2, 27, 4, 20000),
(67, 80000, 45, 2, 28, 4, 20000),
(68, 80000, 46, 2, 29, 4, 20000),
(69, 100000, 47, 2, 30, 5, 20000),
(70, 100000, 48, 2, 31, 5, 20000),
(71, 100000, 49, 2, 32, 5, 20000),
(72, 100000, 50, 2, 33, 5, 20000),
(73, 100000, 51, 2, 34, 5, 20000),
(74, 100000, 52, 2, 35, 5, 20000),
(75, 40000, 53, 2, 36, 2, 20000),
(76, 40000, 54, 2, 37, 2, 20000),
(77, 40000, 55, 2, 38, 2, 20000),
(78, 40000, 56, 2, 39, 2, 20000),
(79, 40000, 57, 2, 40, 2, 20000),
(80, 40000, 58, 2, 41, 2, 20000);

-- --------------------------------------------------------

--
-- Table structure for table `dormitories`
--

CREATE TABLE `dormitories` (
  `dorm_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `dorm_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dormitories`
--

INSERT INTO `dormitories` (`dorm_id`, `address`, `description`, `dorm_name`) VALUES
(1, 'Khu A, Đường 1', 'Gần căn tin', 'KTX Khu A'),
(2, 'Khu B, Đường 2', 'Cạnh sân vận động', 'KTX Khu B');

-- --------------------------------------------------------

--
-- Table structure for table `managers`
--

CREATE TABLE `managers` (
  `manager_id` bigint(20) NOT NULL,
  `department` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `managers`
--

INSERT INTO `managers` (`manager_id`, `department`, `email`, `full_name`, `phone_number`, `position`) VALUES
(1, 'Phòng Công tác SV', 'manager1@univ.edu', 'Manager One', '0909000001', 'Trưởng phòng'),
(2, 'Phòng Công tác SV', 'manager2@univ.edu', 'Manager Two', '0909000002', 'Phó phòng'),
(3, 'Phòng Quản lý KTX', 'manager3@univ.edu', 'Manager Three', '0909000003', 'Nhân viên');

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id` bigint(20) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `content` tinytext NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `title` varchar(150) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `author`, `content`, `created_at`, `title`, `updated_at`) VALUES
(1, 'Nguyễn Văn A', 'Hôm nay thời tiết nắng đẹp, sinh viên háo hức tham gia các hoạt động ngoại khoá.', '2025-04-01 08:00:00.000000', 'Hoạt động ngoại khoá rộn ràng', '2025-04-01 09:00:00.000000'),
(2, 'Trần Thị B', 'Ký túc xá sẽ tiến hành bảo trì hệ thống điện trong hai ngày cuối tuần.', '2025-04-02 10:00:00.000000', 'Thông báo bảo trì hệ thống điện', '2025-04-02 10:30:00.000000'),
(3, 'Lê Văn C', 'Giải bóng đá sinh viên chính thức khởi tranh với nhiều đội bóng mạnh tham dự.', '2025-04-03 14:00:00.000000', 'Giải bóng đá sinh viên 2025 bắt đầu', '2025-04-03 15:00:00.000000'),
(4, 'Phạm Thị D', 'Nhằm chào mừng ngày Sách Việt Nam, thư viện tổ chức buổi giao lưu với các tác giả nổi tiếng.', '2025-04-04 09:00:00.000000', 'Ngày hội sách tại thư viện', '2025-04-04 11:00:00.000000'),
(5, 'Ngô Văn E', 'Tổ chức tập huấn phòng cháy chữa cháy dành cho sinh viên nội trú vào thứ Bảy.', '2025-04-05 13:00:00.000000', 'Tập huấn PCCC cho sinh viên', '2025-04-05 14:00:00.000000'),
(6, 'Đỗ Thị F', 'Phòng quản lý ký túc xá thông báo về việc đăng ký ở lại dịp lễ 30/4 và 1/5.', '2025-04-06 16:00:00.000000', 'Thông báo đăng ký ở lại lễ 30/4', '2025-04-06 16:30:00.000000'),
(7, 'Huỳnh Văn G', 'Sinh viên cần kiểm tra thông tin cá nhân trong hệ thống trước ngày 10/4 để tránh sai sót.', '2025-04-07 07:30:00.000000', 'Cập nhật thông tin sinh viên', '2025-04-07 08:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `read_status` enum('read','unread') DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`notification_id`, `created_at`, `message`, `read_status`, `title`, `student_id`) VALUES
(13, '2024-10-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 4),
(14, '2024-10-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 5),
(15, '2024-10-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 6),
(16, '2024-10-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 7),
(17, '2024-10-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 8),
(18, '2024-10-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 10),
(19, '2024-11-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 4),
(20, '2024-11-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 5),
(21, '2024-11-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 6),
(22, '2024-11-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 7),
(23, '2024-11-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 8),
(24, '2024-11-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 10),
(25, '2024-12-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 4),
(26, '2024-12-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 5),
(27, '2024-12-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 6),
(28, '2024-12-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 7),
(29, '2024-12-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 8),
(30, '2024-12-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 10),
(31, '2025-01-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 4),
(32, '2025-01-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 5),
(33, '2025-01-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 6),
(34, '2025-01-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 7),
(35, '2025-01-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 8),
(36, '2025-01-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 10),
(37, '2025-02-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 4),
(38, '2025-02-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 5),
(39, '2025-02-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 6),
(40, '2025-02-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 7),
(41, '2025-02-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 8),
(42, '2025-02-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 10),
(43, '2024-11-01 08:01:00.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 4),
(44, '2024-11-01 08:01:01.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 5),
(45, '2024-11-01 08:01:02.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 6),
(46, '2024-11-01 08:01:03.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 7),
(47, '2024-11-01 08:01:04.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 8),
(48, '2024-11-01 08:01:05.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 10),
(49, '2024-12-01 08:01:00.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 4),
(50, '2024-12-01 08:01:01.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 5),
(51, '2024-12-01 08:01:02.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 6),
(52, '2024-12-01 08:01:03.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 7),
(53, '2024-12-01 08:01:04.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 8),
(54, '2024-12-01 08:01:05.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 10),
(55, '2025-01-01 08:01:00.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 4),
(56, '2025-01-01 08:01:01.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 5),
(57, '2025-01-01 08:01:02.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 6),
(58, '2025-01-01 08:01:03.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 7),
(59, '2025-01-01 08:01:04.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 8),
(60, '2025-01-01 08:01:05.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 10),
(61, '2025-02-01 08:01:00.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 4),
(62, '2025-02-01 08:01:01.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 5),
(63, '2025-02-01 08:01:02.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 6),
(64, '2025-02-01 08:01:03.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 7),
(65, '2025-02-01 08:01:04.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 8),
(66, '2025-02-01 08:01:05.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 10),
(67, '2025-03-01 08:01:00.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 4),
(68, '2025-03-01 08:01:01.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 5),
(69, '2025-03-01 08:01:02.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 6),
(70, '2025-03-01 08:01:03.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 7),
(71, '2025-03-01 08:01:04.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 8),
(72, '2025-03-01 08:01:05.000000', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 10);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `payment_id` bigint(20) NOT NULL,
  `amount_paid` double DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `status` enum('completed','pending') DEFAULT 'pending',
  `bill_id` bigint(20) NOT NULL,
  `vnp_txn_ref` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Stand-in structure for view `pending_applications`
-- (See below for the actual view)
--
CREATE TABLE `pending_applications` (
`application_id` bigint(20)
,`submission_date` date
,`approval_date` date
,`dormitory_area` varchar(255)
,`full_name` varchar(100)
,`address` varchar(255)
,`department` varchar(100)
,`phone_number` varchar(50)
);

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `room_id` bigint(20) NOT NULL,
  `current_occupancy` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `room_number` varchar(50) DEFAULT NULL,
  `status` enum('available','full','maintenance') DEFAULT NULL,
  `dorm_id` bigint(20) NOT NULL,
  `room_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`room_id`, `current_occupancy`, `description`, `room_number`, `status`, `dorm_id`, `room_type_id`) VALUES
(1, 3, 'Phòng trống', 'A101', 'available', 1, 1),
(2, 3, 'Phòng trống', 'A102', 'available', 1, 2),
(3, 0, 'Phòng trống', 'B201', 'available', 2, 3),
(4, 0, 'Phòng trống', 'B202', 'available', 2, 4);

-- --------------------------------------------------------

--
-- Table structure for table `room_assignments`
--

CREATE TABLE `room_assignments` (
  `assigned_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `room_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room_assignments`
--

INSERT INTO `room_assignments` (`assigned_date`, `end_date`, `room_id`, `student_id`) VALUES
('2025-04-26', NULL, 1, 4),
('2025-04-26', NULL, 1, 5),
('2025-04-26', NULL, 1, 6),
('2025-04-26', NULL, 2, 7),
('2025-04-26', NULL, 2, 8),
('2025-04-26', NULL, 2, 10);

-- --------------------------------------------------------

--
-- Stand-in structure for view `room_list`
-- (See below for the actual view)
--
CREATE TABLE `room_list` (
`room_id` bigint(20)
,`room_number` varchar(50)
,`room_description` varchar(255)
,`status` enum('available','full','maintenance')
,`current_occupancy` int(11)
,`room_max_capacity` int(11)
,`room_type_name` varchar(50)
,`room_type_price` double
,`dorm_name` varchar(100)
,`dorm_address` varchar(255)
,`dorm_description` varchar(255)
,`room_type_id` bigint(20)
,`dorm_id` bigint(20)
);

-- --------------------------------------------------------

--
-- Table structure for table `room_types`
--

CREATE TABLE `room_types` (
  `room_type_id` bigint(20) NOT NULL,
  `gender` enum('Nam','Nữ') DEFAULT NULL,
  `max_capacity` int(11) NOT NULL,
  `price` double NOT NULL,
  `type_name` varchar(50) NOT NULL,
  `dorm_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `room_types`
--

INSERT INTO `room_types` (`room_type_id`, `gender`, `max_capacity`, `price`, `type_name`, `dorm_id`) VALUES
(1, 'Nam', 4, 500000, 'Phòng 4 nam', 1),
(2, 'Nữ', 4, 550000, 'Phòng 4 nữ', 1),
(3, 'Nam', 6, 300000, 'Phòng 6 nam', 2),
(4, 'Nữ', 6, 350000, 'Phòng 6 nữ', 2);

-- --------------------------------------------------------

--
-- Table structure for table `services`
--

CREATE TABLE `services` (
  `service_id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `service_name` varchar(255) DEFAULT NULL,
  `service_type` enum('PERSONAL','ROOM') DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `visible_for_student` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `services`
--

INSERT INTO `services` (`service_id`, `description`, `service_name`, `service_type`, `unit`, `unit_price`, `visible_for_student`) VALUES
(1, 'Internet cáp quang', 'Internet', 'ROOM', 'tháng', 100000, 1),
(2, 'Dịch vụ giặt ủi', 'Giặt ủi', 'PERSONAL', 'lần', 20000, 1),
(3, 'Điện sinh hoạt', 'Điện', 'ROOM', 'kWh', 3000, 1),
(4, 'Nước sinh hoạt', 'Nước', 'ROOM', 'm3', 7000, 1),
(5, 'Phòng ở', 'Phòng ở', 'PERSONAL', 'tháng', 1000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `service_usage`
--

CREATE TABLE `service_usage` (
  `usage_id` bigint(20) NOT NULL,
  `current_reading` double DEFAULT NULL,
  `invoiced` enum('NO','YES') NOT NULL,
  `previous_reading` double DEFAULT NULL,
  `record_date` date DEFAULT NULL,
  `bill_id` bigint(20) DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) NOT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service_usage`
--

INSERT INTO `service_usage` (`usage_id`, `current_reading`, `invoiced`, `previous_reading`, `record_date`, `bill_id`, `room_id`, `service_id`, `student_id`) VALUES
(1, 150, 'NO', 250, '2025-03-26', NULL, 1, 3, NULL),
(2, 60, 'NO', 50, '2025-03-26', NULL, 1, 4, NULL),
(3, 150, 'NO', 250, '2025-03-26', NULL, 2, 3, NULL),
(4, 60, 'NO', 50, '2025-03-26', NULL, 2, 4, NULL),
(6, 50, 'YES', 0, '2024-10-18', 9, 1, 3, NULL),
(7, 10, 'YES', 0, '2024-10-18', 9, 1, 4, NULL),
(8, 50, 'YES', 0, '2024-10-18', 10, 2, 3, NULL),
(9, 10, 'YES', 0, '2024-10-18', 10, 2, 4, NULL),
(10, 50, 'YES', 0, '2024-10-18', 11, 3, 3, NULL),
(11, 10, 'YES', 0, '2024-10-18', 11, 3, 4, NULL),
(12, 50, 'YES', 0, '2024-10-18', 12, 4, 3, NULL),
(13, 10, 'YES', 0, '2024-10-18', 12, 4, 4, NULL),
(14, 100, 'YES', 50, '2024-11-18', 13, 1, 3, NULL),
(15, 20, 'YES', 10, '2024-11-18', 13, 1, 4, NULL),
(16, 100, 'YES', 50, '2024-11-18', 14, 2, 3, NULL),
(17, 20, 'YES', 10, '2024-11-18', 14, 2, 4, NULL),
(18, 100, 'YES', 50, '2024-11-18', 15, 3, 3, NULL),
(19, 20, 'YES', 10, '2024-11-18', 15, 3, 4, NULL),
(20, 100, 'YES', 50, '2024-11-18', 16, 4, 3, NULL),
(21, 20, 'YES', 10, '2024-11-18', 16, 4, 4, NULL),
(22, 150, 'YES', 100, '2024-12-18', 17, 1, 3, NULL),
(23, 30, 'YES', 20, '2024-12-18', 17, 1, 4, NULL),
(24, 150, 'YES', 100, '2024-12-18', 18, 2, 3, NULL),
(25, 30, 'YES', 20, '2024-12-18', 18, 2, 4, NULL),
(26, 150, 'YES', 100, '2024-12-18', 19, 3, 3, NULL),
(27, 30, 'YES', 20, '2024-12-18', 19, 3, 4, NULL),
(28, 150, 'YES', 100, '2024-12-18', 20, 4, 3, NULL),
(29, 30, 'YES', 20, '2024-12-18', 20, 4, 4, NULL),
(30, 200, 'YES', 150, '2025-01-18', 21, 1, 3, NULL),
(31, 40, 'YES', 30, '2025-01-18', 21, 1, 4, NULL),
(32, 200, 'YES', 150, '2025-01-18', 22, 2, 3, NULL),
(33, 40, 'YES', 30, '2025-01-18', 22, 2, 4, NULL),
(34, 200, 'YES', 150, '2025-01-18', 23, 3, 3, NULL),
(35, 40, 'YES', 30, '2025-01-18', 23, 3, 4, NULL),
(36, 200, 'YES', 150, '2025-01-18', 24, 4, 3, NULL),
(37, 40, 'YES', 30, '2025-01-18', 24, 4, 4, NULL),
(38, 250, 'YES', 200, '2025-02-18', 25, 1, 3, NULL),
(39, 50, 'YES', 40, '2025-02-18', 25, 1, 4, NULL),
(40, 250, 'YES', 200, '2025-02-18', 26, 2, 3, NULL),
(41, 50, 'YES', 40, '2025-02-18', 26, 2, 4, NULL),
(42, 250, 'YES', 200, '2025-02-18', 27, 3, 3, NULL),
(43, 50, 'YES', 40, '2025-02-18', 27, 3, 4, NULL),
(44, 250, 'YES', 200, '2025-02-18', 28, 4, 3, NULL),
(45, 50, 'YES', 40, '2025-02-18', 28, 4, 4, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `phone_number` varchar(50) DEFAULT NULL,
  `student_class` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `address`, `date_of_birth`, `department`, `email`, `full_name`, `gender`, `phone_number`, `student_class`) VALUES
(4, '123 ABC St', '2003-05-10', 'CNTT', 'student4@univ.edu', 'Student Four', 'Nam', '0901000004', 'CNTT1'),
(5, '123 ABC St', '2003-04-11', 'CNTT', 'student5@univ.edu', 'Student Five', 'Nam', '0901000005', 'CNTT1'),
(6, '234 BCD St', '2002-03-12', 'CNTT', 'student6@univ.edu', 'Student Six', 'Nam', '0901000006', 'CNTT2'),
(7, '234 BCD St', '2002-02-05', 'CNTT', 'student7@univ.edu', 'Student Seven', 'Nữ', '0901000007', 'CNTT2'),
(8, '345 CDE St', '2003-01-05', 'KinhTe', 'student8@univ.edu', 'Student Eight', 'Nữ', '0901000008', 'KT1'),
(9, '345 CDE St', '2003-08-09', 'KinhTe', 'student9@univ.edu', 'Student Nine', 'Nam', '0901000009', 'KT1'),
(10, '456 DEF St', '2003-07-07', 'KinhTe', 'student10@univ.edu', 'Student Ten', 'Nữ', '0901000010', 'KT2'),
(11, '456 DEF St', '2003-06-08', 'CNTT', 'student11@univ.edu', 'Student Eleven', 'Nam', '0901000011', 'CNTT3'),
(12, '567 EFG St', '2004-12-01', 'XHNV', 'student12@univ.edu', 'Student Twelve', 'Nữ', '0901000012', 'XH1'),
(13, '567 EFG St', '2002-11-02', 'XHNV', 'student13@univ.edu', 'Student Thirteen', 'Nữ', '0901000013', 'XH1'),
(14, '678 FGH St', '2002-10-03', 'KinhTe', 'student14@univ.edu', 'Student Fourteen', 'Nam', '0901000014', 'KT3'),
(15, '789 GHI St', '2002-09-04', 'XHNV', 'student15@univ.edu', 'Student Fifteen', 'Nữ', '0901000015', 'XH2'),
(16, '789 GHI St', '2002-08-05', 'CNTT', 'student16@univ.edu', 'Student Sixteen', 'Nam', '0901000016', 'CNTT4'),
(17, '876 HIJ St', '2002-07-06', 'KinhTe', 'student17@univ.edu', 'Student Seventeen', 'Nữ', '0901000017', 'KT4'),
(18, '987 IJK St', '2002-06-07', 'KinhTe', 'student18@univ.edu', 'Student Eighteen', 'Nam', '0901000018', 'KT5'),
(19, '987 IJK St', '2002-05-08', 'CNTT', 'student19@univ.edu', 'Student Nineteen', 'Nam', '0901000019', 'CNTT5'),
(20, '987 IJK St', '2002-12-25', 'KinhTe', 'nh.minh0403@gmail.com', 'Student Twenty', 'Nữ', '0901000020', 'KT7'),
(21, '123 New St', '2003-03-01', 'CNTT', 'student21@univ.edu', 'Student Twenty One', 'Nam', '0901000021', 'CNTT1'),
(22, '123 New St', '2003-03-02', 'CNTT', 'student22@univ.edu', 'Student Twenty Two', 'Nữ', '0901000022', 'CNTT1'),
(23, '234 New St', '2003-03-03', 'KinhTe', 'student23@univ.edu', 'Student Twenty Three', 'Nam', '0901000023', 'KT1'),
(24, '234 New St', '2003-03-04', 'KinhTe', 'student24@univ.edu', 'Student Twenty Four', 'Nữ', '0901000024', 'KT1'),
(25, '345 New St', '2003-03-05', 'XHNV', 'student25@univ.edu', 'Student Twenty Five', 'Nam', '0901000025', 'XH1'),
(26, '345 New St', '2003-03-06', 'XHNV', 'student26@univ.edu', 'Student Twenty Six', 'Nữ', '0901000026', 'XH1'),
(27, '456 New St', '2003-03-07', 'CNTT', 'student27@univ.edu', 'Student Twenty Seven', 'Nam', '0901000027', 'CNTT2'),
(28, '456 New St', '2003-03-08', 'CNTT', 'student28@univ.edu', 'Student Twenty Eight', 'Nữ', '0901000028', 'CNTT2'),
(29, '567 New St', '2003-03-09', 'KinhTe', 'student29@univ.edu', 'Student Twenty Nine', 'Nam', '0901000029', 'KT2'),
(30, '567 New St', '2003-03-10', 'KinhTe', 'student30@univ.edu', 'Student Thirty', 'Nữ', '0901000030', 'KT2');

-- --------------------------------------------------------

--
-- Table structure for table `student_service_registrations`
--

CREATE TABLE `student_service_registrations` (
  `registration_id` bigint(20) NOT NULL,
  `actual_quantity` int(11) DEFAULT NULL,
  `approval_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date NOT NULL,
  `status` enum('pending','approved','rejected') DEFAULT 'pending',
  `approved_by` bigint(20) DEFAULT NULL,
  `service_id` bigint(20) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `invoiced` enum('NO','YES') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_service_registrations`
--

INSERT INTO `student_service_registrations` (`registration_id`, `actual_quantity`, `approval_date`, `end_date`, `start_date`, `status`, `approved_by`, `service_id`, `student_id`, `invoiced`) VALUES
(1, 1, NULL, NULL, '2025-04-25', 'pending', NULL, 2, 4, 'NO'),
(2, 1, NULL, NULL, '2025-04-24', 'pending', NULL, 2, 5, 'NO'),
(3, 1, NULL, NULL, '2025-04-23', 'pending', NULL, 2, 6, 'NO'),
(4, 1, NULL, NULL, '2025-04-22', 'pending', NULL, 2, 7, 'NO'),
(5, 1, NULL, NULL, '2025-04-21', 'pending', NULL, 2, 8, 'NO'),
(6, 1, NULL, NULL, '2025-04-20', 'pending', NULL, 2, 10, 'NO'),
(7, NULL, '2025-04-26', NULL, '2025-04-25', 'approved', 1, 2, 4, 'NO'),
(8, NULL, '2025-04-26', NULL, '2025-04-24', 'approved', 1, 2, 5, 'NO'),
(9, NULL, '2025-04-26', NULL, '2025-04-23', 'approved', 1, 2, 6, 'NO'),
(10, NULL, '2025-04-26', NULL, '2025-04-22', 'approved', 1, 2, 7, 'NO'),
(11, NULL, '2025-04-26', NULL, '2025-04-21', 'approved', 1, 2, 8, 'NO'),
(12, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 4, 'YES'),
(13, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 5, 'YES'),
(14, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 6, 'YES'),
(15, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 7, 'YES'),
(16, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 8, 'YES'),
(17, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 10, 'YES'),
(18, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 4, 'YES'),
(19, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 5, 'YES'),
(20, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 6, 'YES'),
(21, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 7, 'YES'),
(22, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 8, 'YES'),
(23, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 10, 'YES'),
(24, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 4, 'YES'),
(25, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 5, 'YES'),
(26, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 6, 'YES'),
(27, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 7, 'YES'),
(28, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 8, 'YES'),
(29, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 10, 'YES'),
(30, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 4, 'YES'),
(31, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 5, 'YES'),
(32, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 6, 'YES'),
(33, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 7, 'YES'),
(34, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 8, 'YES'),
(35, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 10, 'YES'),
(36, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 4, 'YES'),
(37, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 5, 'YES'),
(38, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 6, 'YES'),
(39, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 7, 'YES'),
(40, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 8, 'YES'),
(41, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 10, 'YES');

-- --------------------------------------------------------

--
-- Stand-in structure for view `student_service_requests`
-- (See below for the actual view)
--
CREATE TABLE `student_service_requests` (
`student_id` bigint(20)
,`full_name` varchar(100)
,`email` varchar(100)
,`gender` varchar(20)
,`phone_number` varchar(50)
,`student_class` varchar(50)
,`department` varchar(100)
,`date_of_birth` date
,`address` varchar(255)
,`room_id` bigint(20)
,`assigned_date` date
,`room_end_date` date
,`registration_id` bigint(20)
,`service_start_date` date
,`service_end_date` date
,`service_status` enum('pending','approved','rejected')
,`service_name` varchar(255)
);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('manager','student') NOT NULL,
  `status` enum('active','inactive') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `created_at`, `password_hash`, `role`, `status`, `updated_at`, `username`) VALUES
(1, '2025-01-01 10:00:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'manager', 'active', '2025-01-02 10:00:00.000000', 'manager1'),
(2, '2025-01-01 10:10:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'manager', 'active', '2025-01-02 10:10:00.000000', 'manager2'),
(3, '2025-01-01 10:20:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'manager', 'active', '2025-01-02 10:20:00.000000', 'manager3'),
(4, '2025-01-01 11:00:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:00:00.000000', 'student4'),
(5, '2025-01-01 11:10:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:10:00.000000', 'student5'),
(6, '2025-01-01 11:20:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:20:00.000000', 'student6'),
(7, '2025-01-01 11:30:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:30:00.000000', 'student7'),
(8, '2025-01-01 11:40:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:40:00.000000', 'student8'),
(9, '2025-01-01 11:50:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:50:00.000000', 'student9'),
(10, '2025-01-01 12:00:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 12:00:00.000000', 'student10'),
(11, '2025-01-01 12:10:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 12:10:00.000000', 'student11'),
(12, '2025-01-01 12:20:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 12:20:00.000000', 'student12'),
(13, '2025-01-01 12:30:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 12:30:00.000000', 'student13'),
(14, '2025-01-01 12:40:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 12:40:00.000000', 'student14'),
(15, '2025-01-01 12:50:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 12:50:00.000000', 'student15'),
(16, '2025-01-01 13:00:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 13:00:00.000000', 'student16'),
(17, '2025-01-01 13:10:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 13:10:00.000000', 'student17'),
(18, '2025-01-01 13:20:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 13:20:00.000000', 'student18'),
(19, '2025-01-01 13:30:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 13:30:00.000000', 'student19'),
(20, '2025-01-01 13:40:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 13:40:00.000000', 'student20'),
(21, '2025-01-02 14:00:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:00:00.000000', 'student21'),
(22, '2025-01-02 14:10:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:10:00.000000', 'student22'),
(23, '2025-01-02 14:20:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:20:00.000000', 'student23'),
(24, '2025-01-02 14:30:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:30:00.000000', 'student24'),
(25, '2025-01-02 14:40:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:40:00.000000', 'student25'),
(26, '2025-01-02 14:50:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:50:00.000000', 'student26'),
(27, '2025-01-02 15:00:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:00:00.000000', 'student27'),
(28, '2025-01-02 15:10:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:10:00.000000', 'student28'),
(29, '2025-01-02 15:20:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:20:00.000000', 'student29'),
(30, '2025-01-02 15:30:00.000000', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:30:00.000000', 'student30');

-- --------------------------------------------------------

--
-- Table structure for table `utility_billing_schedule`
--

CREATE TABLE `utility_billing_schedule` (
  `schedule_id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `schedule_time` datetime(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure for view `approved_applications`
--
DROP TABLE IF EXISTS `approved_applications`;

CREATE ALGORITHM=UNDEFINED DEFINER=`` SQL SECURITY DEFINER VIEW `approved_applications`  AS SELECT `a`.`application_id` AS `application_id`, `s`.`student_id` AS `student_id`, `s`.`full_name` AS `full_name`, `s`.`date_of_birth` AS `date_of_birth`, `s`.`gender` AS `gender`, `s`.`department` AS `department`, `s`.`student_class` AS `student_class`, `s`.`phone_number` AS `phone_number`, `s`.`email` AS `email`, `s`.`address` AS `address`, `a`.`submission_date` AS `submission_date`, `a`.`status` AS `status`, `a`.`approved_by` AS `approved_by`, `a`.`approval_date` AS `approval_date`, `a`.`dormitory_area` AS `dormitory_area`, `a`.`note` AS `note` FROM (`applications` `a` join `students` `s` on(`a`.`student_id` = `s`.`student_id`)) WHERE `a`.`status` = 'approved' ;

-- --------------------------------------------------------

--
-- Structure for view `approved_student_service_requests`
--
DROP TABLE IF EXISTS `approved_student_service_requests`;

CREATE ALGORITHM=UNDEFINED DEFINER=`` SQL SECURITY DEFINER VIEW `approved_student_service_requests`  AS SELECT `s`.`student_id` AS `student_id`, `s`.`full_name` AS `full_name`, `s`.`email` AS `email`, `s`.`gender` AS `gender`, `s`.`phone_number` AS `phone_number`, `s`.`student_class` AS `student_class`, `s`.`department` AS `department`, `s`.`date_of_birth` AS `date_of_birth`, `s`.`address` AS `address`, `ra`.`room_id` AS `room_id`, `ra`.`assigned_date` AS `assigned_date`, `ra`.`end_date` AS `room_end_date`, `ssr`.`registration_id` AS `registration_id`, `ssr`.`start_date` AS `service_start_date`, `ssr`.`end_date` AS `service_end_date`, `ssr`.`status` AS `service_status`, `ssr`.`actual_quantity` AS `actual_quantity`, `sv`.`service_name` AS `service_name` FROM (((`students` `s` left join `room_assignments` `ra` on(`s`.`student_id` = `ra`.`student_id`)) join `student_service_registrations` `ssr` on(`s`.`student_id` = `ssr`.`student_id`)) join `services` `sv` on(`ssr`.`service_id` = `sv`.`service_id`)) WHERE `ssr`.`status` = 'approved' AND `ssr`.`invoiced` = 'NO' ;

-- --------------------------------------------------------

--
-- Structure for view `pending_applications`
--
DROP TABLE IF EXISTS `pending_applications`;

CREATE ALGORITHM=UNDEFINED DEFINER=`` SQL SECURITY DEFINER VIEW `pending_applications`  AS SELECT `a`.`application_id` AS `application_id`, `a`.`submission_date` AS `submission_date`, `a`.`approval_date` AS `approval_date`, `a`.`dormitory_area` AS `dormitory_area`, `s`.`full_name` AS `full_name`, `s`.`address` AS `address`, `s`.`department` AS `department`, `s`.`phone_number` AS `phone_number` FROM (`applications` `a` join `students` `s` on(`a`.`student_id` = `s`.`student_id`)) WHERE `a`.`status` = 'pending' ;

-- --------------------------------------------------------

--
-- Structure for view `room_list`
--
DROP TABLE IF EXISTS `room_list`;

CREATE ALGORITHM=UNDEFINED DEFINER=`` SQL SECURITY DEFINER VIEW `room_list`  AS SELECT `r`.`room_id` AS `room_id`, `r`.`room_number` AS `room_number`, `r`.`description` AS `room_description`, `r`.`status` AS `status`, `r`.`current_occupancy` AS `current_occupancy`, `rt`.`max_capacity` AS `room_max_capacity`, `rt`.`type_name` AS `room_type_name`, `rt`.`price` AS `room_type_price`, `d`.`dorm_name` AS `dorm_name`, `d`.`address` AS `dorm_address`, `d`.`description` AS `dorm_description`, `r`.`room_type_id` AS `room_type_id`, `r`.`dorm_id` AS `dorm_id` FROM ((`rooms` `r` join `room_types` `rt` on(`r`.`room_type_id` = `rt`.`room_type_id`)) join `dormitories` `d` on(`r`.`dorm_id` = `d`.`dorm_id`)) ;

-- --------------------------------------------------------

--
-- Structure for view `student_service_requests`
--
DROP TABLE IF EXISTS `student_service_requests`;

CREATE ALGORITHM=UNDEFINED DEFINER=`` SQL SECURITY DEFINER VIEW `student_service_requests`  AS SELECT `s`.`student_id` AS `student_id`, `s`.`full_name` AS `full_name`, `s`.`email` AS `email`, `s`.`gender` AS `gender`, `s`.`phone_number` AS `phone_number`, `s`.`student_class` AS `student_class`, `s`.`department` AS `department`, `s`.`date_of_birth` AS `date_of_birth`, `s`.`address` AS `address`, `ra`.`room_id` AS `room_id`, `ra`.`assigned_date` AS `assigned_date`, `ra`.`end_date` AS `room_end_date`, `ssr`.`registration_id` AS `registration_id`, `ssr`.`start_date` AS `service_start_date`, `ssr`.`end_date` AS `service_end_date`, `ssr`.`status` AS `service_status`, `sv`.`service_name` AS `service_name` FROM (((`students` `s` left join `room_assignments` `ra` on(`s`.`student_id` = `ra`.`student_id`)) join `student_service_registrations` `ssr` on(`s`.`student_id` = `ssr`.`student_id`)) join `services` `sv` on(`ssr`.`service_id` = `sv`.`service_id`)) WHERE `ssr`.`status` <> 'approved' ORDER BY `ssr`.`start_date` DESC ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `applications`
--
ALTER TABLE `applications`
  ADD PRIMARY KEY (`application_id`),
  ADD KEY `FKb2lc6fpwli1wgn8ftbbwcpec2` (`approved_by`),
  ADD KEY `FKov0s7340wdyk5o43l68ap02vg` (`dorm_id`),
  ADD KEY `FKbxjuiec753shgoyw6x0l8opn8` (`student_id`);

--
-- Indexes for table `billing_schedules`
--
ALTER TABLE `billing_schedules`
  ADD PRIMARY KEY (`schedule_id`);

--
-- Indexes for table `bills`
--
ALTER TABLE `bills`
  ADD PRIMARY KEY (`bill_id`),
  ADD KEY `FK8s4psg8ktsl6d0skeh8inl4ms` (`room_id`),
  ADD KEY `FK50pgvyni32dj5bi4wdj6lh0vu` (`student_id`);

--
-- Indexes for table `bill_items`
--
ALTER TABLE `bill_items`
  ADD PRIMARY KEY (`bill_item_id`),
  ADD KEY `FKj9o7g8krc56gf6t6f0sy4ic5p` (`bill_id`),
  ADD KEY `FKpmju93c2sd04wsuxc7u4w3q8d` (`service_id`),
  ADD KEY `FK6n5upeipchlyetaa3stqqvbl4` (`registration_id`);

--
-- Indexes for table `dormitories`
--
ALTER TABLE `dormitories`
  ADD PRIMARY KEY (`dorm_id`);

--
-- Indexes for table `managers`
--
ALTER TABLE `managers`
  ADD PRIMARY KEY (`manager_id`);

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `FKlchct0805t9w7mapbehsi6dcw` (`student_id`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `FK9565r6579khpdjxnyla0l2ycd` (`bill_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`room_id`),
  ADD KEY `FK4mmk4h0ymtdest0iuowicpkpe` (`dorm_id`),
  ADD KEY `FKh9m2n1paq5hmd3u0klfl7wsfv` (`room_type_id`);

--
-- Indexes for table `room_assignments`
--
ALTER TABLE `room_assignments`
  ADD PRIMARY KEY (`room_id`,`student_id`),
  ADD KEY `FK67vqh51xh9aa8ef4dlf6b68lh` (`student_id`);

--
-- Indexes for table `room_types`
--
ALTER TABLE `room_types`
  ADD PRIMARY KEY (`room_type_id`),
  ADD KEY `FK14tu3j5tewldlsgynosphmkqn` (`dorm_id`);

--
-- Indexes for table `services`
--
ALTER TABLE `services`
  ADD PRIMARY KEY (`service_id`);

--
-- Indexes for table `service_usage`
--
ALTER TABLE `service_usage`
  ADD PRIMARY KEY (`usage_id`),
  ADD KEY `FK7g8shnsjor31x62t18ml92589` (`bill_id`),
  ADD KEY `FK57ibeqnu7p2c89d2ca8njfadb` (`room_id`),
  ADD KEY `FKsp7vf00mxc26jt67axd6bg3en` (`service_id`),
  ADD KEY `FK4wai8s5plmo360m1s1kf4wiw5` (`student_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`);

--
-- Indexes for table `student_service_registrations`
--
ALTER TABLE `student_service_registrations`
  ADD PRIMARY KEY (`registration_id`),
  ADD KEY `FK8tbn7pjy8gclx9ix15wnqtrlv` (`approved_by`),
  ADD KEY `FKir6bbqwjyjr0k77c5ym2b8fmu` (`service_id`),
  ADD KEY `FKs4c1cl89hxefunk6fm6t3nkeo` (`student_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Indexes for table `utility_billing_schedule`
--
ALTER TABLE `utility_billing_schedule`
  ADD PRIMARY KEY (`schedule_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `applications`
--
ALTER TABLE `applications`
  MODIFY `application_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `billing_schedules`
--
ALTER TABLE `billing_schedules`
  MODIFY `schedule_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bills`
--
ALTER TABLE `bills`
  MODIFY `bill_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT for table `bill_items`
--
ALTER TABLE `bill_items`
  MODIFY `bill_item_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT for table `dormitories`
--
ALTER TABLE `dormitories`
  MODIFY `dorm_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `payment_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `room_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `room_types`
--
ALTER TABLE `room_types`
  MODIFY `room_type_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `services`
--
ALTER TABLE `services`
  MODIFY `service_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `service_usage`
--
ALTER TABLE `service_usage`
  MODIFY `usage_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT for table `student_service_registrations`
--
ALTER TABLE `student_service_registrations`
  MODIFY `registration_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `utility_billing_schedule`
--
ALTER TABLE `utility_billing_schedule`
  MODIFY `schedule_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `applications`
--
ALTER TABLE `applications`
  ADD CONSTRAINT `FKb2lc6fpwli1wgn8ftbbwcpec2` FOREIGN KEY (`approved_by`) REFERENCES `managers` (`manager_id`),
  ADD CONSTRAINT `FKbxjuiec753shgoyw6x0l8opn8` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `FKov0s7340wdyk5o43l68ap02vg` FOREIGN KEY (`dorm_id`) REFERENCES `dormitories` (`dorm_id`);

--
-- Constraints for table `bills`
--
ALTER TABLE `bills`
  ADD CONSTRAINT `FK50pgvyni32dj5bi4wdj6lh0vu` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `FK8s4psg8ktsl6d0skeh8inl4ms` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`);

--
-- Constraints for table `bill_items`
--
ALTER TABLE `bill_items`
  ADD CONSTRAINT `FK6n5upeipchlyetaa3stqqvbl4` FOREIGN KEY (`registration_id`) REFERENCES `student_service_registrations` (`registration_id`),
  ADD CONSTRAINT `FKj9o7g8krc56gf6t6f0sy4ic5p` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`bill_id`),
  ADD CONSTRAINT `FKpmju93c2sd04wsuxc7u4w3q8d` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`);

--
-- Constraints for table `managers`
--
ALTER TABLE `managers`
  ADD CONSTRAINT `FKse3yw51u49ns29jk2m73ui715` FOREIGN KEY (`manager_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `FKlchct0805t9w7mapbehsi6dcw` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `FK9565r6579khpdjxnyla0l2ycd` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`bill_id`);

--
-- Constraints for table `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `FK4mmk4h0ymtdest0iuowicpkpe` FOREIGN KEY (`dorm_id`) REFERENCES `dormitories` (`dorm_id`),
  ADD CONSTRAINT `FKh9m2n1paq5hmd3u0klfl7wsfv` FOREIGN KEY (`room_type_id`) REFERENCES `room_types` (`room_type_id`);

--
-- Constraints for table `room_assignments`
--
ALTER TABLE `room_assignments`
  ADD CONSTRAINT `FK67vqh51xh9aa8ef4dlf6b68lh` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `FKt96wkyclodjlrg52xftxyve1h` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`);

--
-- Constraints for table `room_types`
--
ALTER TABLE `room_types`
  ADD CONSTRAINT `FK14tu3j5tewldlsgynosphmkqn` FOREIGN KEY (`dorm_id`) REFERENCES `dormitories` (`dorm_id`);

--
-- Constraints for table `service_usage`
--
ALTER TABLE `service_usage`
  ADD CONSTRAINT `FK4wai8s5plmo360m1s1kf4wiw5` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  ADD CONSTRAINT `FK57ibeqnu7p2c89d2ca8njfadb` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`),
  ADD CONSTRAINT `FK7g8shnsjor31x62t18ml92589` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`bill_id`),
  ADD CONSTRAINT `FKsp7vf00mxc26jt67axd6bg3en` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `FKnl1q3iljhy1184875o5nu9ryy` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `student_service_registrations`
--
ALTER TABLE `student_service_registrations`
  ADD CONSTRAINT `FK8tbn7pjy8gclx9ix15wnqtrlv` FOREIGN KEY (`approved_by`) REFERENCES `managers` (`manager_id`),
  ADD CONSTRAINT `FKir6bbqwjyjr0k77c5ym2b8fmu` FOREIGN KEY (`service_id`) REFERENCES `services` (`service_id`),
  ADD CONSTRAINT `FKs4c1cl89hxefunk6fm6t3nkeo` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
