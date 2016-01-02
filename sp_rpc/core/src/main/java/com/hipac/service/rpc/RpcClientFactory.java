package com.hipac.service.rpc;

import org.springframework.beans.factory.FactoryBean;

/**
 * Created by huqiang on 15/11/31.
 */
public class RpcClientFactory<T> implements FactoryBean<T> {

    private RpcProxy rpcProxy;

    private Class<T> interfaceClass;

    private T        obj;

    private long     timeOut = 5000;

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRpcProxy(RpcProxy rpcProxy) {
        this.rpcProxy = rpcProxy;
    }

    @Override
    public T getObject() throws Exception {
        if (obj != null) {
            return obj;
        }
        obj = rpcProxy.create(interfaceClass, timeOut);
        return obj;
    }

    @Override
    public Class<T> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
