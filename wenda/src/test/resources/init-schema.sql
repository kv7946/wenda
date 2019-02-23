DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `head_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `user_id` int(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `comment_count` int(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date_index` (`created_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `user_id` int(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `entity_id` int(50) NOT NULL,
  `entity_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date_index` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `from_id` int(50) NOT NULL,
  `to_id` int(50) NOT NULL,
  `tontent` text NOT NULL,
  `conversation_id` int(50) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `date_index` (`created_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

