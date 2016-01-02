package com.ocean.rpc.core.service.rpc;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ocean on 15/11/15.
 */
public class RpcRequest implements Serializable {
	private static final long serialVersionUID = 4368896732497853014L;

	private String     requestId;

    private String     className;

    private String     methodName;

    private Class<?>[] parameterTypes;

    private Object[]   parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
