import java.rmi.RemoteException;

public interface Server_itf extends java.rmi.Remote {
    public int lookup(String name) throws java.rmi.RemoteException;
	public void register(String name, int id) throws java.rmi.RemoteException;
	public int create(Object o) throws java.rmi.RemoteException;
	public Object lock_read(int id, Client_itf client) throws java.rmi.RemoteException;
	public Object lock_write(int id, Client_itf client) throws java.rmi.RemoteException;

	/*PROJET DEUXIEME SEMESTRE */
	public void subscribe(Client_itf client, int objectId, Callback_itf callback) throws RemoteException;
    public void unsubscribe(Client_itf client, int objectId) throws RemoteException;
    public void pub(int objectId) throws RemoteException;
	public void exiter(int id) throws RemoteException;
}
