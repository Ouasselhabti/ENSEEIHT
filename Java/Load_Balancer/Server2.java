
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
    public static void main(String[] args) throws IOException {
        //Launching the server socket that will listen for requests (basically from the load balancers)
        ServerSocket serverSocket  = new ServerSocket(8082);
        while(true) {
            byte[] buffer = new byte[5000];
            //Accepting the request from the load balancer (blocking method)
            Socket sockWithLoad = serverSocket.accept();
            //Extracting the connection with the load balancer
            InputStream input = sockWithLoad.getInputStream();
            OutputStream output = sockWithLoad.getOutputStream();
            input.read(buffer);
            System.out.println(new String(buffer));
            output.write("im server 2 bitchhh".getBytes());
            sockWithLoad.close();
        }
    }
}