import java.util.concurrent.Semaphore;

public class Liane {
    public  static Semaphore sens_GD = new Semaphore(1);
    public  static Semaphore sens_DG = new Semaphore(1);
    public static int nb_travers;
    public static final int nb_total = 5;

}
