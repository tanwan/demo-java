CREATE TABLE `simple_jdbi` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                   `use_string_enum` enum('ONE','TWO','THREE') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                   `use_enum_value_enum` int(11) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;