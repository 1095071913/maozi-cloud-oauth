-- 【客户端】


-- DDL
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端Id',
  `auth_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[4]',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `resource_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[\"backend-resources\"]',
  `client_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[\"backend\"]' COMMENT '范围',
  `registered_redirect_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向地址',
  `authorities` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '[]' COMMENT '权限标识',
  `access_token_validity_seconds` int NULL DEFAULT 86400 COMMENT '授权令牌有效期 秒',
  `refresh_token_validity_seconds` int NULL DEFAULT 86400 COMMENT '刷新令牌有效期 秒',
  `additional_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `id` bigint NOT NULL COMMENT '主键',
  `create_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` bigint NULL DEFAULT 0 COMMENT '逻辑删除',
  `status` bigint NULL DEFAULT 1 COMMENT '状态',
  `version` int NULL DEFAULT 0 COMMENT '版本号',
  `create_time` bigint NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `actable_idx_client_id`(`client_id` ASC) USING BTREE,
  INDEX `actable_idx_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端' ROW_FORMAT = Dynamic;


-- DML
insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorities`,`additional_information`,`autoapprove`,`id`,`deleted`,`version`,`create_time`,`update_time`,`remark`,`name`,`status`,`create_username`,`update_username`,`registered_redirect_uri`,`access_token_validity_seconds`,`refresh_token_validity_seconds`,`auth_types`) values
('system','[\"backend-resources\"]','{MD5}{gJ0C3ekBfCzF/VYZXgBR/ILuzoXK+MkqhasqPqRSPyw=}fcefb209be792c2a7b28ffc8ef97853b','[\"backend\"]','[]',NULL,NULL,1666202288290533378,0,0,1686088556729,1686088556729,'系统内置专用','系统内置',1,'admin','admin',NULL,3600,86400,'[0,1,2,3,4]');