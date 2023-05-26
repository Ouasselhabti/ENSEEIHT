import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender implements Runnable{
    private Socket socket;
    private Person person;
    public Sender(Socket s , Person person) {
        this.socket = s;
        this.person = person;
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(this.socket.getOutputStream());
            output.writeObject(this.person);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
