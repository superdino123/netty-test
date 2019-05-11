package com.nettytest.chapter2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	
	private final int port;
	
	public EchoServer(int port){
		this.port = port;
	}
	
	public static void main(String[] args) throws Exception{
		int port = 8001;
		
		//���÷�������start()����
		new EchoServer(port).start();
	}
	
	public void start() throws Exception{
		final EchoServerHandler serverHandler = new EchoServerHandler();
		
		//����EventLoopGroup
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			//����ServerBootstrap
			ServerBootstrap b = new ServerBootstrap();
			b.group(group)
				//ָ����ʹ�õ�NIO����Channel
				.channel(NioServerSocketChannel.class)
				//ʹ��ָ���Ķ˿������׽��ֵ�ַ
				.localAddress(new InetSocketAddress(port))
				//���һ��EchoServerHandler����Channel��ChannelPipeline
				.childHandler(new ChannelInitializer<SocketChannel>(){
					public void initChannel(SocketChannel ch)
							throws Exception{
						//EchoServerHandler ����עΪ@Shareable���������ǿ�������ʹ��ͬ����ʵ��
						ch.pipeline().addLast(serverHandler);
					}
				});
			//�첽�ذ󶨷�����������sync()���������ȴ�ֱ�������
			ChannelFuture f = b.bind().sync();
			//��ȡChannel��CloseFuture������������ǰ�߳�ֱ�������
			f.channel().closeFuture().sync();
		}
		finally{
			//�ر�EventLoopGroup���ͷ����е���Դ
			group.shutdownGracefully().sync();
		}
	}
}
