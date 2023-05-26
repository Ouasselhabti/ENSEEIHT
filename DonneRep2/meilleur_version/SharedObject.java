import java.io.*;
import java.rmi.RemoteException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class SharedObject implements Serializable, SharedObject_itf {

	public int id;
	public Object obj;
	// Lock state de l'objet
	public LockState lock;
	private boolean waiter;
	public ReentrantLock mutex; 
	public Condition AP;
	

	public SharedObject(int id, Object obj) {
		this.id = id;
		this.obj = obj;
		this.lock = LockState.NL;
		this.waiter = false;
		this.mutex = new ReentrantLock();
	}

	
    public Object getObj() {
		return obj;
	}

	public int getId() {
		return id;
	}
	
	

	

	// 
	public void lock_read() {
	    mutex.lock();
		boolean thereIsChange = false;
			while (this.waiter) {
				try {
					AP.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// If the lock state is "no local lock" or "read lock cached" then set the lock state to "read lock taken"
			if (this.lock == LockState.NL) {
				this.lock = LockState.RLT;
				thereIsChange = true;
			} else if (this.lock == LockState.RLC) {
				this.lock = LockState.RLT;
			}
			// If the lock state is "write lock cached" then set the lock state to "read lock taken and write lock cached"
			else if (this.lock == LockState.WLC) {
				this.lock = LockState.RLT_WLC;
			} else {
				System.err.println("Ce lock : " + this.lock + "n'est pas valide");
			}
			mutex.unlock();
		
			//Inform Client when there is a change
			if (thereIsChange) {
					this.obj = Client.lock_read(this.id);
				
			}
		}

	
	public void lock_write() {
	    mutex.lock();
	   
		boolean thereIsChange = this.lock == LockState.NL || this.lock == LockState.RLC;
			while (this.waiter) {
				try {
					AP.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (thereIsChange || this.lock == LockState.WLC ) {
			     this.lock = LockState.WLT;
			} else {
				System.err.println("Ce lock : " + this.lock + "n'est pas valide");
			}
		    mutex.unlock();
			if (thereIsChange) {
				this.obj = Client.lock_write(this.id);
			}
	} 

	
	public synchronized void unlock() {
				
				if (this.lock == LockState.RLT) {
					this.lock = LockState.RLC;
				}
				else if (this.lock == LockState.WLT || this.lock == LockState.RLT_WLC ) {
					this.lock = LockState.WLC;
					try {
						Client.server.pub(Client.client,this.id);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
			         notify();
	           	} catch (Exception e) {
			            e.printStackTrace();
		        }		
				}
	
	


	
	public synchronized Object reduce_lock() {
		this.waiter = true;


		if (this.lock == LockState.WLT) {
			while (this.lock == LockState.WLT ) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		else if (this.lock == LockState.WLC) {
			this.lock = LockState.RLC;
		}
		else if (this.lock == LockState.RLT_WLC) {
			this.lock = LockState.RLT;
		}
		else {
			System.err.println("Ce lock : " + this.lock + "n'est pas valide");
		}
		this.waiter = false;
		try {
			notify();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.obj;
	}

	// Callback invoked remotely by the server
	public synchronized void invalidate_reader() {
		this.waiter = true;	
		if (this.lock == LockState.RLT) {
			while (this.lock == LockState.RLT) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (this.lock == LockState.RLC) {
			this.lock = LockState.NL;
		}
		this.waiter = false;
		try {
			notify();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Object invalidate_writer() {
		this.waiter = true;
		if (this.lock == LockState.WLT) {
			while (this.lock == LockState.WLT) {
				try {
					wait();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
		if (this.lock == LockState.RLT_WLC) {
			while (this.lock == LockState.RLT_WLC) {
				try {
					wait();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
		 else if (this.lock == LockState.WLC) {
			this.lock = LockState.NL;
		}
		this.waiter = false;
		try {
			notify();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.obj;
	}
	@Override
	public String toString() {
		return "Client( "+this.id+" )";
	}
}
