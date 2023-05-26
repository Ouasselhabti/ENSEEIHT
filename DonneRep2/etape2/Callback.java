import java.rmi.*;
import java.rmi.server.*;

public class Callback extends UnicastRemoteObject implements Callback_itf {
    Object obj;
    public Callback(Object o) throws RemoteException {
        this.obj = o;
    }

    @Override
    public void stateChanged() throws RemoteException {
        // This method will be called when the state changes
        System.out.println("State has changed: " + this.obj.toString());
    }

}