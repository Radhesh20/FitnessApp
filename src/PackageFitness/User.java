package PackageFitness;

//user class containing constructor to get and initialise the user details
public class User{
    public String username;
    public String password;
    public String name;
    public String goal;
    public int age;
    public String gender;
    public double height;
    public double weight;
    public User(String username,String password, String name, int age, String gender,double height,double weight,String goal){
        this.username=username;
        this.password=password;
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.height=height;
        this.weight=weight;
        this.goal=goal;
    }
}