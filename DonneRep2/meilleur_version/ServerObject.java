import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ServerObject {
	 //Attributs du ServerObject
	private Object obj;
	private LockState lock;
	private Client_itf writer;
	private List<Client_itf> readers;
    private int id;
	/*Debut deuxieme semestre*/
	private List<Client_itf> subs_clients;
	/*end deuxieme semstre */

    
    
	public enum LockState {
		RL,
		WL,
		NL,
	}

	// Constructor
	public ServerObject(Object obj, int id) {
	    this.id = id;
		this.obj = obj;
		this.lock = LockState.NL;
		this.writer = null;
		this.readers = new ArrayList<Client_itf>();
		this.subs_clients = new ArrayList<>(); 
	}
	
	
	// Getters and setters
	public Object getObj() {
		return obj;
	}
	public synchronized void setObj(Object obj) {
		this.obj = obj;
		/*try {
			notifySubscribers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}*/
	}
	
  
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
	public LockState getLock() {
		return lock;
	}
	public void setLock(LockState lock) {
		this.lock = lock;
	}
	public Client_itf getWriter() {
		return writer;
	}
	public void setWriter(Client_itf writer) {
		this.writer = writer;
	}
	public List<Client_itf> getReaders() {
		return readers;
	}
	public void setReaders(List<Client_itf> readers) {
		this.readers = readers;
	}

    public synchronized Object lock_read(Client_itf client)  {
       try{
		switch (lock) {
			case NL:
				break;
			case RL:
				break;
			case WL:
				this.obj = writer.reduce_lock(id);
				this.readers.add(writer);
				break;
			default:
				break;
		}
		this.lock = LockState.RL;
		this.readers.add(client);
		this.writer = null;	
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return obj;
	}

    
	public synchronized Object lock_write(Client_itf client) {
        switch(this.lock){
			case WL :
				try{
					this.obj = this.writer.invalidate_writer(this.id);     
				}catch(RemoteException e){
					e.printStackTrace();
				}
			    break;
            case RL :
                this.readers.remove(client);
           	    for(Client_itf cl : this.readers){
                    try{
                        cl.invalidate_reader(this.id);
                    }catch(RemoteException e) {
                        e.printStackTrace();
                    }
                }
            break;
			default:
				break;
        }
        this.lock = LockState.WL;
      	this.writer = client;
        this.readers.clear();
		/*Debut deuxieme semestre */
		    /* Notify subscribers of state change
			try {
				this.notifySubscribers();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		/*end deuxieme semestre */
        return obj;
	}
	public synchronized void subscribe(Client_itf client,Callback_itf callback) throws RemoteException {
		if (!this.subs_clients.contains(client)) {
			this.subs_clients.add(client);
			 

			/* 
			for(Client_itf sub : subs_clients){
				try{
					sub.notifyCallback();
				}catch(RemoteException e) {
					e.printStackTrace();
				}
			}
			*/


			System.out.println("Client subscribed Successfully!!");
		} else {
			System.out.println("Client already subscribed !");
		}
	}
	
	public synchronized void unsubscribe(Client_itf client) throws RemoteException {
			if (this.subs_clients.contains(client)) {
				this.subs_clients.remove(client);
				 
				/*
				for(Client_itf sub : subs_clients){
					try{
						sub.notifyCallback();
					}catch(RemoteException e) {
						e.printStackTrace();
					}
				}
				*/ 


				System.out.println("Client unsubscribed Successfully!!");
		} else {
				System.out.println("Client is not subscribed!!");
		}
	}
	public synchronized void notifySubscribers(Client_itf client2) throws RemoteException {
		for (Client_itf client : subs_clients) {
			if (!client.equals(client2))
				client.notifyCallback();
		}
	}
	
}

