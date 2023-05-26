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
	}
	
	
	// Getters and setters
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
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
        return obj;
	}

	
}

