package com.hipac.server.serivce;

import com.hipac.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huqiang on 15/11/31.
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
