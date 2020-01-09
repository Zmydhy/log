package com.zmy.jar_test.log;

import com.zmy.jar_test.domain.LogInfo;
import com.zmy.jar_test.domain.MongoDbConfig;
import com.zmy.jar_test.domain.MongoDocument;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Log {

    public static void info(java.lang.String s) {
        log.info(s);
    }
    public static void infos(java.lang.String s) {
        log.info(s+"  ("+getInfo().toString()+")");
    }

    public static void info(java.lang.String s, java.lang.Object o) {
        log.info(s, o);
    }

    public static void info(java.lang.String s, java.lang.Object o, java.lang.Object o1) {
        log.info(s, o, o1);
    }

    public static void info(java.lang.String s, java.lang.Object... objects) {
        log.info(s, objects);
    }

    public static void info(java.lang.String s, java.lang.Throwable throwable) {
        log.info(s, throwable);
    }

    public static void warn(java.lang.String s) {
        log.warn(s);
    }
    public static void warns(java.lang.String s) {
        log.warn(s);
    }

    public static void warn(java.lang.String s, java.lang.Object o) {
        log.warn(s, o);
    }

    public static void warn(java.lang.String s, java.lang.Object o, java.lang.Object o1) {
        log.warn(s, o, o1);
    }

    public static void warn(java.lang.String s, java.lang.Object... objects) {
        log.warn(s, objects);
    }

    public static void warn(java.lang.String s, java.lang.Throwable throwable) {
        log.warn(s, throwable);
    }


    public static void error(java.lang.String s) {
        log.error(s);
    }
    public static void errors(java.lang.String s) {
        log.error(s,getInfo(),getInfo());
    }

    public static void error(java.lang.String s, java.lang.Object o) {
        log.error(s, o);
    }

    public static void error(java.lang.String s, java.lang.Object o, java.lang.Object o1) {
        log.error(s, o, o1);
    }

    public static void error(java.lang.String s, java.lang.Object... objects) {
        log.error(s, objects);
    }

    public static void error(java.lang.String s, java.lang.Throwable throwable) {
        log.error(s, throwable);
    }


    public static  void mongo(String msg){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement path = elements[2];
        MongoDocument mongoDocument = new MongoDocument();
        mongoDocument.setMessage(msg);
        mongoDocument.setLevel("mongo");
        mongoDocument.setLogger(path.getClassName());
        mongoDocument.setSource(path.getMethodName());
        mongoDocument.setThread(Thread.currentThread().getName());
        MongoDbConfig.append(mongoDocument);
    }

    public static LogInfo getInfo(){
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        StackTraceElement path = elements[3];
        return  LogInfo.builder().clazzName(path.getClassName()).methodName(path.getMethodName()).lineNum(path.getLineNumber()).build();
    }


}
