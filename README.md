# Pdman

有需要的可以下载Pdman导入[项目数据](https://gitee.com/xmaozi/maozi-cloud-parent-script/tree/release/maozi-cloud-service/maozi-cloud-service-pdman)

<img src="http://zs-dev01.oss-cn-shenzhen.aliyuncs.com/202306/0396152f-b779-46e0-bc90-9d56beb3e409.png" />

<img src="http://zs-dev01.oss-cn-shenzhen.aliyuncs.com/202306/6133de91-6a29-4c18-a2d9-c2077998fa3c.png" />

<br/>

<br/>

<br/>

# ApiFox

有需要的可以下载Apifox导入[项目数据](https://gitee.com/xmaozi/maozi-cloud-parent-script/blob/release/maozi-cloud-service/maozi-cloud-service-apifox/maozi-cloud-oauth.apifox.json)

<img src="http://zs-dev01.oss-cn-shenzhen.aliyuncs.com/202306/38d4037f-e121-43d1-8949-e1c766a9305d.png" />

<img src="http://zs-dev01.oss-cn-shenzhen.aliyuncs.com/202306/159c99f2-7ef8-4dfa-a581-5274aa62808c.png" />

<br/>

<br/>

# 目录说明

```text
maozi-cloud-oauth-vo                   (ViewObject)
maozi-cloud-oauth-dto                  (DataTransferObject)
maozi-cloud-oauth-enum                 (枚举)
maozi-cloud-service-oauth              (服务实现)
maozi-cloud-service-rpc-api-oauth      (RPC接口)
maozi-cloud-service-rest-api-oauth     (REST接口)
```

<br/>

# 包说明

<br/>

**maozi-cloud-service-oauth目录**

```text
com.maozi.                                          
  OauthApplication.java                               (启动类)
  oauth.                                              (父模块oauth)
    client.                                         (子模块client)
    token.                                          (子模块token)
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

数据库名字默认为：maozi-cloud-oauth-localhost-db

maozi-cloud-oauth为父级目录名字

localhost为Nacos配置中cloud-default.yml里面的

```yaml
project: 
  environment: ${environment:localhost}
```

默认为localhost，可根据环境变量environment定义

db为固定名称

<br/>

如：https://gitee.com/xmaozi/maozi-cloud-system  定义为：

maozi-cloud-system-test-db 

maozi-cloud-system-production-db

<br/>

**若需要修改数据库名则看到下面的Nacos配置篇**

<br/>

<br/>

# 项目编译依赖

**此项目依赖  https://gitee.com/xmaozi/maozi-cloud-system**

**所以先将以下项目编译了**

**maozi-cloud-user/maozi-cloud-service-rpc-api-system**

<br/>

编译完成后再去编译本项目就不会报错了

**记住.git文件不可以缺少，不然也会报错**

<br/>

<br/>

# Nacos配置

<br/>

**Nacos地址默认为localhost:8081，若不是则添加环境变量NACOS_CONFIG_SERVER**

或找到 maozi-cloud-service-oauth/src/main/resources/bootstrap.properties 添加

```
spring.cloud.nacos.config.server-addr={Nacos地址}
```

<br/>

Nacos找到 **maozi-cloud-oauth.yml** 配置

```yaml
# 端口
application-port: 1000

#数据库配置,这里的值都是默认值,如果值没变可以将以下数据库配置注释掉
application-datasource-jdbc-url: localhost:3306
application-datasource-jdbc-username: root
application-datasource-jdbc-password: password
application-datasource-db-name: maozi-cloud-oauth-localhost-db

# 白名单
application-redis-database: 3

application-project-whitelist: /oauth/check_token,/oauth/token/get,/oauth/token/check,/oauth/token/destroy
```

<br/>

Nacos找到**boot-redis.yml** 配置

```yaml
spring: 
  redis: 
    enabled: true
    host: localhost
    port: 6379
    password: 
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

## 设置Jvm VM参数

**因为用的是JDK17 所以要设置以下VM餐宿**

```text
--add-opens java.base/java.math=ALL-UNNAMED  --add-opens java.base/java.lang=ALL-UNNAMED  --add-opens java.base/java.lang.reflect=ALL-UNNAMED
```

<br/>

<br/>

# 启动服务

<br/>

```
[ 2023-03-03 00:11:21 ]  [ level：INFO ]  [ TID: N/A ]  [ app：maozi-cloud-user ]  [ environment：localhost ]  [ uptime：72469 ms ]  [ config：cloud-default.yml,cloud-nacos.yml,cloud-dubbo.yml,boot-admin.yml,api-whitelist.yml,cloud-oauth.yml,boot-redis.yml,boot-swagger.yml,boot-arthas.yml,cloud-sentinel.yml,boot-datasource.yml,boot-db.yml ]  [ nacosAddr：127.0.0.1:8848 net ]  [ subscribe：null ]
```

<br/>

启动成功 ，访问 **localhost:1000/doc.html**

若已启动 https://gitee.com/xmaozi/maozi-cloud-system 即可测试接口

访问 **localhost:2000/doc.html**

【后台】【V1】用户模块/用户登录

取消请求头选择

username=admin

password=812840531zhang

或者使用ApiFox跑一次全流程测试用例
