package com.hipac.service;

/**
 * Created by huqiang on 15/11/31.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name+" hello";
    }
}
