public interface SharedObject_itf {
	public Object getObj();
	public void setObj(Object obj);
	public void setLock(LockState lock);
	public LockState getLock();
	public void lock_read();
	public void lock_write();
	public void unlock();
}