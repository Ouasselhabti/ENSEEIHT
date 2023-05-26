import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int num_of_senders = 10;
    public static void main(String[] args) throws IOException {
        for (int i = 0 ; i < num_of_senders ; i++) {
            Socket s = new Socket("localhost",8081);
            Sender sender = new Sender(s , new Person("Person"+i,i*i+1,"Job"+i));
            Thread thread = new Thread(sender);
            thread.run();
            s.close();
        }
    }
}
