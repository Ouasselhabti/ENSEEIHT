import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            byte[] buffer = new byte[5000];
            Socket socket = new Socket("localhost",8080);
            OutputStream output = socket.getOutputStream();
            InputStream input = socket.getInputStream();
            output.write("Hellomotherfuckers , idk if it server1 or server2 pls answer me .....".getBytes());
            input.read(buffer);
            System.out.println(new String(buffer));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
