-- 这边两个数据库都使用同一个表来测试
DROP TABLE IF EXISTS `simple_atomikos`;
CREATE TABLE `simple_atomikos` (
                                          `id` INT NOT NULL AUTO_INCREMENT,
                                          `name` VARCHAR(45) NULL,
                                          PRIMARY KEY (`id`));
