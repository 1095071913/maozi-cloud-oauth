# 目录说明

```text
maozi-cloud-sso-user                 (SSO用户实体)
maozi-cloud-service-sso              (SSO服务实现)
```

<br/>

# 包说明

<br/>

**maozi-cloud-service-sso目录**

```text
com.maozi.                                          
  SSOApplication.java                               (启动类)
  sso.                                              (父模块sso)
    oauth.                                          (子模块oauth)
      api.
        OauthService.java                           (子模块服务接口)
        impl.                        
          OauthServiceImpl.java                     (子模块服务接口实现)
          rpc.
            v1.
              OauthServiceRpcV1Impl.java            (RPC V1 接口实现)
            v2.                                     (RPC V2 接口实现)
          rest.
            v1.
              app.
                OauthServiceRestV1AppImpl.java      (APP HTTP V1 接口实现)
              pc.
                OauthServiceRestV1PcImpl.java       (PC HTTP V1 接口实现)
      utils.                                        (工具)
      domain.                                       (domain object)
      mapper.                                       (domain mapper)
      config.                                       (配置)
      properties.                                   (配置属性)
    
```

<br/>

<br/>

# 数据库导入

数据库名字：maozi-cloud-sso-localhost-db

maozi-cloud-sso为父级目录名字

localhost为Nacos配置中cloud-default.yml里面的

```yaml
project: 
  environment: ${environment:localhost}
```

默认为localhost，可根据环境变量environment定义

db为固定名称

<br/>

如：https://gitee.com/xmaozi/maozi-cloud-user  定义为：

maozi-cloud-user-test-db 

maozi-cloud-user-production-db

<br/>

<br/>

```sql
/*
SQLyog Trial v13.1.8 (64 bit)
MySQL - 8.0.27 : Database - maozi-cloud-sso-localhost-db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`maozi-cloud-sso-localhost-db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `maozi-cloud-sso-localhost-db`;

/*Table structure for table `oauth_access_token` */

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_approvals` */

CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `oauth_approvals` */

/*Table structure for table `oauth_client_details` */

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) DEFAULT NULL COMMENT '账号id',
  `resource_ids` varchar(255) DEFAULT 'backend-resources',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '密码',
  `scope` varchar(255) DEFAULT 'backend' COMMENT '范围',
  `authorized_grant_types` varchar(255) DEFAULT 'client_credentials',
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` varchar(255) DEFAULT '86400',
  `refresh_token_validity` varchar(255) DEFAULT '86400',
  `additional_information` varchar(255) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  `id` bigint NOT NULL,
  `create_by` bigint DEFAULT NULL COMMENT '创建人',
  `deleted` int DEFAULT '0' COMMENT '逻辑删除键',
  `version` int DEFAULT '0' COMMENT '版本号',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='租户/渠道表';

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`,`id`,`create_by`,`deleted`,`version`,`create_time`,`update_time`,`remark`,`name`) values 
('client','backend-resources','{MD5}{iIjvOixqPCPs6LdceBoM0JkRkoqlGh3+mQsBRmPzbQw=}1dbf70cfe45126875cbe962478a7bdaa','backend','client_credentials,password,refresh_token',NULL,NULL,'86400','86400',NULL,NULL,0,NULL,0,0,NULL,NULL,'备注','万循');

/*Table structure for table `oauth_client_token` */

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `oauth_client_token` */

/*Table structure for table `oauth_code` */

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `undo_log` */

CREATE TABLE `undo_log` (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb3 COMMENT='AT transaction mode undo table';

/*Data for the table `undo_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

```

<br/>

<br/>

# 项目编译依赖

**此项目依赖  https://gitee.com/xmaozi/maozi-cloud-user**

**所以先将以下项目编译了**

**maozi-cloud-user/maozi-cloud-service-rpc-api-user**

<br/>

编译完成后再去编译本项目就不会报错了

**记住.git文件不可以缺少，不然也会报错**

<br/>

# Nacos配置

<br/>

**Nacos地址默认为localhost:8081，若不是则添加环境变量NACOS_CONFIG_SERVER**

或找到 maozi-cloud-service-sso/src/main/resources/bootstrap.properties 添加

```
spring.cloud.nacos.config.server-addr=localhost:8081
```

<br/>

Nacos找到 **maozi-cloud-sso.yml** 配置

```yaml
# 端口
application-port: 1000

#数据库配置,这里的值都是默认值,如果值没变可以将以下数据库配置注释掉
application-datasource-jdbc-url: localhost:3306
application-datasource-jdbc-username: root
application-datasource-jdbc-password: password

# 白名单
application-redis-database: 3

application-project-whitelist: /oauth/check_token,/oauth/token/get,/oauth/token/check,/oauth/token/destroy
```

<br/>

Nacos找到** boot-redis.yml** 配置

```yaml
spring: 
  redis: 
    enabled: true
    host: localhost
    port: 6379
    password: 812840531zhang
    lettuce:
      shutdown-timeout: 300ms
      pool:
        max-active: 600
        max-wait: 1000
        max-idle: 300
        min-idle: 0
    timeout: 60000
#    cluster:
#      nodes: 
#        - 192.168.1.111:7001
#        - 192.168.1.112:7001
#        - 192.168.1.110:7002
#        - 192.168.1.110:7001
#        - 192.168.1.111:7002
#        - 192.168.1.112:7001

```

**该项目依赖Redis ，将Redis参数修改即可**

<br/>

<br/>

# 启动服务

<br/>

```
[ 2023-03-03 00:10:17 ]  [ level：INFO ]  [ TID: N/A ]  [ app：null ]  [ environment：null ]  [ No active profile set, falling back to 1 default profile: "default" ]
log4j:WARN No appenders could be found for logger (org.apache.dubbo.common.Version).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
[ 2023-03-03 00:11:21 ]  [ level：INFO ]  [ TID: N/A ]  [ app：maozi-cloud-user ]  [ environment：localhost ]  [ Started UserApplication in 71.894 seconds (JVM running for 73.315) ]
[ 2023-03-03 00:11:21 ]  [ level：INFO ]  [ TID: N/A ]  [ app：maozi-cloud-user ]  [ environment：localhost ]  [ uptime：72469 ms ]  [ config：cloud-default.yml,cloud-nacos.yml,cloud-dubbo.yml,boot-admin.yml,api-whitelist.yml,cloud-security.yml,boot-redis.yml,boot-swagger.yml,boot-arthas.yml,cloud-sentinel.yml,boot-datasource.yml,boot-mybatisplus.yml ]  [ nacosAddr：127.0.0.1:8848 net ]  [ subscribe：null ]

```

<br/>

启动成功 ，访问 **localhost:1000/doc.html**

若已启动 https://gitee.com/xmaozi/maozi-cloud-user 即可测试接口

访问 **localhost:2000/doc.html**

【后台】【V1】用户模块/用户登录

取消请求头选择

username=admin

password=812840531zhang
