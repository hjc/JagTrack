--could use InnoDB for FK constraints

-- --------------------------------------------------------

--
-- Table structure for table `jt_address`
--

CREATE TABLE IF NOT EXISTS `jt_address` (
  `addr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`addr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Table for the address object' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `jt_bus`
--

CREATE TABLE IF NOT EXISTS `jt_bus` (
  `bus_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `max_seats` int(11) DEFAULT NULL,
  `arrival_time` datetime DEFAULT NULL,
  `departure_time` datetime DEFAULT NULL,
  `current_route_id` int(11) NOT NULL,
  `current_loc_id` int(11) DEFAULT NULL,
  `next_stop_id` int(11) NOT NULL,
  `passenger_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`bus_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Table for storing bus info. Most NULLs correspond to unknown' AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- Table structure for table `jt_loc`
--

CREATE TABLE IF NOT EXISTS `jt_loc` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `x_coord` int(11) NOT NULL,
  `y_coord` int(11) NOT NULL,
  `addr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Table for locations' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `jt_passenger`
--

CREATE TABLE IF NOT EXISTS `jt_passenger` (
  `pass_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `jag_number` varchar(20) DEFAULT NULL COMMENT 'Student/Employee JagNumber. Null means unknown',
  `email` varchar(100) DEFAULT NULL COMMENT 'passenger e-mail, optional',
  `first_name` varchar(150) DEFAULT NULL COMMENT 'passenger first name, optional',
  `last_name` varchar(150) DEFAULT NULL COMMENT 'passenger last name, optional',
  `deperature_stop_id` int(11) DEFAULT NULL COMMENT 'FK to jt_stop, bus'' arrival location',
  `arrival_stop_id` int(11) DEFAULT NULL COMMENT 'FK to jt_stop, bus'' destination location',
  `current_bus_id` int(11) DEFAULT NULL COMMENT 'FK to jt_bus, current bus user is on (NULL means not on a bus, -1 means unknown bus)',
  PRIMARY KEY (`pass_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Table for passengers' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- --------------------------------------------------------

--
-- Table structure for table `jt_route`
--

CREATE TABLE IF NOT EXISTS `jt_route` (
  `route_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `color` enum('red','white','blue','yellow','green') DEFAULT NULL COMMENT 'Possible values: red, blue, yellow, green, white',
  `start_time` datetime DEFAULT NULL COMMENT 'When route begins running',
  `end_time` datetime DEFAULT NULL COMMENT 'When route ends running',
  PRIMARY KEY (`route_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


-- --------------------------------------------------------

--
-- Table structure for table `jt_route_bus`
--

CREATE TABLE IF NOT EXISTS `jt_route_bus` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT,
  `route_id` int(11) NOT NULL COMMENT 'FK to jt_routes',
  `bus_id` int(11) NOT NULL COMMENT 'FK to jt_bus',
  PRIMARY KEY (`link_id`),
  KEY `route_id` (`route_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Links buses to their routes' AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `jt_route_stop`
--

--rid and sid, want to get to sid

CREATE TABLE IF NOT EXISTS `jt_route_stop` (
  `link_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `route_id` int(11) NOT NULL COMMENT 'FK to jt_routes',
  `stop_id` int(11) NOT NULL COMMENT 'FK to jt_stop',
  `next_stop_id` int(11) NOT NULL COMMENT 'Next stop in this route',
  `avg_time_to_next_stop` float(11) NOT NULL COMMENT 'avg time from this stop to next one, expressed in minutes; 0.5 = 30s',
  `distance_to_next_stop` int(11) DEFAULT NULL COMMENT 'distance to next stop, in feet',
  PRIMARY KEY (`link_id`),
  KEY `route_id` (`route_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Table to link routes to their stops.' AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- Table structure for table `jt_stop`
--

CREATE TABLE IF NOT EXISTS `jt_stop` (
  `stop_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `stop_num` int(11) DEFAULT NULL,
  `loc_id` int(11) DEFAULT NULL COMMENT 'FK to jt_loc, stores stop location',   -- should be NOT NULL for release, dont need for proto
  PRIMARY KEY (`stop_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Table for stops' AUTO_INCREMENT=1 ;


--------------------------------------------------------------------------------
----
  
-- Insert data

INSERT INTO jt_bus 
  (max_seats, arrival_time, departure_time, current_route_id, next_stop_id, passenger_count)
  VALUES (30, NOW(), NOW(), 1, 1, 18),
  (30, NOW(), NOW(), 1, 2, 22),
  (30, NOW(), NOW(), 2, 5, 20),
  (30, NOW(), NOW(), 2, 6, 16),
  (30, NOW(), NOW(), 2, 7, 12) ;
  
  
INSERT INTO jt_route
  (color, start_time, end_time)
  VALUES ('red', '2012-01-04 06:30:00', '2012-01-04 21:30:00'),
  ('blue', '2012-01-04 06:30:00', '2012-01-04 21:30:00') ;
  
INSERT INTO jt_route_stop
  (route_id, stop_id, next_stop_id, avg_time_to_next_stop)
  VALUES (1, 1, 2, 6),
  (1, 2, 3, 8),
  (1, 3, 4, 12),
  (1, 4, 1, 4),
  (2, 5, 6, 8),
  (2, 6, 7, 12),
  (2, 7, 8, 4),
  (2, 8, 1, 9),
  (2, 1, 5, 4) ;
  
INSERT INTO jt_stop
  (stop_num)
  VALUES (1),
  (2), (3), (4), (5), (6), (7), (8) ;

-- --------------------------------------------------------
