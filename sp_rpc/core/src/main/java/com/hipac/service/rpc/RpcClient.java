package com.hipac.service.rpc;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by huqiang on 15/11/31.
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private String              address;

    private int                 port;

    private RpcResponse response;

    private final Object obj=new Object();

    public RpcClient(String address, int port){
        this.address = address;
        this.port = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.response=rpcResponse;
        logger.info("[RpcClient - channelRead0 ] {} ",response);
        synchronized (obj){
            obj.notifyAll();//收到响应，唤醒线程
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[RpcClient - exceptionCaught fail ]",cause);
        ctx.close();
    }

    public RpcResponse send(RpcRequest request) throws Exception{
        return send(request, 5000);
    }

    public RpcResponse send(RpcRequest request,long timeOut) throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        logger.info("[RpcClient - send ] request: {} ",request);
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new RpcEncoder(RpcRequest.class))
                                    .addLast(new RpcDecoder(RpcResponse.class))
                                    .addLast(RpcClient.this);
                        }
                    }).option(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture future=bootstrap.connect(address,port).sync();
            future.channel().writeAndFlush(request).sync();
            synchronized (obj){
                obj.wait(timeOut);
            }
            if (response!=null){
                future.channel().closeFuture().sync();
            }
        } catch (Exception e) {
            logger.error("[RpcClient - send fail ]", e);
        } finally {
            group.shutdownGracefully();
        }
        return response;
    }
}
