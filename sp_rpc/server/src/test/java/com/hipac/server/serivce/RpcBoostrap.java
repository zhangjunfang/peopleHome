package com.hipac.server.serivce;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by huqiang on 15/11/31.
 */
public class RpcBoostrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server-spring.xml").close();
    }
}
