package test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
	public static void main(String[] args) {
		try {
			// UDP 소켓 클래스 생성
			DatagramSocket dsoc = new DatagramSocket(4445);
			
			// 데이터를 저장하기 위한 바이트 배열 생성
			byte [] data = new byte[65536];
			
			while(true) {
				System.out.println("받을 준비 완료");
				
				// 데이터를 전송받기 위한 준비가 완료됨 - 데이터를 전송받아야 다음으로 넘어감
				DatagramPacket dp = new DatagramPacket(data, data.length);
				dsoc.receive(dp);
				
				// 보낸 곳 주소 확인
				System.out.println("보낸 곳 : " + dp.getAddress().getHostAddress());
			
				// 전송받은 데이터 확인
				String utf8String = 
						new String(new String(dp.getData()).trim().getBytes("UTF-8"));
				System.out.println("받은 메시지 : " + utf8String);
				
				String msg = "피카츄입니다.";
				
				// 보낸 곳의 주소를 저장
				InetAddress address = dp.getAddress();
				
				int port = dp.getPort();
				
				// UDP로 전송할 데이터 생성
				dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, port);
				
				// 데이터 전송\
				dsoc.send(dp);
			}
		} catch (Exception e) {
			System.out.println("소켓 통신 에러");
			System.out.println(e.getLocalizedMessage());
		}
	}
}
