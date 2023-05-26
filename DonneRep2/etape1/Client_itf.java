public interface Client_itf extends java.rmi.Remote {
	public Object reduce_lock(int id) throws java.rmi.RemoteException;
	public void invalidate_reader(int id) throws java.rmi.RemoteException;
	public Object invalidate_writer(int id) throws java.rmi.RemoteException;
	public void subscribe(SharedObject_itf so) throws java.rmi.RemoteException;
	public void unsubscribe(SharedObject_itf so) throws java.rmi.RemoteException;
	public String notifyCallback() throws java.rmi.RemoteException;
}
