package com.zmy.jar_test.domain;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.property.ResourceExistsPropertyDefiner;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.util.ContextUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zmy.jar_test.log.Log;
import lombok.Value;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


public class MongoDbConfig {
    RollingFileAppender rollingFileAppender;
    private static MongoClient mongo;
    private static MongoCollection<Document> eventsCollection;
    private static  String host = "129.1.19.28"; // 地址
    private static  int port = 28017;           // 端口号
    private static  String dbName = "eastsoft";       // 库名
    private static  String collectionName = "mongo_info";      // 集合名
    private static  String username = "eastadmin";            // 用户名
    private static  String password = "123456";            // 密码

    private  static final int connectionsPerHost = 10; // 空闲线程池中最大链接数
    private  static final int threadsAllowedToBlockForConnectionMultiplier = 5; //一个线程等待链接可用的最大等待毫秒数
    private  static final int maxWaitTime = 1000 * 60 * 2;  // 最长等待时间
    private  static  int connectTimeout;
    private  static  int socketTimeout;
    private  static  int wtimeout;


    public static void start() {
        checkParams();
        try {
            Log.info("start mongo");
            connectToMongoDB();
        } catch (UnknownHostException e) {
            Log.error("mongo failed", e);
        }
    }

    private static void checkParams() {
        try {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            Map<String, String> map =   lc.getLoggerContextRemoteView().getPropertyMap();
            String hosts =  map.get("logHost");
            String ports =  map.get("logPort");
            String dbNames =  map.get("dbName");
            String collectionNames =  map.get("collectionsName");
            String usernames =  map.get("userName");
            String passwords =  map.get("passWord");
            if(hosts !=null){
                host = hosts;
            }
            if(ports !=null){
                port = Integer.parseInt(ports);
            }
            if(dbNames !=null){
                dbName = dbNames;
            }
            if(collectionNames !=null){
                collectionName = collectionNames;
            }
            if(usernames !=null){
                username = usernames;
            }
            if(passwords !=null){
                password = passwords;
            }
        }catch (NullPointerException e){
            Log.error("mongo config failed");
        }

    }

    private static void connectToMongoDB() throws UnknownHostException {
        // 用户名 数据库 密码
        if (username != null && password != null) {
            MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
            mongo = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credential), buildOptions());
        } else {
            mongo = new MongoClient(new ServerAddress(host, port), buildOptions());
        }

        MongoDatabase db = mongo.getDatabase(dbName);
        eventsCollection = db.getCollection(collectionName);
        Log.info("mongo success");
    }

    private static MongoClientOptions buildOptions() {
        final MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.connectionsPerHost(connectionsPerHost);
        options.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        options.maxWaitTime(maxWaitTime);
        options.connectTimeout(connectTimeout);
        options.socketTimeout(socketTimeout);
        options.maxWaitTime(wtimeout);
        return options.build();
    }

    public static Document toMongoDocument(MongoDocument event) {
        final Document doc = new Document();
        doc.append("date", new Date());
        doc.append("source", event.getSource());
        doc.append("level", event.getLevel());
        doc.append("logger", event.getLogger());
        doc.append("thread", event.getThread());
        doc.append("message", event.getMessage());
        return doc;
    }


    public static void append(MongoDocument eventObject) throws NullPointerException {
        Log.info("mongo message:"+eventObject.getMessage());
        if (mongo != null && eventsCollection != null) {
            eventsCollection.insertOne(toMongoDocument(eventObject));
        } else {
            Log.info("eventsCollection为null");
            start();
            append(eventObject);
        }

    }

    public void stop() {
        Log.info("disconect mongo");
        if (mongo != null)
            mongo.close();
    }

}
