package com.ocean.exam.rpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ocean on 15/12/31.
 */
public class RpcBoostrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server-spring.xml").close();
    }
}
