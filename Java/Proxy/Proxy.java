import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Proxy {
    public static final int PORT = 8080;
    public static final int BUFF_SIZE = 8000;
    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(PORT);
            while (true) {
                Socket clientSocket = s.accept();
                Socket targetSocket = new Socket(Server.HOST,Server.HOST_PORT);
                Thread thread1 = new Thread(() -> {
                    try (InputStream in = clientSocket.getInputStream(); OutputStream out = targetSocket.getOutputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    } catch (IOException e) {
                        // handle exception
                    }
                });
                Thread thread2 = new Thread(() -> {
                    try (InputStream in = targetSocket.getInputStream(); OutputStream out = clientSocket.getOutputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                        }
                        } catch (IOException e) {
                        // handle exception
                        }
                        });
                        thread1.start();
                        thread2.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}