# Log使用文档
## 第一步 在pom.xml文件中加入
```
    <repositories>
        <repository>
            <id>maven-public</id>
            <url>http://129.1.19.28:8081/repository/maven-public/</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
```
## 第二步
### 第一种方式：

添加以下依赖
```
	     <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongo-java-driver</artifactId>
            <version>3.4.2</version>
        </dependency>

        <dependency>
            <groupId>com.zmy</groupId>
            <artifactId>log</artifactId>
            <version>0.0.1</version>
        </dependency>
```
### 第二种方式
直接添加
```      
        <dependency>
            <groupId>com.zmy</groupId>
            <artifactId>log</artifactId>
            <version>0.0.3</version>
        </dependency>
```
### 第三种方式

添加带样式的依赖
```
        <dependency>
            <groupId>com.zmy</groupId>
            <artifactId>log</artifactId>
            <version>0.0.5</version>
        </dependency>
```
设置配置文件（自定义）
```
logs:
  source: haha
  mongos:
    host: 129.1.19.28
    port: 28017
    dbName: eastsoft
    userName: eastadmin
    passWord: 123456
    collectionsName: log_info
```
默认里面含有logback-spring.xml   
支持自定义logback-spring.xml，直接在resource文件夹下添加即可   

## 第三步
 
1、Log.info（msg） 输出普通信息
 
2、Log.infos(msg) 输出具体调用详细信息
 
warn和error 使用同上面一样。

3、Log.mongo(msg) 保存到日志信息到mongo数据库
 
## Ps:
第二种依赖方式默认集成了以下依赖包（如有重复，请直接将pom文件中响应依赖删除即可）   
```
<dependencies>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.8</version>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>3.4.2</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.1.11</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.1.11</version>
    </dependency>
</dependencies>
```
