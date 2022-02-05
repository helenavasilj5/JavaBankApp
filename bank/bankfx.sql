-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 05, 2022 at 07:21 PM
-- Server version: 10.4.18-MariaDB
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bankfx`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `idaccount` int(11) NOT NULL,
  `account_balance` decimal(19,2) NOT NULL,
  `currency` varchar(40) NOT NULL,
  `account_number` varchar(40) NOT NULL,
  `user_fk` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`idaccount`, `account_balance`, `currency`, `account_number`, `user_fk`) VALUES
(1, '992.75', 'BAM', '11111', 1),
(2, '1181.56', 'BAM', '22222', 2),
(3, '14.22', 'BAM', '00000', 3);

-- --------------------------------------------------------

--
-- Table structure for table `loglogin`
--

CREATE TABLE `loglogin` (
  `idloglogin` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL,
  `role` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `sucessful` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `loglogin`
--

INSERT INTO `loglogin` (`idloglogin`, `name`, `username`, `role`, `email`, `sucessful`) VALUES
(13, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', 'Y'),
(14, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(16, '', 'ana', '', '', 'N'),
(17, '', 'ana', '', '', 'N'),
(18, '', 'bojan', '', '', 'N'),
(19, 'Bojan', 'bojan', 'ADMIN', 'bojansudar@gmail.com', 'Y'),
(20, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(21, 'Bojan', 'bojan', 'ADMIN', 'bojansudar@gmail.com', 'Y'),
(22, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(23, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', 'Y'),
(24, '', 'danijel', '', '', 'N'),
(25, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(26, '', 'fasvf', '', '', 'N'),
(27, '', 'fasvf', '', '', 'N'),
(28, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(29, 'Name', 'name', 'USER', 'name@gmail.com', 'Y'),
(30, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(31, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(32, 'Bojan', 'bojan', 'ADMIN', 'bojansudar@gmail.com', 'Y'),
(33, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', 'Y'),
(34, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', 'Y'),
(35, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', 'Y'),
(36, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', 'Y'),
(37, '', 'a or 1=1 or ', '', '', 'N'),
(38, 'Bojan', 'bojan', 'ADMIN', 'bojansudar@gmail.com', 'Y'),
(39, 'Bojan', 'bojan', 'ADMIN', 'bojansudar@gmail.com', 'Y'),
(44, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', 'Y');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `idtransaction` int(11) NOT NULL,
  `currency_of_transaction` varchar(40) NOT NULL,
  `date_of_transaction` timestamp NOT NULL DEFAULT current_timestamp(),
  `reciver_acc_number` varchar(40) NOT NULL,
  `sender_acc_number` varchar(40) NOT NULL,
  `amount` decimal(19,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`idtransaction`, `currency_of_transaction`, `date_of_transaction`, `reciver_acc_number`, `sender_acc_number`, `amount`) VALUES
(3, 'BAM', '2022-01-31 10:28:19', '11111', '22222', '100.00'),
(4, 'BAM', '2022-02-04 10:27:37', '11111', '22222', '100.00'),
(5, 'BAM', '2022-02-04 10:29:52', '22222', '11111', '100.00'),
(6, 'BAM', '2022-02-04 10:46:52', '11111', '22222', '195.58'),
(7, 'BAM', '2022-02-04 10:51:37', '22222', '33333', '195.58'),
(8, 'BAM', '2022-02-04 11:15:22', '11111', '22222', '100.00'),
(9, 'BAM', '2022-02-05 16:31:17', '22222', '11111', '293.37');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `idusers` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL,
  `role` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idusers`, `name`, `username`, `role`, `email`, `password`) VALUES
(1, 'Helena', 'helena', 'USER', 'helenavasilj@gmail.com', '123'),
(2, 'Iva', 'iva', 'USER', 'ivastojcic@gmail.com', '123'),
(3, 'Bojan', 'bojan', 'ADMIN', 'bojansudar@gmail.com', '123');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`idaccount`);

--
-- Indexes for table `loglogin`
--
ALTER TABLE `loglogin`
  ADD PRIMARY KEY (`idloglogin`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`idtransaction`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idusers`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `idaccount` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `loglogin`
--
ALTER TABLE `loglogin`
  MODIFY `idloglogin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `idtransaction` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `idusers` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
