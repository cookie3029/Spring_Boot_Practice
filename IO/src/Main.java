import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 데이터를 다른 곳으로 전송할 목적으로 만드는 클래스는 반드시 Serializable 인터페이스를 implements해야 합니다.
// 이런 작업을 직렬화라고 합니다.
class Data implements Serializable {
	public String name;
	public int num;
}

public class Main {
	public static void main(String[] args) {
		Data data = new Data();
		
		data.name = "Cookie";
		data.num = 10;
		
		// 객체 단위로 기록하는 스트림 ObjectOuputStream
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./data.data"))) {
			oos.writeObject(data);
			oos.flush();
		} catch (Exception e) {
			
		}
	}
}
