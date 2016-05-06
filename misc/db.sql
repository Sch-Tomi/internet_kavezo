-- phpMyAdmin SQL Dump
-- version 4.6.1
-- http://www.phpmyadmin.net
--

-- --------------------------------------------------------

--
-- Table structure for table `befizetesek`
--

CREATE TABLE `befizetesek` (
  `azon` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  `osszeg` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gepek`
--

CREATE TABLE `gepek` (
  `id` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `leiras` text COLLATE utf8_hungarian_ci NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hasznalat`
--

CREATE TABLE `hasznalat` (
  `azon` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  `gepid` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  `be` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `ki` varchar(100) COLLATE utf8_hungarian_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ugyfelek`
--

CREATE TABLE `ugyfelek` (
  `azon` varchar(30) COLLATE utf8_hungarian_ci NOT NULL,
  `cim` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `szemszam` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `nev` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `beido` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `egyenleg` int(11) NOT NULL,
  `status` int(1) NOT NULL,
  `gepid` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `pont` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `befizetesek`
--
ALTER TABLE `befizetesek`
  ADD KEY `azon` (`azon`);

--
-- Indexes for table `gepek`
--
ALTER TABLE `gepek`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `ugyfelek`
--
ALTER TABLE `ugyfelek`
  ADD UNIQUE KEY `unique` (`azon`),
  ADD KEY `gepid` (`gepid`);
