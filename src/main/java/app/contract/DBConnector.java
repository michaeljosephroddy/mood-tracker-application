package app.contract;

import java.sql.ResultSet;

public interface DBConnector {
    void connect();

    void disconnect();

    ResultSet executeSQLRead(String sql);

    int executeSQLUpdate(String sql);
}