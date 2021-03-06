package com.ocean.exam.rpc.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ocean on 15/12/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:server-beans.xml")
public class HelloServiceTest {

    @Autowired
    HelloService helloService;

    @Test
    public void sayHello(){
        System.out.println(helloService.sayHello("haha"));
    }

}
