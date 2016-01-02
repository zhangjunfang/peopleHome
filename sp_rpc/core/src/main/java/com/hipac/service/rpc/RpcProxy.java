package com.hipac.service.rpc;

import java.lang.reflect.Method;
import java.util.UUID;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huqiang on 15/11/31.
 */
public class RpcProxy {

    private static final Logger logger = LoggerFactory.getLogger(RpcProxy.class);

    private String              serverAddress;

    private ServiceDiscovery    serviceDiscovery;

    public RpcProxy(String serverAddress){
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery){
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
	public <T> T create(Class<?> interfaceClass,long timeOut) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameters(objects);
                        request.setParameterTypes(method.getParameterTypes());
                        if (serviceDiscovery != null) {
                            serverAddress = serviceDiscovery.discover();//发现服务
                        }
                        String[] array = serverAddress.split(":");
                        String host = array[0];
                        int port = Integer.valueOf(array[1]);
                        RpcClient client = new RpcClient(host, port);
                        RpcResponse response = client.send(request, timeOut);
                        logger.info("[RpcProxy - invoke ] response:{} ", response);
                        if (response.isError()) {
                            logger.error("[RpcProxy - invoke fail ]  {}", response);
                            throw response.getError();
                        }
                        return response.getResult();
                    }
                });
    }


}
