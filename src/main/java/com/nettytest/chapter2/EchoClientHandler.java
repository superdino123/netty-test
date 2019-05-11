package com.nettytest.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//标记该类的实例可以被多个Channel共享
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	public void channelActive(ChannelHandlerContext ctx){
		//当被通知Channel是活跃的时候，发送一条消息
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		//记录已接收消息的转储
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
	}
	
	//在发生异常时，记录错误并关闭Channel
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
