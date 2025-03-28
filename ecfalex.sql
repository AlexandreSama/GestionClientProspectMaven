-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 28, 2025 at 07:33 AM
-- Server version: 9.2.0
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecfalex`
--
CREATE DATABASE IF NOT EXISTS `ecfalex` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `ecfalex`;

-- --------------------------------------------------------

--
-- Table structure for table `adresse`
--

CREATE TABLE `adresse` (
  `idAdresse` int NOT NULL,
  `numeroDeRue` varchar(2) NOT NULL,
  `nomDeRue` varchar(50) NOT NULL,
  `codePostal` varchar(50) NOT NULL,
  `ville` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `adresse`
--

INSERT INTO `adresse` (`idAdresse`, `numeroDeRue`, `nomDeRue`, `codePostal`, `ville`) VALUES
(1, '10', 'rue des Lilas', '75001', 'Nancy'),
(3, '5', 'boulevard Haussmann', '75009', 'Paris'),
(4, '5C', 'Rue des cocas', '67845', 'Arcachon'),
(5, '15', 'rue de la Paix', '75002', 'Paris'),
(6, '25', 'rue Saint-Honoré', '75001', 'Paris'),
(7, '5A', 'Rue des Pissenlits', '54780', 'Johannesbourg'),
(13, '9D', 'Boulevard francis', '68785', 'Colmar'),
(14, '68', 'Rue des Jardins Fleuris', '54340', 'Pompey'),
(16, '65', 'Chau. du Ban-la-Dame', '54390', 'Frouard'),
(18, '41', 'Bd Emile Zola', '54520', 'Laxou');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `idClient` int NOT NULL,
  `chiffreAffaire` int NOT NULL,
  `nbrEmploye` int NOT NULL,
  `idSociete` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`idClient`, `chiffreAffaire`, `nbrEmploye`, `idSociete`) VALUES
(1, 500000, 50, 1),
(3, 700000, 70, 3),
(4, 5420000, 250, 4),
(14, 154485, 4585, 14),
(16, 1254585, 250, 16);

-- --------------------------------------------------------

--
-- Table structure for table `contrat`
--

CREATE TABLE `contrat` (
  `idContrat` int NOT NULL,
  `libelle` varchar(50) NOT NULL,
  `montant` double NOT NULL,
  `idClient` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `contrat`
--

INSERT INTO `contrat` (`idContrat`, `libelle`, `montant`, `idClient`) VALUES
(1, 'Contrat Pneu Slick', 1000000, 1),
(2, 'Contrat Bloc Aluminium', 2458520, 14),
(3, 'Contrat bloc Fonte', 1458789, 1);

-- --------------------------------------------------------

--
-- Table structure for table `prospect`
--

CREATE TABLE `prospect` (
  `idProspect` int NOT NULL,
  `dateProspection` date NOT NULL,
  `estInteresse` tinyint(1) NOT NULL,
  `idSociete` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `prospect`
--

INSERT INTO `prospect` (`idProspect`, `dateProspection`, `estInteresse`, `idSociete`) VALUES
(1, '2015-11-23', 1, 5),
(2, '2023-01-14', 0, 6),
(3, '2000-07-12', 0, 7),
(4, '2025-03-04', 1, 18);

-- --------------------------------------------------------

--
-- Table structure for table `societe`
--

CREATE TABLE `societe` (
  `idSociete` int NOT NULL,
  `adresseMail` varchar(100) NOT NULL,
  `commentaire` text NOT NULL,
  `telephone` varchar(12) NOT NULL,
  `raisonSociale` varchar(50) NOT NULL,
  `idAdresse` int NOT NULL,
  `gestionnaire` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `societe`
--

INSERT INTO `societe` (`idSociete`, `adresseMail`, `commentaire`, `telephone`, `raisonSociale`, `idAdresse`, `gestionnaire`) VALUES
(1, 'client1@mail.com', '', '0618888112', 'Woods Associates', 1, 8),
(3, 'client3@mail.com', 'Commentaire client 3', '0102030407', 'Shifty Corp', 3, 8),
(4, 'test@test.fr', 'test ? ouais ca marche', '0618888112', 'Woody Inc', 4, 8),
(5, 'prospect1@mail.com', 'Commentaire prospect 1', '0102030408', 'Prospect X', 5, 8),
(6, 'prospect2@mail.com', 'Commentaire prospect 2', '0102030409', 'Prospect Y', 6, 8),
(7, 'test@test.fr', 'Un commentaire ?', '0612452101', 'Piwi Industry', 7, 8),
(14, 'test@test.fr', '', '0618888112', 'Lkoww', 13, 8),
(16, 'sofidel@gmail.com', '', '0383495353', 'Sofidel France', 16, 8),
(18, 'cynara@gmail.com', '', '0383414956', 'Cynara', 18, 8);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `pwd` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `pwd`) VALUES
(8, 'alex', '$argon2i$v=19$m=65536,t=2,p=1$PKMQeurQO6GGx+tSIa6V7A$ZHQR8T/Gx5KW/3PGlUR6wVrexY9lH2sF59ZZCnh1cMk'),
(9, 'Djinn', '$argon2i$v=19$m=65536,t=2,p=1$cgMpp9Sk+85/uKLG/U1ViQ$zKatZDRbtGMga16hwvL2AlktAZWNATNAOqF3EvrRBuA');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `adresse`
--
ALTER TABLE `adresse`
  ADD PRIMARY KEY (`idAdresse`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`),
  ADD KEY `idSociete` (`idSociete`);

--
-- Indexes for table `contrat`
--
ALTER TABLE `contrat`
  ADD PRIMARY KEY (`idContrat`),
  ADD KEY `idClient` (`idClient`);

--
-- Indexes for table `prospect`
--
ALTER TABLE `prospect`
  ADD PRIMARY KEY (`idProspect`),
  ADD KEY `idSociete` (`idSociete`);

--
-- Indexes for table `societe`
--
ALTER TABLE `societe`
  ADD PRIMARY KEY (`idSociete`),
  ADD UNIQUE KEY `raisonSociale` (`raisonSociale`),
  ADD KEY `idAdresse` (`idAdresse`),
  ADD KEY `fk_societe_gestionnaire` (`gestionnaire`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `adresse`
--
ALTER TABLE `adresse`
  MODIFY `idAdresse` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `idClient` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `contrat`
--
ALTER TABLE `contrat`
  MODIFY `idContrat` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `prospect`
--
ALTER TABLE `prospect`
  MODIFY `idProspect` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `societe`
--
ALTER TABLE `societe`
  MODIFY `idSociete` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`idSociete`) REFERENCES `societe` (`idSociete`);

--
-- Constraints for table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `contrat_ibfk_1` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`);

--
-- Constraints for table `prospect`
--
ALTER TABLE `prospect`
  ADD CONSTRAINT `prospect_ibfk_1` FOREIGN KEY (`idSociete`) REFERENCES `societe` (`idSociete`);

--
-- Constraints for table `societe`
--
ALTER TABLE `societe`
  ADD CONSTRAINT `fk_societe_gestionnaire` FOREIGN KEY (`gestionnaire`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  ADD CONSTRAINT `societe_ibfk_1` FOREIGN KEY (`idAdresse`) REFERENCES `adresse` (`idAdresse`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
