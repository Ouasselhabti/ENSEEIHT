import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback_itf extends Remote {
    public void stateChanged() throws RemoteException;
}
