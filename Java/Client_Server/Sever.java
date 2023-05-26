import java.io.IOException;
import java.net.ServerSocket;

public class Sever {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true) {
            Slave slave = new Slave(serverSocket.accept());
            Thread th = new Thread(slave);
            th.run();
        }
    }
}
