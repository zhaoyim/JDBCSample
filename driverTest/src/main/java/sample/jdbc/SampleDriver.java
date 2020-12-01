package sample.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class SampleDriver implements Driver, Closeable {

    private static final String DRIVER_URL_START = "jdbc:sample:";


    public void close() throws IOException {

    }

    public Connection connect(String url, Properties info) throws SQLException {

        if (!acceptsURL(url)) {
            return null;
        }

        SampleDriverUri uri = new SampleDriverUri(url, info);

        return new SampleConnection(uri);
    }

    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith(DRIVER_URL_START);
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 0;
    }

    public boolean jdbcCompliant() {
        return false;
    }


    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
