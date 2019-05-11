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
		//����һ��socket�����ڼ���ָ���˿ڵ���������
		ServerSocket serverSocket = new ServerSocket(port);
		//��accept�ĵ��ý���������һֱ�ȵ�һ�����ӱ�����
		Socket clientSocket = serverSocket.accept();
		//��Щ�����������Ը��׽��ֵ�������
		BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
;		//��ʼѭ������
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
