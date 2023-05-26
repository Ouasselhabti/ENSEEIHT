public interface SharedObject_itf {
	public int getId();
	public Object getObj();
	public void setObj(Object obj);
	public void lock_read();
	public void lock_write();
	public void unlock();
	public void getLocalVersion();
}