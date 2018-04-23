-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 12, 2018 at 11:45 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `sleep`
--

CREATE TABLE `sleep` (
  `sleepID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `bedTime` datetime NOT NULL,
  `wakeTime` datetime NOT NULL,
  `sleepGrade` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `firstname` varchar(40) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `gender` enum('m','f') NOT NULL,
  `postcode` varchar(8) NOT NULL,
  `nationality` varchar(40) NOT NULL,
  `email` varchar(60) NOT NULL,
  `height` double NOT NULL,
  `dob` date NOT NULL,
  `exerciseLevel` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `username`, `password`, `firstname`, `lastname`, `gender`, `postcode`, `nationality`, `email`, `height`, `dob`, `exerciseLevel`) VALUES
(1, 'parkesy', '81dc9bdb52d04dc20036dbd8313ed055', 'Matt', 'Parkes', 'm', 'NR59NZ', 'belarusian', 'matt.parkes@outlook.com', 186.5, '1996-10-10', 1.375),
(2, 'dsaiuf', '81dc9bdb52d04dc20036dbd8313ed055', 'hi', 'yo', 'f', 'dsfsdfds', 'barbadian', 'matt.parkes@outlook.com', 232, '0000-00-00', 1),
(4, 'asdasdasd', '202cb962ac59075b964b07152d234b70', 'dad', 'dad', 'f', 'pe29 gkn', '', 'asdasd@Asdasd.com', 1, '0000-00-00', 1),
(7, 'kappa1', '202cb962ac59075b964b07152d234b70', 'mum', 'mum', 'm', 'asdasd', 'barbudans', 'asd@asd.com', 1, '0000-00-00', 1),
(9, 'adasd', '202cb962ac59075b964b07152d234b70', 'dsedcqdf', 'fsdfsd', 'm', 'asdas', 'barbadian', 'asdas@dfsd.com', 323, '0000-00-00', 1),
(10, 'test', '202cb962ac59075b964b07152d234b70', 'qweq', 'qweqw', 'm', 'dasd', 'barbadian', 'asdas@fsdf.com', 23, '0000-00-00', 1),
(11, 'test2', '202cb962ac59075b964b07152d234b70', 'sdf', 'fgsdgd', 'm', 'tyjty', 'barbadian', 'fwef@dfgdf.com', 43, '0000-00-00', 1),
(12, 'parkesy2', '202cb962ac59075b964b07152d234b70', 'csdmkl', 'vmsdcfkl', 'm', 'cm233ng', 'albanian', 'wefjiowefh@sdfbh.com', 32, '0000-00-00', 1.725),
(13, 'test5', '36c5ac34a0dcf8de9344a6fb7a5c4648', 'Hello', 'there', 'm', 'sdfsd', 'american', 'matt.parkes@outlook.com', 182, '1996-10-10', 1.9);

-- --------------------------------------------------------

--
-- Table structure for table `weight`
--

CREATE TABLE `weight` (
  `weightID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `weight` decimal(10,0) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `weight`
--

INSERT INTO `weight` (`weightID`, `userID`, `weight`, `date`) VALUES
(2, 1, '90', '2018-03-16'),
(8, 1, '82', '2018-03-22'),
(9, 1, '84', '2018-03-07'),
(12, 1, '82', '2018-03-18'),
(13, 1, '84', '2018-03-24'),
(14, 1, '85', '2018-03-25'),
(15, 13, '100', '2018-03-10');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `sleep`
--
ALTER TABLE `sleep`
  ADD PRIMARY KEY (`sleepID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `weight`
--
ALTER TABLE `weight`
  ADD PRIMARY KEY (`weightID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sleep`
--
ALTER TABLE `sleep`
  MODIFY `sleepID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `weight`
--
ALTER TABLE `weight`
  MODIFY `weightID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
