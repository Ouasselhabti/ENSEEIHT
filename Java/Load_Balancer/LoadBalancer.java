import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class LoadBalancer {
	static String[] hosts = {"localhost","localhost"};
	static int[] ports = {8081,8082};
	static Random rand = new Random();
	static int nbHosts = 2;

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(8080);
		while(true) {
			try {
				byte[] buffer = new byte[5000];
				byte[] buffer2 = new byte[5000];
				Socket sWithClient = socket.accept();
				Socket sWithHost = new Socket("localhost",ports[rand.nextInt(0,ports.length)]);
				InputStream inputClient = sWithClient.getInputStream();
				OutputStream outputClient = sWithClient.getOutputStream();
				InputStream inputHost = sWithHost.getInputStream();
				OutputStream outputHost = sWithHost.getOutputStream();
				inputClient.read(buffer);
				outputHost.write(buffer);
				inputHost.read(buffer2);
				outputClient.write(buffer2);
				sWithHost.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}