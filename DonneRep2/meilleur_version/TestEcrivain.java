public class TestEcrivain {

    public static void main(String argv[]) {
		
		if (argv.length != 1) {
			System.out.println("java TestEcrivain <nb d'écritures>");
			return;
		}
		int N = Integer.parseInt(argv[0]);

    	// initialize the system
		Client.init();
		
		// look up the ecrivain_i object in the name server
		// if not found, create it, and register it in the name server
		SharedObject s = (SharedObject) Client.lookup("ecrivain_i");
		if (s == null) {
            Integer monint = 0;
			s = Client.create(monint);
			Client.register("ecrivain_i", s);
		}

        //On lock notre shared object en ecriture, on écrit quelque chose dedans, puis on le unlock
        for(int i =0 ; i< N ; i++) {
            // lock the object in write mode
            s.lock_write();
            Integer ancienneVal = (Integer) s.obj;
			s.obj = ancienneVal + 1;

			//System.out.println("Le nombre d'écriture courant:" + s.obj);
            // unlock the object
            s.unlock();
        }
		System.out.println("Le nombre d'écriture effectué est:" + s.obj);
    }
}