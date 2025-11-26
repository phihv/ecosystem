CREATE TABLE `users`
(
    `id`         bigint unsigned NOT NULL,
    `username`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL,
    `password`   varchar(100) COLLATE utf8mb4_unicode_ci                                DEFAULT NULL,
    `first_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `last_name`  varchar(100) COLLATE utf8mb4_unicode_ci                       NOT NULL,
    `status`     tinyint                                                       NOT NULL DEFAULT '1',
    `created_at` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci