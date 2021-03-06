package com.ocean.rpc.core.service.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ocean.rpc.core.service.RpcService;
import com.ocean.rpc.core.service.ServiceRegistry;

/**
 * Created by ocean on 15/11/20.
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger logger     = LoggerFactory.getLogger(RpcServer.class);

    private String              serverAddress;

    private ServiceRegistry     serviceRegistry;

    private Map<String, Object> handlerMap = new HashMap<String, Object>();           // 存放接口名与对象之间的映射关系

    public RpcServer(String serverAddress){
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry){
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object o : serviceBeanMap.values()) {
                String interfaceName = o.getClass().getAnnotation(RpcService.class).value().getName();
                handlerMap.put(interfaceName, o);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new RpcHandler(handlerMap));
                        }
                    }).option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            String [] array=serverAddress.split(":");
            String host=array[0];
            int port=Integer.valueOf(array[1]);
            ChannelFuture future=bootstrap.bind(host,port).sync();
            logger.info("[RpcServer - afterPropertiesSet ] host:{} port:{} ",host,port);
            if (serviceRegistry!=null){
                serviceRegistry.register(serverAddress);
            }
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("[RpcServer - afterPropertiesSet fail ]",e);
        } finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
