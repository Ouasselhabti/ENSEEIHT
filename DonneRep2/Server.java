import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server extends UnicastRemoteObject implements Server_itf {
    
    private  HashMap<Integer, ServerObject> liste_objet;
    private HashMap<String, Integer> liste_names;
    private static int compteur = 0;

    public Server() throws RemoteException{
        liste_names = new HashMap<String, Integer>();
        liste_objet = new HashMap<Integer, ServerObject>();
    }

   
    @Override
    public int lookup(String name) throws RemoteException {
        int n = -1;
		if (liste_names.containsKey(name)) {
            n = liste_names.get(name);
            
        } 
        return n;
        
    }


    @Override
    public void register(String name, int id) throws RemoteException {
        if (!this.liste_names.containsKey(name)) {
            this.liste_names.put(name, id);
        } else {
           System.out.println("L'objet est deja enregistré");
        }
    }

    @Override
    public int create(Object o) throws RemoteException {
      ServerObject obj = new ServerObject(o, ++compteur);
        liste_objet.put(compteur, obj);
        return compteur;
    }

    @Override
    public Object lock_read(int id, Client_itf client) throws RemoteException {
        ServerObject obj = liste_objet.get(id);
        obj.lock_read(client);
        return obj.getObj();
    }

    @Override
    public Object lock_write(int id, Client_itf client) throws RemoteException {
        ServerObject obj = liste_objet.get(id);
        obj.lock_write(client);
        return obj.getObj();
    }
    
   
   
    public static void main(String[] args) {
        try {
            
              //URL et PORT par défaut
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Server server = new Server();
			String Server_URL = "//" + InetAddress.getLocalHost().getHostName() + ":" + Registry.REGISTRY_PORT + "/server";
			Naming.rebind(Server_URL, server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
