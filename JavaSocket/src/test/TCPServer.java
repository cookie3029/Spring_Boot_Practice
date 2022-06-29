package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		// 클라이언트의 접속을 처리하기 위한 소켓
		ServerSocket ss = null;
		
		// 클라이언트와 통신을 하기 위한 소켓
		Socket socket = null;
		
		try {
			// 서버 소켓을 생성
			ss = new ServerSocket(9999);
			
			while (true) {
				System.out.println("서버 대기중");
				
				// 클라이언트의 접속 대기
				socket = ss.accept();
				
				// 접속자 정보 확인
				System.out.println("접속자 정보 : " + socket.toString());
				
				// 클라이언트가 전송한 정보 확인
				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				
				// 한 줄 읽기
				String str = in.readLine();
				System.out.println("전송된 내용 : " + str);
				
				in.close();
				socket.close();
			}
		} catch (Exception e) {
			System.out.println("소켓 통신 에러");			
			System.out.println(e.getLocalizedMessage());
		}
	}
}
