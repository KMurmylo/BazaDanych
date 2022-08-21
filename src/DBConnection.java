import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    static private Connection instance;
    private Connection(String Adress,String dbname,String login)
    {
        java.sql.Connection connection;
        try {
            connection= DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?user=%s"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    static public Connection GetConnection(){
        if(instance==null)instance=new Connection();
        return instance;
    }
}
