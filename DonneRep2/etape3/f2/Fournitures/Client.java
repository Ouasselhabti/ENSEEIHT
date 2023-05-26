import java.rmi.RemoteException;

public class Client implements Client_itf {

    public static Moniteur monitor;

    

    public static void init(String s) {

    }
    public static SharedObject lookup(Object obj) {
        return null;
    }
    public static void write(int id,Object o) {

    }
    public static SharedObject publish(String s1, String s2, boolean bool) {

        return null;
    }

    @Override
    public void initSO(int idObj, Object valeur) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initSO'");
    }

    @Override
    public void reportValue(int idObj, ReadCallback rcb) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reportValue'");
    }

    @Override
    public void update(int idObj, int version, Object valeur, WriteCallback wcb) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String getSite() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSite'");
    }

    @Override
    public Object getObj(String name) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getObj'");
    }

    @Override
    public int getVersion(String name) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVersion'");
    }

    public static String getIdSite() {
        return null;
    }
}
