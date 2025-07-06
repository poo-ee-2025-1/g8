package edu.grupo8.utils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class DBUtils {
    private static final String DATABASE_URL = "jdbc:sqlite:keepfix.db";
    private static ConnectionSource connectionSource;

    public static synchronized ConnectionSource getConnectionSource() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
        }
        return connectionSource;
    }

    public static void close() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
