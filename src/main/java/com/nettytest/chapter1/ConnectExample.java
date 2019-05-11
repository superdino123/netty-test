package com.nettytest.chapter1;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ConnectExample {

	private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
	
	public static void connect(){
		Channel channel = CHANNEL_FROM_SOMEWHERE;
		//异步建立连接
		ChannelFuture future = channel.connect(
				new InetSocketAddress("192.168.0.1", 25));
		//注册一个ChannelFutureListener以便在操作完成时获得通知
		future.addListener(new ChannelFutureListener(){
			
			public void operationComplete(ChannelFuture future){
				if(future.isSuccess()){
					//如果操作是成功的，则创建一个ByteBuf以持有数据
					ByteBuf buffer = Unpooled.copiedBuffer(
							"Hello", Charset.defaultCharset());
					//将异步数据发送到远程节点，返回一个ChannelFuture
					ChannelFuture wf = future.channel().writeAndFlush(buffer);
				}else{
					//如果发生错误，则访问描述原因的Throwable
					Throwable cause = future.cause();
					cause.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args){
		
		connect();
	}
}
