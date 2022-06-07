--- sharding的dml
use demo;
DROP TABLE IF EXISTS `simple_mybatis`;
CREATE TABLE `simple_mybatis` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `use_string_enum` enum('ONE','TWO','THREE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `use_index_enum` int DEFAULT NULL,
                                  `use_enum_value_enum` int DEFAULT NULL,
                                  `version` int DEFAULT NULL,
                                  `del_flg` int DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `order_0`;
CREATE TABLE `order_0` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `order_id` int DEFAULT NULL,
                         `user_id` int DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
--- sharding的dml
use demo;
DROP TABLE IF EXISTS `simple_mybatis`;
CREATE TABLE `simple_mybatis` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `use_string_enum` enum('ONE','TWO','THREE') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `use_index_enum` int DEFAULT NULL,
                                  `use_enum_value_enum` int DEFAULT NULL,
                                  `version` int DEFAULT NULL,
                                  `del_flg` int DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


DROP TABLE IF EXISTS `order_0`;
CREATE TABLE `order_0` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `order_id` int DEFAULT NULL,
                           `user_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_other_0`;
CREATE TABLE `order_other_0` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `order_id` int DEFAULT NULL,
                                 `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_1`;
CREATE TABLE `order_1` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `order_id` int DEFAULT NULL,
                           `user_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_other_1`;
CREATE TABLE `order_other_1` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `order_id` int DEFAULT NULL,
                                 `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
DROP TABLE IF EXISTS `order_other_0`;
CREATE TABLE `order_other_0` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `order_id` int DEFAULT NULL,
                         `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_1`;
CREATE TABLE `order_1` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `order_id` int DEFAULT NULL,
                           `user_id` int DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `order_other_1`;
CREATE TABLE `order_other_1` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `order_id` int DEFAULT NULL,
                                 `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;