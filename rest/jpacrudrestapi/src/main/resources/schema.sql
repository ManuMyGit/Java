create sequence HIBERNATE_SEQUENCE;

CREATE TABLE `teacher` (
  `id` int(10) unsigned NOT NULL default HIBERNATE_SEQUENCE.nextval,
  `version` int(10) unsigned NOT NULL DEFAULT 0,
  `name` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `birth_date` date NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  --UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `student` (
  `id` int(10) unsigned NOT NULL default HIBERNATE_SEQUENCE.nextval,
  `version` int(10) unsigned NOT NULL DEFAULT 0,
  `name` varchar(64) NOT NULL,
  `email` varchar(128) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `teacher_id` int(10) unsigned NOT NULL,
  FOREIGN KEY (teacher_id) REFERENCES teacher(id),
  PRIMARY KEY (`id`),
  --UNIQUE KEY `id_UNIQUE` (`id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
