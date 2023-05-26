import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Slave implements Runnable {
    private Socket socket;

    public Slave(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
            Person readedPerson = (Person) input.readObject();
            System.out.println("Yeaah got the Person from the client :");
            System.out.println(readedPerson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
