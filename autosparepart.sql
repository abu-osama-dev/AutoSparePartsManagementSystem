-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 05, 2019 at 05:47 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `autosparepart`
--

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

CREATE TABLE `invoice` (
  `INVOICEID` int(11) NOT NULL,
  `INVOICENO` int(11) DEFAULT NULL,
  `PARTYSNAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TINNO` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `INVOICETOTAL` int(11) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parts`
--

CREATE TABLE `parts` (
  `id` int(11) NOT NULL,
  `part_no` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `purchase_price` float NOT NULL,
  `sales_price` float NOT NULL,
  `stock_unit` int(11) NOT NULL DEFAULT '0',
  `stock_location` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

--
-- Dumping data for table `parts`
--

INSERT INTO `parts` (`id`, `part_no`, `name`, `purchase_price`, `sales_price`, `stock_unit`, `stock_location`, `created_at`, `updated_at`) VALUES
(6, '123', 'zaqwer', 10, 15, 0, 'hfsoiod', '2019-10-27 20:52:51', '2019-10-27 20:53:50'),
(7, '321', 'rewqaz', 10, 15, 0, 'viiijkbm', '2019-10-30 06:19:53', '2019-10-30 06:19:53'),
(8, '4321', 'hummer', 12, 50, 0, 'jkshks', '2019-10-30 06:20:22', '2019-10-30 06:20:22'),
(9, '2345', 'bumper', 20, 100, 0, 'buhddsajai', '2019-10-30 06:22:19', '2019-10-30 06:22:19'),
(10, '1234', 'hisejfk', 30, 100, 0, 'd;ksfksdo', '2019-10-30 07:02:02', '2019-10-30 07:02:02');

-- --------------------------------------------------------

--
-- Table structure for table `purchaseitems`
--

CREATE TABLE `purchaseitems` (
  `sold_item_id` int(11) NOT NULL,
  `invoice_item_id` int(11) NOT NULL,
  `part_no` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
  `part_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` float NOT NULL,
  `unit` int(11) NOT NULL,
  `amount` float NOT NULL,
  `discount` float NOT NULL DEFAULT '0',
  `tax_rate` float NOT NULL DEFAULT '0',
  `items_total` float NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `bill_total` float NOT NULL,
  `purch_invoice_no` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `purchaseitems`
--

INSERT INTO `purchaseitems` (`sold_item_id`, `invoice_item_id`, `part_no`, `part_name`, `price`, `unit`, `amount`, `discount`, `tax_rate`, `items_total`, `invoice_id`, `bill_total`, `purch_invoice_no`, `created_at`, `updated_at`) VALUES
(1, 1, '123', NULL, 15, 5, 75, 0, 0, 75, 51, 75, 38490, '2019-11-05 15:56:19', '2019-11-05 15:56:19');

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `id` int(11) NOT NULL,
  `invoice_no` int(11) NOT NULL,
  `supplier_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `grand_total` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`id`, `invoice_no`, `supplier_name`, `user_id`, `grand_total`, `created_at`, `updated_at`) VALUES
(51, 38490, 'javed', NULL, 75, '2019-11-05 15:56:19', '2019-11-05 15:56:19');

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `INVOICEID` int(11) NOT NULL,
  `INVOICENO` int(11) DEFAULT NULL,
  `PARTYSNAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `INVOICETOTAL` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salesitems`
--

CREATE TABLE `salesitems` (
  `SOLDITEMID` int(11) NOT NULL,
  `INVITEMSNO` int(11) NOT NULL,
  `PARTNO` varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PARTNAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PRICE` float NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  `AMOUNT` float NOT NULL,
  `DISCOUNT` float NOT NULL,
  `TRATE` float NOT NULL,
  `TAMOUNT` float NOT NULL,
  `ITEMTOTAL` float NOT NULL,
  `INVOICEID` int(11) NOT NULL,
  `BILLTOTAL` float NOT NULL,
  `DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `email`, `phone`, `address`, `created_at`, `updated_at`) VALUES
(1, 'abu', 'abugmail', 'odjsjferejfaierirgkfdj', '89785678', '2019-10-27 21:05:01', '2019-10-27 21:05:01'),
(2, 'javed', 'javed@gmail', 'mm aligarh', '7898789', '2019-10-27 21:12:14', '2019-10-27 21:12:14');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(200) UNSIGNED NOT NULL,
  `fullname` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` int(200) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullname`, `username`, `password`, `email`, `phone`, `role`, `created_at`, `updated_at`) VALUES
(1, 'Abu Osama ', 'osama', 'osama', 'contact@gmail.com', '7538549358495830', 1, '2019-10-26 19:04:53', '2019-10-26 23:09:04'),
(2, 'Mohd Adnan', 'adnan', 'adnan', 'adnan@gmail.com', '88899999000', 2, '2019-10-26 19:43:18', '2019-10-26 23:50:25'),
(3, 'Nawaz Sarwar', 'nawaz', 'nawaz', 'nawaz@somewhere.com', '424392489', 1, '2019-10-26 22:40:14', '2019-10-26 22:40:14'),
(4, 'javed', 'javed', 'javed2', 'mkhhj.', '698098', 2, '2019-10-26 23:39:07', '2019-10-27 16:00:22'),
(5, 'mohd zayeem', 'zayeem', 'zayeem', 'odfdjddkkox', '7877899789', 1, '2019-10-29 16:45:59', '2019-10-29 16:45:59');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`INVOICEID`);

--
-- Indexes for table `parts`
--
ALTER TABLE `parts`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `part_no` (`part_no`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `purchaseitems`
--
ALTER TABLE `purchaseitems`
  ADD PRIMARY KEY (`sold_item_id`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `invoice_no` (`invoice_no`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`INVOICEID`),
  ADD UNIQUE KEY `invoice` (`INVOICENO`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `invoice`
--
ALTER TABLE `invoice`
  MODIFY `INVOICEID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `parts`
--
ALTER TABLE `parts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `purchaseitems`
--
ALTER TABLE `purchaseitems`
  MODIFY `sold_item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `purchases`
--
ALTER TABLE `purchases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `INVOICEID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(200) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
