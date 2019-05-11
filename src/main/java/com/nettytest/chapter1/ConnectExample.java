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
		//�첽��������
		ChannelFuture future = channel.connect(
				new InetSocketAddress("192.168.0.1", 25));
		//ע��һ��ChannelFutureListener�Ա��ڲ������ʱ���֪ͨ
		future.addListener(new ChannelFutureListener(){
			
			public void operationComplete(ChannelFuture future){
				if(future.isSuccess()){
					//��������ǳɹ��ģ��򴴽�һ��ByteBuf�Գ�������
					ByteBuf buffer = Unpooled.copiedBuffer(
							"Hello", Charset.defaultCharset());
					//���첽���ݷ��͵�Զ�̽ڵ㣬����һ��ChannelFuture
					ChannelFuture wf = future.channel().writeAndFlush(buffer);
				}else{
					//��������������������ԭ���Throwable
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
