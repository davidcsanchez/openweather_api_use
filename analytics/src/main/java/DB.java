import java.sql.Connection;
import java.sql.SQLException;

public interface DB {

    Connection openConnection() throws SQLException;
    void closeConnection(Connection connection) throws SQLException;
}
