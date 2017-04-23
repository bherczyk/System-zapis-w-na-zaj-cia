

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Baza danych: `14651951_001`
--
CREATE DATABASE `14651951_001` DEFAULT CHARACTER SET latin2 COLLATE latin2_general_ci;
USE `14651951_001`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Classes`
--

CREATE TABLE IF NOT EXISTS `Classes` (
  `idClasses` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ECTS` int(11) unsigned DEFAULT NULL,
  `Type` varchar(45) NOT NULL,
  `Day` varchar(45) NOT NULL,
  `Week` varchar(45) DEFAULT NULL,
  `Hour` time NOT NULL,
  `freePlaces` int(11) unsigned DEFAULT NULL,
  `idCourse` int(10) unsigned DEFAULT NULL,
  `idLocation` int(11) unsigned DEFAULT NULL,
  `idLecturer` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`idClasses`),
  UNIQUE KEY `idClasses_UNIQUE` (`idClasses`),
  KEY `idCourse_idx` (`idCourse`),
  KEY `idLecturer_idx` (`idLecturer`),
  KEY `idLocation_idx` (`idLocation`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin2 AUTO_INCREMENT=63 ;

--
-- Zrzut danych tabeli `Classes`
--

INSERT INTO `Classes` (`idClasses`, `ECTS`, `Type`, `Day`, `Week`, `Hour`, `freePlaces`, `idCourse`, `idLocation`, `idLecturer`) VALUES
(37, 2, 'Wykład', 'Pt', 'TP', '09:30:00', 20, 14, 1, 8),
(39, 3, 'Wykład', 'Pn', 'TP', '09:30:00', 20, 14, 1, 8),
(42, 20, 'Ćwiczenia', 'Sr', 'TN', '07:30:00', 24, 14, 5, 8),
(44, 2, 'Laboratorium', 'Pn', 'TN', '09:30:00', 19, 24, 1, 1),
(45, 3, 'Laboratorium', 'Pn', 'TP', '10:15:00', 21, 25, 10, 10),
(46, 2, 'Projekt', 'Pn', 'TN', '11:15:00', 17, 26, 11, 11),
(47, 1, 'Projekt', 'Pn', '', '13:15:00', 19, 27, 3, 3),
(48, 2, 'Laboratorium', 'Pn', '', '15:15:00', 23, 28, 4, 12),
(49, 1, 'Laboratorium', 'Wt', 'TN', '11:15:00', 24, 26, 12, 13),
(50, 2, 'Laboratorium', 'Wt', 'TP', '13:15:00', 19, 29, 13, 14),
(51, 4, 'Wykład', 'Wt', '', '17:05:00', 178, 30, 14, 15),
(52, 3, 'Wykład', 'Wt', '', '18:55:00', 169, 31, 15, 16),
(53, 1, 'Wykład', 'Sr', 'TP', '09:15:00', 169, 28, 15, 17),
(54, 2, 'Projekt', 'Sr', 'TP', '11:15:00', 11, 24, 16, 18),
(55, 1, 'Seminarium', 'Sr', 'TP', '13:15:00', 25, 27, 17, 19),
(56, 1, 'Wykład', 'Sr', 'TN', '17:05:00', 169, 29, 15, 14),
(57, 1, 'Wykład', 'Czw', 'TN', '09:15:00', 169, 26, 15, 21),
(58, 2, 'Laboratorium', 'Czw', '', '13:15:00', 25, 30, 18, 19),
(59, 1, 'Wykład', 'Sr', '', '18:55:00', 169, 25, 15, 20),
(60, 1, 'Wykład', 'Czw', '', '11:15:00', 169, 24, 15, 22),
(61, 5, 'Wykład', 'Pn', 'TP', '07:30:00', 0, 14, 19, 8),
(62, 25, 'Laboratorium', 'Sr', '', '09:15:00', 26, 24, 18, 13);

--
-- Wyzwalacze `Classes`
--
DROP TRIGGER IF EXISTS `Classes_BEFORE_INSERT`;
DELIMITER //
CREATE TRIGGER `Classes_BEFORE_INSERT` BEFORE INSERT ON `Classes`
 FOR EACH ROW BEGIN
	SET NEW.freePlaces = (SELECT size FROM `14651951_001`.`Locations` WHERE idLocation = NEW.idLocation);
	IF (SELECT COUNT(*) FROM `14651951_001`.`Classes` WHERE ( idClasses <> NEW.idClasses AND idLocation = NEW.idLocation AND (Day = NEW.Day AND NEW.Hour BETWEEN ADDTIME(Hour,'-1:30') AND ADDTIME(Hour,'1:30') AND (Week = NEW.Week OR ((Week is NULL OR Week like '')  AND NEW.Week is NOT NULL) OR (Week is not null AND (NEW.Week is NULL OR NEW.Week like '')))))) > 0  THEN
				signal sqlstate '12345'
					SET message_text = 'Wybrana sala zajęta w podanym terminie!';
	END IF;

	IF(SELECT COUNT(*) FROM `14651951_001`.`Classes` WHERE (idClasses <> NEW.idClasses AND idLecturer = NEW.idLecturer AND (Day = NEW.Day AND NEW.Hour BETWEEN ADDTIME(Hour,'-1:30') AND ADDTIME(Hour,'1:30') AND (Week = NEW.Week OR ((Week is NULL OR Week like '') AND NEW.Week is NOT NULL) OR (Week is not null AND (NEW.Week is NULL OR NEW.Week like '')))))) > 0 THEN
			signal sqlstate '12345'
				set message_text = 'Wybrany prowadzący zajęty w podanym terminie!';
	END IF;
	
    
	IF NEW.ECTS > 30 OR NEW.ECTS <= 0 then
				signal sqlstate '12345'
			set message_text = 'Błędna wartość ECTS!';
	END IF;
    
    IF NEW.Type not like 'Wykład' AND NEW.Type not like 'Ćwiczenia' AND NEW.Type not like 'Laboratorium' AND NEW.Type not like 'Projekt' AND NEW.Type not like 'Seminarium' then
    				signal sqlstate '12345'
			set message_text = 'Błędna wartość type!';
    END IF;
    
	IF NEW.Day not like 'Pn' AND NEW.Day not like 'Wt' AND NEW.Day not like 'Sr' AND NEW.Day not like 'Czw' AND NEW.Day not like 'Pt' then
        				signal sqlstate '12345'
			set message_text = 'Błędna wartość day!';
    END IF;
    
    IF  NEW.Week not like '' AND NEW.Week is not null AND (NEW.Week not like 'TP' AND NEW.Week not like 'TN') then
            				signal sqlstate '12345'
			set message_text = 'Błędna wartość Week!';
    END IF;
END
//
DELIMITER ;
DROP TRIGGER IF EXISTS `Classes_BEFORE_UPDATE`;
DELIMITER //
CREATE TRIGGER `Classes_BEFORE_UPDATE` BEFORE UPDATE ON `Classes`
 FOR EACH ROW BEGIN

	IF NEW.idLocation <> OLD.idLocation OR NEW.Day <> OLD.Day OR NEW.Week <> OLD.Week OR NEW.Hour <> OLD.Hour THEN
		
			SET NEW.freePlaces = (SELECT size FROM `14651951_001`.`Locations` WHERE idLocation = NEW.idLocation);
			IF (SELECT COUNT(*) FROM `14651951_001`.`Classes` WHERE ( idClasses <> NEW.idClasses AND idLocation = NEW.idLocation AND (Day = NEW.Day AND NEW.Hour BETWEEN ADDTIME(Hour,'-1:30') AND ADDTIME(Hour,'1:30')  AND (Week = NEW.Week OR ((Week is NULL OR Week like '')  AND NEW.Week is NOT NULL) OR (Week is not null AND (NEW.Week is NULL OR NEW.Week like '')))))) > 0  THEN
				signal sqlstate '12345'
					SET message_text = 'Wybrana sala zajęta w podanym terminie!';
			END IF;
	END IF;
    IF NEW.idLecturer <> OLD.idLecturer OR NEW.Day <> OLD.Day OR NEW.Week <> OLD.Week OR NEW.Hour <> OLD.Hour THEN
		IF(SELECT COUNT(*) FROM `14651951_001`.`Classes` WHERE (idClasses <> NEW.idClasses AND idLecturer = NEW.idLecturer AND (Day = NEW.Day AND NEW.Hour BETWEEN ADDTIME(Hour,'-1:30') AND ADDTIME(Hour,'1:30') AND (Week = NEW.Week OR ((Week is NULL OR Week like '') AND NEW.Week is NOT NULL) OR (Week is not null AND (NEW.Week is NULL OR NEW.Week like '')))))) > 0 THEN
			signal sqlstate '12345'
				set message_text = 'Wybrany prowadzący zajęty w podanym terminie!';
		END IF;
	END IF;
    
	IF NEW.ECTS > 30 OR NEW.ECTS <= 0 then
				signal sqlstate '12345'
			set message_text = 'Błędna wartość ECTS!';
	END IF;
    
    IF NEW.Type not like 'Wykład' AND NEW.Type not like 'Ćwiczenia' AND NEW.Type not like 'Laboratorium' AND NEW.Type not like 'Projekt' AND NEW.Type not like 'Seminarium' then
    				signal sqlstate '12345'
			set message_text = 'Błędna wartość type!';
    END IF;
    
	IF NEW.Day not like 'Pn' AND NEW.Day not like 'Wt' AND NEW.Day not like 'Sr' AND NEW.Day not like 'Czw' AND NEW.Day not like 'Pt' then
        				signal sqlstate '12345'
			set message_text = 'Błędna wartość day!';
    END IF;
    
    IF  NEW.Week not like '' AND NEW.Week is not null AND (NEW.Week not like 'TP' AND NEW.Week not like 'TN') then
            				signal sqlstate '12345'
			set message_text = 'Błędna wartość Week!';
    END IF;
END
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Courses`
--

CREATE TABLE IF NOT EXISTS `Courses` (
  `idCourse` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`idCourse`),
  UNIQUE KEY `idCourse_UNIQUE` (`idCourse`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin2 AUTO_INCREMENT=32 ;

--
-- Zrzut danych tabeli `Courses`
--

INSERT INTO `Courses` (`idCourse`, `Name`) VALUES
(27, 'Bazy Danych 2'),
(25, 'Grafika kom. i kom.człow-komp.'),
(30, 'Inżynieria oprogramowania'),
(14, 'Matematyka'),
(24, 'Projektowanie efektywnych algorytmów'),
(31, 'Systemy operacyjne 1'),
(26, 'Technologie sieciowe 2'),
(28, 'Układy cyfr. i sys.wbud.1'),
(29, 'Urządzenia peryferyjne');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Lecturers`
--

CREATE TABLE IF NOT EXISTS `Lecturers` (
  `idLecturer` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `pesel` bigint(11) unsigned NOT NULL,
  PRIMARY KEY (`idLecturer`),
  UNIQUE KEY `idLecturer_UNIQUE` (`idLecturer`),
  UNIQUE KEY `pesel_UNIQUE` (`pesel`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin2 AUTO_INCREMENT=23 ;

--
-- Zrzut danych tabeli `Lecturers`
--

INSERT INTO `Lecturers` (`idLecturer`, `title`, `firstName`, `lastName`, `pesel`) VALUES
(1, 'Dr inż.', 'Jarosław', 'Mierzwa', 77041908198),
(3, 'Mgr inż.', 'Łukasz', 'Culer', 89042314937),
(8, 'mgr. inż.', 'Jerzy', 'Pietraszko', 66666666666),
(10, 'Mgr inż', 'Szymon', 'Datko', 90129822312),
(11, 'Dr inż', 'Marcinwww', 'Markowski', 81457412541),
(12, 'Mgr inż.', 'Antoni', 'Sterna', 64042147414),
(13, 'Dr inż.', 'Róża', 'Goścień', 88010241114),
(14, 'Dr inż.', 'Jan', 'Nikodem', 59874147859),
(15, 'Dr  hab inż.', 'Olgierd', 'Unold', 59874147854),
(16, 'Dr inż.', 'Dariusz', 'Caban', 64124444444),
(17, 'Dr inż.', 'Jarosław', 'Sugier', 64124444442),
(18, 'Dr inż.', 'Zbigniew', 'Buchalski', 6412444444),
(19, 'Dr inż.', 'Pawel', 'Głuchowski', 87040847412),
(20, 'Dr inż.', 'Jacek', 'Jarnicki', 57040847412),
(21, 'Dr inż.', 'Arkadiusz', 'Grzybowski', 67040847412),
(22, 'Dr inż.', 'Tomasz', 'Kapłon', 77040847412);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Locations`
--

CREATE TABLE IF NOT EXISTS `Locations` (
  `idLocation` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `buildingName` varchar(45) NOT NULL,
  `hallName` varchar(45) NOT NULL,
  `size` varchar(45) NOT NULL,
  PRIMARY KEY (`idLocation`),
  UNIQUE KEY `idLocation_UNIQUE` (`idLocation`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin2 AUTO_INCREMENT=20 ;

--
-- Zrzut danych tabeli `Locations`
--

INSERT INTO `Locations` (`idLocation`, `buildingName`, `hallName`, `size`) VALUES
(1, 'C-3', '127P', '20'),
(2, 'C-9', '110', '20'),
(3, 'C-3', '05', '20'),
(4, 'C-3', '016', '24'),
(5, 'C-4', '24', '25'),
(7, 'C-2', '224', '18'),
(10, 'C-16', 'L2.7', '24'),
(11, 'C-3', '108', '18'),
(12, 'C-3', '126', '25'),
(13, 'C-3', '229', '20'),
(14, 'C-1', '205', '180'),
(15, 'C-1', '201', '170'),
(16, 'C-3', '225', '12'),
(17, 'C-3', '20', '26'),
(18, 'C-16', 'L2.6', '26'),
(19, 'C-2', '2', '0');

--
-- Wyzwalacze `Locations`
--
DROP TRIGGER IF EXISTS `Locations_BEFORE_INSERT`;
DELIMITER //
CREATE TRIGGER `Locations_BEFORE_INSERT` BEFORE INSERT ON `Locations`
 FOR EACH ROW BEGIN

	IF (SELECT COUNT(*) FROM `14651951_001`.`Locations` WHERE buildingName = NEW.buildingName AND hallName = NEW.hallName ) > 0 THEN
				signal sqlstate '12345'
				SET message_text = 'Wybrana sala już istnieje!';
    END IF;
END
//
DELIMITER ;
DROP TRIGGER IF EXISTS `Locations_BEFORE_UPDATE`;
DELIMITER //
CREATE TRIGGER `Locations_BEFORE_UPDATE` BEFORE UPDATE ON `Locations`
 FOR EACH ROW BEGIN
	IF NEW.buildingName <> OLD.buildingName OR NEW.hallName <> OLD.hallName THEN
    
		IF (SELECT COUNT(*) FROM `14651951_001`.`Locations` WHERE buildingName = NEW.buildingName AND hallName = NEW.hallName ) > 0 THEN
					signal sqlstate '12345'
					SET message_text = 'Wybrana sala już istnieje!';
		END IF;
	END IF;
END
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Record`
--

CREATE TABLE IF NOT EXISTS `Record` (
  `idStudent` int(11) unsigned NOT NULL,
  `idClasses` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idStudent`,`idClasses`),
  KEY `idStudent_idx` (`idStudent`),
  KEY `idClasses_idx` (`idClasses`)
) ENGINE=InnoDB DEFAULT CHARSET=latin2;

--
-- Zrzut danych tabeli `Record`
--

INSERT INTO `Record` (`idStudent`, `idClasses`) VALUES
(1, 37),
(1, 39),
(1, 42),
(1, 61),
(3, 45),
(3, 51),
(9, 45),
(10, 44),
(10, 45),
(10, 46),
(10, 47),
(10, 48),
(10, 49),
(10, 50),
(10, 51),
(10, 52),
(10, 53),
(10, 54),
(10, 55),
(10, 56),
(10, 57),
(10, 58),
(10, 59),
(10, 60);

--
-- Wyzwalacze `Record`
--
DROP TRIGGER IF EXISTS `Record_AFTER_DELETE`;
DELIMITER //
CREATE TRIGGER `Record_AFTER_DELETE` AFTER DELETE ON `Record`
 FOR EACH ROW BEGIN
	UPDATE `14651951_001`.`Classes` SET `freePlaces` = `freePlaces` + 1 WHERE `idClasses` = OLD.idClasses;
    UPDATE `14651951_001`.`Students` SET `ECTS` = `ECTS` - (SELECT ECTS FROM `14651951_001`.`Classes` WHERE idClasses = OLD.idClasses ) WHERE `idStudent` = OLD.idStudent;
END
//
DELIMITER ;
DROP TRIGGER IF EXISTS `Record_BEFORE_INSERT`;
DELIMITER //
CREATE TRIGGER `Record_BEFORE_INSERT` BEFORE INSERT ON `Record`
 FOR EACH ROW BEGIN
	


    
	IF (SELECT ECTS + (SELECT ECTS FROM `14651951_001`.`Classes` WHERE idClasses = NEW.idClasses) FROM `14651951_001`.`Students` WHERE idStudent = NEW.idStudent) > 30 THEN
				signal sqlstate '12345'
				SET message_text = 'Przekroczono limit punktów ECTS!';
	END IF;
    
   
    
	IF(SELECT COUNT(*) FROM 14651951_001.Record, 14651951_001.Classes 
		WHERE (14651951_001.Record.idClasses = 14651951_001.Classes.idClasses)
        AND (14651951_001.Record.idStudent = NEW.idStudent)
		AND (Day = (SELECT Day FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses) )
		AND (Day = (SELECT Day FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses) )
		AND ((SELECT Hour FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses) BETWEEN ADDTIME(Hour,'-1:30') AND ADDTIME(Hour,'1:30'))
		AND (Week = (SELECT Week FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses)
		OR ((Week is NULL OR Week like "") AND (SELECT Week FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses) is NOT NULL)
		OR (Week is not null AND ((SELECT Week FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses) is NULL
		OR (SELECT Week FROM  14651951_001.Classes WHERE idClasses = NEW.idClasses) like "" ))))  > 0 THEN
			signal sqlstate '12345'
				set message_text = 'Zapisany na inne zajęcia w podanym terminie!';
	END IF;
  
	UPDATE `14651951_001`.`Classes` SET `freePlaces` = `freePlaces` - 1 WHERE `idClasses` = NEW.idClasses;
    UPDATE `14651951_001`.`Students` SET `ECTS` = `ECTS` + (SELECT ECTS FROM `14651951_001`.`Classes` WHERE idClasses = NEW.idClasses ) WHERE `idStudent` = NEW.idStudent;
END
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Students`
--

CREATE TABLE IF NOT EXISTS `Students` (
  `idStudent` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `lastName` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `ECTS` int(11) DEFAULT '0',
  `Pesel` bigint(11) unsigned NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`idStudent`),
  UNIQUE KEY `idStudent_UNIQUE` (`idStudent`),
  UNIQUE KEY `Pesel_UNIQUE` (`Pesel`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin2 AUTO_INCREMENT=13 ;

--
-- Zrzut danych tabeli `Students`
--

INSERT INTO `Students` (`idStudent`, `lastName`, `firstName`, `ECTS`, `Pesel`, `Password`) VALUES
(1, 'Kobak', 'Radosław', 30, 14785236987, '38d0f42245445c73237c943ecbe08a08'),
(3, 'Jan', 'Jan', 7, 11122547895, '12630487d3f98f1a07153d34c2b05a67'),
(4, 'Marek', 'Dawid', 0, 22233345321, 'bcfbb2c105c182cc169e6b8fd0471abf'),
(6, 'Jan', 'Ciesielski', 0, 91097810599, '363b122c528f54df4a0446b6bab05515'),
(9, 'Janoew', 'Janeczekw', 3, 882, '4f36ad2928f8fc16972c2c11a2358a79'),
(10, 'Bartosz', 'Herczyk', 30, 94081610599, 'bcfbb2c105c182cc169e6b8fd0471abf'),
(11, 'as', 'as', 1, 1, 'qwwq'),
(12, '', '', 0, 0, '');

-- --------------------------------------------------------

--
-- Zastąpiona struktura widoku `Zajecia`
--
CREATE TABLE IF NOT EXISTS `Zajecia` (
`Kod zajęć` int(10) unsigned
,`Nazwa kursu` varchar(45)
,`Rodzaj` varchar(45)
,`ECTS` int(11) unsigned
,`Dzień` varchar(45)
,`Parzystość` varchar(45)
,`Godzina` time
,`Budynek` varchar(45)
,`Sala` varchar(45)
,`Prowadzący` varchar(149)
,`Liczba miejsc` int(11) unsigned
);
-- --------------------------------------------------------

--
-- Struktura widoku `Zajecia`
--
DROP TABLE IF EXISTS `Zajecia`;

CREATE ALGORITHM=UNDEFINED DEFINER=`14651951_001`@`%` SQL SECURITY DEFINER VIEW `Zajecia` AS select `Classes`.`idClasses` AS `Kod zajęć`,`Courses`.`Name` AS `Nazwa kursu`,`Classes`.`Type` AS `Rodzaj`,`Classes`.`ECTS` AS `ECTS`,`Classes`.`Day` AS `Dzień`,`Classes`.`Week` AS `Parzystość`,`Classes`.`Hour` AS `Godzina`,`Locations`.`buildingName` AS `Budynek`,`Locations`.`hallName` AS `Sala`,concat_ws(' ',`Lecturers`.`idLecturer`,`Lecturers`.`title`,`Lecturers`.`firstName`,`Lecturers`.`lastName`) AS `Prowadzący`,`Classes`.`freePlaces` AS `Liczba miejsc` from (((`Courses` join `Classes` on((`Courses`.`idCourse` = `Classes`.`idCourse`))) left join `Locations` on((`Classes`.`idLocation` = `Locations`.`idLocation`))) left join `Lecturers` on((`Classes`.`idLecturer` = `Lecturers`.`idLecturer`)));

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `Classes`
--
ALTER TABLE `Classes`
  ADD CONSTRAINT `idCourse` FOREIGN KEY (`idCourse`) REFERENCES `Courses` (`idCourse`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `idLecturer` FOREIGN KEY (`idLecturer`) REFERENCES `Lecturers` (`idLecturer`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `idLocation` FOREIGN KEY (`idLocation`) REFERENCES `Locations` (`idLocation`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `Record`
--
ALTER TABLE `Record`
  ADD CONSTRAINT `idClasses` FOREIGN KEY (`idClasses`) REFERENCES `Classes` (`idClasses`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `idStudent` FOREIGN KEY (`idStudent`) REFERENCES `Students` (`idStudent`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Baza danych: `information_schema`
--
CREATE DATABASE `information_schema` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `information_schema`;

-- --------------------------------------------------------





/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
