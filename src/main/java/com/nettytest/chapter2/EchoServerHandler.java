package com.nettytest.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//��ʾһ��ChannelHandler���Ա����Channel��ȫ�ع���
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	public void channelRead(ChannelHandlerContext ctx, Object msg){
		//����Ϣ��¼������̨
		ByteBuf in = (ByteBuf)msg;
		System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
		//�����յ�����Ϣд�������ߣ�������ˢ��վ��Ϣ
		ctx.write(in);
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx){
		//��δ����Ϣ��ˢ��Զ�̽ڵ㣬���ҹرո�channel
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		//��ӡ�쳣��ջ��Ϣ
		cause.printStackTrace();
		//�رո�channel
		ctx.close();
	}
}
