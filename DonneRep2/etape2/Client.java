import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.registry.*;
import java.net.*;

public class Client extends UnicastRemoteObject implements Client_itf {

	private static HashMap<Integer,SharedObject> sharedObjects;

	public static Client_itf client;
	public static Server_itf server;
    private static String URL;
	public Client() throws RemoteException {
		super();
	}




	// initialization of the client layer
	public static void init() {
		sharedObjects = new HashMap<Integer,SharedObject >();
		try {
			client = new Client();
			Client.URL = "//"
						+ InetAddress.getLocalHost().getHostName() + ":"
						+ Registry.REGISTRY_PORT + "/server";

				// Recherche du serveur
				server = (Server_itf) Naming.lookup(URL);
        } catch (Exception e ) {
            e.printStackTrace();
        }
	}
	
	// creation of a shared object
	public static SharedObject create(Object o) {
	    SharedObject shared_obj = null;
		int id;
		try {
			id = server.create(o);
			shared_obj = new SharedObject(id, o);
			sharedObjects.put(id, shared_obj);
			return shared_obj;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	// lookup in the name server
	public static SharedObject lookup(String name) {
		int id;
		try {
			id = server.lookup(name);
			if (sharedObjects.containsKey(id)) {
				return sharedObjects.get(id);
			} else {
				if (id >= 0) {
				    Object obj = server.lock_read(id, client);
				    SharedObject object = new SharedObject(id, obj);
					sharedObjects.put(id, object);
					return object;
					
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}		
	
	// binding in the name server
	public static void register(String name, SharedObject_itf so) {
		int id = ((SharedObject) so).getId();
		try {
			server.register(name, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	

	// request a read lock from the server
	public static Object lock_read(int id) {
		Object returned_obj = null;
		try {
			returned_obj = server.lock_read(id, client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return returned_obj;
	}

	// request a write lock from the server
	public static Object lock_write (int id) {
		Object returned_obj = null;
		try {
			returned_obj = server.lock_write(id, client);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returned_obj;
	}

	// receive a lock reduction request from the server
	public Object reduce_lock(int id) throws java.rmi.RemoteException {
		SharedObject sharedObject = sharedObjects.get(id);
		return sharedObject.reduce_lock();
	}


	// receive a reader invalidation request from the server
	public void invalidate_reader(int id) throws java.rmi.RemoteException {
		SharedObject sharedObject = sharedObjects.get(id);
		sharedObject.invalidate_reader();
	}


	// receive a writer invalidatiogn request from the server
	public Object invalidate_writer(int id) throws java.rmi.RemoteException {
		SharedObject sharedObject = sharedObjects.get(id);
		Object returned_obj = sharedObject.invalidate_writer();
		return returned_obj;
	}

	/*Debut Deuxieme semestre */
	public void subscribe(SharedObject_itf so) {
		 try {
			server.subscribe(this,((SharedObject)so).id,new Callback(((SharedObject)so).obj));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void unsubscribe(SharedObject_itf so) {
		try {
			server.unsubscribe(this,((SharedObject)so).id);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void notifyCallback() {
		System.out.println("I am notified");
	}


	
	public static void exiter(int id) throws RemoteException {
		server.exiter(id);
	}
	/* END deuxieme semestre */
}
