package sample.jdbc;

import com.google.common.net.HostAndPort;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.requireNonNull;

public class SampleDriverUri {


    private static final String JDBC_URL_START = "jdbc:";
    private final HostAndPort address;
    private final URI uri;

    private final boolean useSecureConnection = false;

//    private final Properties properties;


    public SampleDriverUri(String url, Properties driverProperties)
            throws SQLException {
        this(parseDriverUrl(url), driverProperties);
    }

    private SampleDriverUri(URI uri, Properties driverProperties)
            throws SQLException {
        this.uri = requireNonNull(uri, "uri is null");
        address = HostAndPort.fromParts(uri.getHost(), uri.getPort());
//        properties = mergeConnectionProperties(uri, driverProperties);
//
//        validateConnectionProperties(properties);
//
//        // enable SSL by default for standard port
//        useSecureConnection = SSL.getValue(properties).orElse(uri.getPort() == 443);
//
//        initCatalogAndSchema();
    }


    private static URI parseDriverUrl(String url)
            throws SQLException {
        URI uri;
        try {
            uri = new URI(url.substring(JDBC_URL_START.length()));
        } catch (URISyntaxException e) {
            throw new SQLException("Invalid JDBC URL: " + url, e);
        }
        if (isNullOrEmpty(uri.getHost())) {
            throw new SQLException("No host specified: " + url);
        }
        if (uri.getPort() == -1) {
            throw new SQLException("No port number specified: " + url);
        }
        if ((uri.getPort() < 1) || (uri.getPort() > 65535)) {
            throw new SQLException("Invalid port number: " + url);
        }
        return uri;
    }

    public URI getJdbcUri() {
        return uri;
    }

    public URI getHttpUri() {
        return buildHttpUri();
    }

    private URI buildHttpUri() {
        String scheme = useSecureConnection ? "https" : "http";
        try {
            return new URI(scheme, null, address.getHost(), address.getPort(), null, null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
