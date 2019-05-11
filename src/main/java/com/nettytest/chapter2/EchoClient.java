package com.nettytest.chapter2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	
	private final String host;
	private final int port;
	
	public EchoClient(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			//����Bootstrap
			Bootstrap b = new Bootstrap();
			//ָ��EventLoopGroup�Դ���ͻ����¼�����Ҫ������NIO��ʵ��
			b.group(group)
				//������NIO�����Channel����
				.channel(NioSocketChannel.class)
				.remoteAddress(new InetSocketAddress(host, port))
				//����Channelʱ����ChannelPipeline�����һ��EchiClientHandlerʵ��
				.handler(new ChannelInitializer<SocketChannel>(){
				
					public void initChannel(SocketChannel ch) throws Exception{
						ch.pipeline().addLast(new EchoClientHandler());
					}
			});
			//���ӵ�Զ�̽ڵ㣬�����ȴ�֪���������
			ChannelFuture f = b.connect().sync();
			//������ֱ��Channel�ر�
			f.channel().closeFuture().sync();
		} finally{
			//�ر��̳߳ز����ͷ����е���Դ
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws Exception{
		String host = "";
		int port = 8001;
		new EchoClient(host, port).start();
	}
}
