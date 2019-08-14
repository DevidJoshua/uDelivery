-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2019 at 10:09 AM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_udelivery`
--

DELIMITER $$
--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `CALCULATE_DISTANCE`(`@oLat` DOUBLE, `@oLon` DOUBLE, `@dLat` DOUBLE, `@dLon` DOUBLE) RETURNS decimal(10,7)
    NO SQL
BEGIN
	RETURN (
		(
			ACOS(
				SIN(`@dLat` * PI() / 180) *
				SIN(`@oLat` * PI() / 180) +
				COS(`@dLat` * PI() / 180) *
				COS(`@oLat` * PI() / 180) *
				COS((`@dLon` - `@oLon`) * PI() / 180)
			) *
			180 / PI()
		) *
		60 *
		1.1515 *
		1.609344
	);
    END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `tb_courier`
--

CREATE TABLE IF NOT EXISTS `tb_courier` (
  `cour_email` varchar(255) NOT NULL,
  `cour_name` varchar(15) NOT NULL,
  `cour_phone` char(13) NOT NULL,
  `cour_password` varchar(255) NOT NULL,
  `cour_status` enum('Registered','Unregistered','Suspended','Banned') NOT NULL,
  `cour_pos_latitude` double DEFAULT NULL,
  `cour_pos_longtitude` double DEFAULT NULL,
  `cour_vehicle_type` enum('Car','Motocycle','','') NOT NULL,
  `cour_vehicle_number` varchar(255) NOT NULL,
  `cour_vehicle_name` varchar(255) NOT NULL,
  `cour_image` text NOT NULL,
  `cour_state` enum('Available','Booked','Offline','') NOT NULL,
  `cour_order_handle` varchar(255) NOT NULL DEFAULT 'N/A'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_courier`
--

INSERT INTO `tb_courier` (`cour_email`, `cour_name`, `cour_phone`, `cour_password`, `cour_status`, `cour_pos_latitude`, `cour_pos_longtitude`, `cour_vehicle_type`, `cour_vehicle_number`, `cour_vehicle_name`, `cour_image`, `cour_state`, `cour_order_handle`) VALUES
('alo@gmail.com', 'Alo', '0813411115', '123', 'Registered', 1.4167682, 124.9848886, 'Car', 'DB1243', 'Avanza', 'vex5.jpg', 'Available', 'lWjo9uI'),
('ungke@gmail.com', 'Ungke', '089992383838', '123', 'Registered', 1.4167682, 124.9848886, 'Car', 'DB930030', 'Xenia vvti', 'ungke.jpg', 'Offline', 'N/A');

-- --------------------------------------------------------

--
-- Table structure for table `tb_customer`
--

CREATE TABLE IF NOT EXISTS `tb_customer` (
  `cust_nim` varchar(10) NOT NULL,
  `cust_name` varchar(15) NOT NULL,
  `cust_phone` char(13) NOT NULL,
  `cust_password` varchar(255) NOT NULL,
  `cust_status` enum('Registered','Unregistered','Suspended','Banned') NOT NULL,
  `cust_image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_customer`
--

INSERT INTO `tb_customer` (`cust_nim`, `cust_name`, `cust_phone`, `cust_password`, `cust_status`, `cust_image`) VALUES
('2', 'Nanas', '089999090', '123', 'Registered', 'nanas.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tb_order`
--

CREATE TABLE IF NOT EXISTS `tb_order` (
  `or_id` varchar(255) NOT NULL,
  `cour_email` varchar(255) NOT NULL,
  `cust_nim` varchar(255) NOT NULL,
  `vend_id` int(11) NOT NULL,
  `vend_name` varchar(255) NOT NULL,
  `vend_address` text NOT NULL,
  `pickup_lat` double NOT NULL,
  `pickup_long` double NOT NULL,
  `vend_pos_lat` double NOT NULL,
  `vend_pos_long` double NOT NULL,
  `or_distance` int(11) NOT NULL,
  `or_status` enum('Finish','Inprogress','Canceled by Courier','Canceled by Customer','Uncheck') NOT NULL DEFAULT 'Uncheck'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_order`
--

INSERT INTO `tb_order` (`or_id`, `cour_email`, `cust_nim`, `vend_id`, `vend_name`, `vend_address`, `pickup_lat`, `pickup_long`, `vend_pos_lat`, `vend_pos_long`, `or_distance`, `or_status`) VALUES
('3bKezdh', 'ungke@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.4164131028325775, 124.98526588082314, 1.415652, 124.983774, 178, 'Uncheck'),
('8Hksnfx', 'alo@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.4163309852787487, 124.98495172709228, 1.415652, 124.983774, 182, 'Uncheck'),
('A9yKzr0', 'alo@gmail.com', '2', 1, 'Toko klabat', 'Toko klabat', 1.4162686429706315, 124.98488165438174, 1.417655, 124.985884, 144, 'Canceled by Courier'),
('AiP5X6g', 'alo@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.416411091790498, 124.98502079397439, 1.415652, 124.983774, 181, 'Uncheck'),
('GOCkUUp', 'alo@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.416348414310834, 124.9851005896926, 1.415652, 124.983774, 180, 'Uncheck'),
('lWjo9uI', 'alo@gmail.com', '2', 5, 'Warong Dixhuit', 'Warong Dixhuit', 1.4166849286737175, 124.98490411788225, 1.417442, 124.982634, 269, 'Uncheck'),
('Mz7QO0v', 'alo@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.4164918686468038, 124.98523470014334, 1.415652, 124.983774, 178, 'Uncheck'),
('QVX1qGx', 'alo@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.4163319907998266, 124.98505901545286, 1.415652, 124.983774, 181, 'Uncheck'),
('SnsKx7a', 'alo@gmail.com', '2', 5, 'Warong Dixhuit', 'Warong Dixhuit', 1.4162797037028323, 124.98486489057542, 1.417442, 124.982634, 258, 'Canceled by Courier'),
('SY4Vjba', 'alo@gmail.com', '2', 4, 'Store Unklab', 'Store Unklab', 1.416422487695696, 124.98513344675303, 1.415652, 124.983774, 181, 'Uncheck');

-- --------------------------------------------------------

--
-- Table structure for table `tb_product`
--

CREATE TABLE IF NOT EXISTS `tb_product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `vend_id` int(11) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_stock` int(11) NOT NULL,
  `product_image` text NOT NULL,
  `product_caption` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_product`
--

INSERT INTO `tb_product` (`product_id`, `product_name`, `vend_id`, `product_price`, `product_stock`, `product_image`, `product_caption`) VALUES
(1, 'Sandal Jepit', 1, 10000, 10, 'Sandal jepit', 'Asli indonesia'),
(2, 'Cap tikus', 2, 8000, 10, 'captikus.jpg', 'Beking mabo orang'),
(3, 'Kopra', 2, 4000, 900, 'kopra.jpg', 'minya'),
(4, 'Selop', 2, 20000, 90, 'sandal', 'karet'),
(5, 'Ikang Ayam', 3, 37000, 90, 'ayam.jpg', 'krenyes'),
(6, 'Kentang gosong', 3, 8000, 400, 'Kentang.jpg', 'Makanan anak kota'),
(7, 'Pepsi', 3, 5000, 800, 'pep.jpg', 'dingin'),
(8, 'Tas kresek', 4, 20000, 56, 'Tas kresek', 'Plastik'),
(9, 'Kertas ajaib', 4, 8000, 5, 'kertas.jpg', 'cover.jpg'),
(10, 'Kola Goreng', 5, 5000, 7, 'Kola.jpg', 'kola.jpg'),
(11, 'Gohu Kukus', 5, 5000, 89, 'gohu.jpg', 'Sedap');

-- --------------------------------------------------------

--
-- Table structure for table `tb_transaction`
--

CREATE TABLE IF NOT EXISTS `tb_transaction` (
  `trans_id` int(11) NOT NULL,
  `or_id` varchar(255) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `cust_nim` varchar(255) NOT NULL,
  `trans_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `trans_status` enum('Finish','Waiting','Canceled','') NOT NULL,
  `trans_price` int(11) NOT NULL,
  `trans_quantity` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9007 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_transaction`
--

INSERT INTO `tb_transaction` (`trans_id`, `or_id`, `product_id`, `product_name`, `cust_nim`, `trans_date`, `trans_status`, `trans_price`, `trans_quantity`) VALUES
(8990, '', 1234, 'KFC', '2', '2019-05-14 08:34:13', 'Finish', 12, 2),
(8991, '5', 11, 'Gohu Kukus', '2', '0000-00-00 00:00:00', 'Waiting', 2, 10000),
(8992, '5', 11, 'Gohu Kukus', '2', '0000-00-00 00:00:00', 'Waiting', 2, 10000),
(8993, '5', 11, 'Gohu Kukus', '2', '0000-00-00 00:00:00', 'Waiting', 2, 10000),
(8994, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 2, 16000),
(8995, '4', 8, 'Tas kresek', '2', '0000-00-00 00:00:00', 'Waiting', 1, 20000),
(8996, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 2, 16000),
(8997, '4', 8, 'Tas kresek', '2', '0000-00-00 00:00:00', 'Waiting', 1, 20000),
(8998, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 2, 16000),
(8999, '5', 11, 'Gohu Kukus', '2', '0000-00-00 00:00:00', 'Waiting', 2, 10000),
(9000, '1', 1, 'Sandal Jepit', '2', '0000-00-00 00:00:00', 'Waiting', 1, 10000),
(9001, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 2, 16000),
(9002, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 2, 16000),
(9003, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 3, 24000),
(9004, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 3, 24000),
(9005, '4', 9, 'Kertas ajaib', '2', '0000-00-00 00:00:00', 'Waiting', 3, 24000),
(9006, '5', 11, 'Gohu Kukus', '2', '0000-00-00 00:00:00', 'Waiting', 2, 10000);

-- --------------------------------------------------------

--
-- Table structure for table `tb_vendor`
--

CREATE TABLE IF NOT EXISTS `tb_vendor` (
  `vend_id` int(11) NOT NULL,
  `vend_name` varchar(25) NOT NULL,
  `vend_phone` char(13) NOT NULL,
  `vend_address` text NOT NULL,
  `vend_pos_longtitude` double NOT NULL,
  `vend_pos_latitude` double NOT NULL,
  `vend_profile_image` text NOT NULL,
  `vend_cover_image` text NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_vendor`
--

INSERT INTO `tb_vendor` (`vend_id`, `vend_name`, `vend_phone`, `vend_address`, `vend_pos_longtitude`, `vend_pos_latitude`, `vend_profile_image`, `vend_cover_image`) VALUES
(1, 'Toko klabat', '08968887888', 'Jln. Arnold Momuntu No.007', 124.985884, 1.417655, 'Abc.jpg', 'Def.jpg'),
(2, 'Multi Mart', '089393939', 'Megamas', 124.834033, 1.478223, 'multi.jpg', 'multicover.jpg'),
(3, 'KFC', '08999329389', 'Megamass', 124.834794, 1.483543, 'kfc.jpg', 'kfccover.jpg'),
(4, 'Store Unklab', '082332323', 'Unklab, Jl. Arnold Momuntu', 124.983774, 1.415652, 'nanas.jpg', 'cover.jpg'),
(5, 'Warong Dixhuit', '+12389089', 'Kampus Unklab', 124.982634, 1.417442, 'Dixuit.jpg', 'Cover.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_courier`
--
ALTER TABLE `tb_courier`
  ADD PRIMARY KEY (`cour_email`);

--
-- Indexes for table `tb_customer`
--
ALTER TABLE `tb_customer`
  ADD PRIMARY KEY (`cust_nim`);

--
-- Indexes for table `tb_order`
--
ALTER TABLE `tb_order`
  ADD PRIMARY KEY (`or_id`);

--
-- Indexes for table `tb_product`
--
ALTER TABLE `tb_product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `tb_transaction`
--
ALTER TABLE `tb_transaction`
  ADD PRIMARY KEY (`trans_id`);

--
-- Indexes for table `tb_vendor`
--
ALTER TABLE `tb_vendor`
  ADD PRIMARY KEY (`vend_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_product`
--
ALTER TABLE `tb_product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `tb_transaction`
--
ALTER TABLE `tb_transaction`
  MODIFY `trans_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9007;
--
-- AUTO_INCREMENT for table `tb_vendor`
--
ALTER TABLE `tb_vendor`
  MODIFY `vend_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
