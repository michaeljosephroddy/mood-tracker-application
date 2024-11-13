package contract;

import java.sql.ResultSet;

public interface DBConnector {
    void connect();

    void disconnect();

    void createStatement();

    ResultSet executeSQLRead(String sql);

    int executeSQLUpdate(String sql);
}