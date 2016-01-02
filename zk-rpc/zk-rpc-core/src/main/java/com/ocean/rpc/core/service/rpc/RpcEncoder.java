package com.ocean.rpc.core.service.rpc;

import com.ocean.rpc.core.service.common.SerializationUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ocean on 15/11/22.
 */
@SuppressWarnings("rawtypes")
public class RpcEncoder extends MessageToByteEncoder {

    private static final Logger logger= LoggerFactory.getLogger(RpcEncoder.class);

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass){
        this.genericClass=genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        logger.info("[RpcEncoder - encode ] ");
        if (genericClass.isInstance(o)){
            byte[] data= SerializationUtil.serialize(o);
            byteBuf.writeInt(data.length);
            byteBuf.writeBytes(data);
        }
    }
}
