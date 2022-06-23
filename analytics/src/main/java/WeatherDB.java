import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;

public class WeatherDB implements DB {

    private String directoryDB;
    private String filePath;

    public WeatherDB(String directoryBD){
        this.directoryDB= "jdbc:sqlite:" + directoryBD + "\\Weather.db";
        this.filePath = directoryBD;
    }


    public void storageData(Weather weather) throws Exception {
        createNewTable();
        Connection connection = openConnection();
        putRow(weather, connection);
        closeConnection(connection);
    }

    private void createFilePath() {
        try {
            Path path = Paths.get(this.filePath);
            Files.createDirectories(path);
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }

    private void putRow(Weather weather, Connection connection) throws SQLException {
        String sql = "INSERT INTO weather(ts,lat,lon,temperature,pressure,humidity) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setLong(1, weather.getTs().toEpochMilli());
        pstmt.setDouble(2, weather.getLocation().getLat());
        pstmt.setDouble(3, weather.getLocation().getLon());
        pstmt.setDouble(4, weather.getTemp());
        pstmt.setDouble(5, weather.getPressure());
        pstmt.setDouble(6, weather.getHumidity());
        pstmt.executeUpdate();
    }

    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public Connection openConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(this.directoryDB);
        if (connection != null)
        {
            connection.getMetaData();
        }
        return connection;
    }

    public void createNewTable() {
        createFilePath();
        String sql = "CREATE TABLE IF NOT EXISTS weather (\n"
                + "	ts Long, \n"
                + "	lat Double, \n"
                + "	lon Double, \n"
                + "	temperature Double, \n"
                + "	pressure Double, \n"
                + "	humidity Double \n"
                + ");";
        try (Connection connection = DriverManager.getConnection(this.directoryDB);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Weather> selectAll() {
        String sql = "SELECT ts, lat, lon, temperature, pressure, humidity FROM Weather";
        ArrayList<Weather> listOfRows = new ArrayList<>();
        try (Connection conn =DriverManager.getConnection(this.directoryDB) ;
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Weather row = new Weather.Builder()
                        .ts(Instant.ofEpochMilli( rs.getLong("ts")))
                        .location(new Location(rs.getDouble("lat"),rs.getDouble("lon")))
                        .temp(rs.getDouble("temperature"))
                        .pressure(rs.getDouble("pressure"))
                        .humidity(rs.getDouble("humidity"))
                        .build();
                listOfRows.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfRows;
    }
}
