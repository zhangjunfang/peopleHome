package com.ocean.exam.rpc.server;

import com.ocean.rpc.core.service.RpcService;

/**
 * Created by ocean on 15/12/22.
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name+" hello";
    }
}
