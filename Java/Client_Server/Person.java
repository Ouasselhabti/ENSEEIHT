import java.io.Serializable;

public class Person implements Serializable {
    private String name ;
    private int age ;
    private String job ;

    public Person(String name , int age , String job) {
        this.name = name ;
        this.age = age ;
        this.job = job ;
    }
    public Person() {

    }
    @Override
    public String toString() {
        return "name : "+ this.name + "\n" + "age : "+this.age + "\n" + "Job : "+this.job + "\n";
    }
}
