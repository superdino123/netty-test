package com.nettytest.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingIoExample {

	public static void main(String[] args) throws IOException{
		
		BlockingIoExample example = new BlockingIoExample();
		example.serve(8001);
		System.out.println("finish");
	}
	
	public void serve(int port) throws IOException{
		//创建一个socket，用于监听指定端口的连接请求
		ServerSocket serverSocket = new ServerSocket(port);
		//对accept的调用将会阻塞，一直等到一个连接被建立
		Socket clientSocket = serverSocket.accept();
		//这些流对象都派生自该套接字的流对象
		BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
;		//开始循环处理
		String request, response;
		while((request = in.readLine()) != null){
			if("Done".equals(request)){
				break;
			}
			response = request.toString();
			out.println(response);
			System.out.println(response);
		}
	}
}
