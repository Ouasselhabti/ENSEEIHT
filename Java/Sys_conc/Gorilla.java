import java.util.concurrent.Semaphore;

public class Gorilla {
    private Liane liane ;
    private String sens;
    private int index = 0 ; 

    public void s_engager_sur_la_liane() throws InterruptedException {
        Semaphore s ;
        if (sens == "GD") {
            s = Liane.sens_DG;
        } else {
            s = Liane.sens_GD;
        }
        s.acquire();
        i
    }
    
}
