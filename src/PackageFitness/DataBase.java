package PackageFitness;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase{
    private static final String url="jdbc:mysql://localhost:3306/FitnessApp";
    private static final String user="root";
    private static final String pass="Rad12";
    public static Connection connect(){
        try{
            return DriverManager.getConnection(url, user, pass);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
