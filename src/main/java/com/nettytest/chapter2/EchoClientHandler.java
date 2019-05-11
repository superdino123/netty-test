package com.nettytest.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

//��Ǹ����ʵ�����Ա����Channel����
@Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	public void channelActive(ChannelHandlerContext ctx){
		//����֪ͨChannel�ǻ�Ծ��ʱ�򣬷���һ����Ϣ
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		//��¼�ѽ�����Ϣ��ת��
		System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
	}
	
	//�ڷ����쳣ʱ����¼���󲢹ر�Channel
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
