package com.nettytest.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//标示一个ChannelHandler可以被多个Channel安全地共享
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	public void channelRead(ChannelHandlerContext ctx, Object msg){
		//将消息记录到控制台
		ByteBuf in = (ByteBuf)msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		//将接收到的消息写给发送者，而不冲刷出站消息
		ctx.write(in);
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx){
		//将未决消息冲刷到远程节点，并且关闭该channel
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		//打印异常堆栈信息
		cause.printStackTrace();
		//关闭该channel
		ctx.close();
	}
}
