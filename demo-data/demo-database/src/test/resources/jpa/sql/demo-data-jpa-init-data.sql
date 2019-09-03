INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (1,'lzy',1,1489226618000);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (2,'lzy',2,1489226686000);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (3,'lzy',3,1489226665000);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (4,'lzy2',1,NULL);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (5,'lzy3',1,NULL);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (6,'lzy4',1,NULL);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (7,'lzy',3,1489226763000);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (8,'lzy',4,NULL);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (9,'lzy',NULL,NULL);
INSERT IGNORE INTO `sample_jpa` (`id`,`name`,`age`,`create_time`) VALUES (9,'lzy',NULL,NULL);
INSERT IGNORE INTO `sample_jpa_relevance`(`id`, `name`, `size`, `sample_jpa_id`) VALUES ('1', 'lzy', '1', '1');
INSERT IGNORE INTO `sample_optimistic_lock` (`id`,`name`,`version`) VALUES (1,'lzy',0);
INSERT IGNORE INTO `sample_lock` (`id`,`name`) VALUES (1,'lzy');


