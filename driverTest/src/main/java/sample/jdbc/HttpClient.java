package sample.jdbc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);

    public static HttpClientResponse sendGet(String url, HttpClientContext context, Map<String, String> headers)
            throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom().setMaxConnTotal(200).build();
        CloseableHttpResponse response = null;
        try {
            HttpGet request = new HttpGet(url);
            addContentType(request);
            addHeaders(request, headers);
            setTimeout(request);
            if (context == null) {
                response = httpclient.execute(request);
            } else {
                response = httpclient.execute(request, context);
            }
            return new HttpClientResponse(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
        } catch (Exception e) {
            throw new IOException("Exception while send http reqeust: ", e);
        } finally {
            close(httpclient);
        }
    }

    public static HttpClientResponse sendPost(String url, String body, HttpClientContext context,
                                              Map<String, String> headers) throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom().setMaxConnTotal(200).build();
        CloseableHttpResponse response = null;
        try {
            HttpPost request = new HttpPost(url);
            addContentType(request);
            addHeaders(request, headers);
            setTimeout(request);
            StringEntity entity = new StringEntity(body, Charset.forName(HttpConstant.UTF_8));
            entity.setContentType(HttpConstant.APPLICATION_JSON);
            entity.setContentEncoding(HttpConstant.UTF_8);
            request.setEntity(entity);
            if (context == null) {
                response = httpclient.execute(request);
            } else {
                response = httpclient.execute(request, context);
            }
            return new HttpClientResponse(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
        } catch (Exception e) {
            throw new IOException("Exception while send http reqeust: ", e);
        } finally {
            close(httpclient);
        }
    }

    public static HttpClientResponse sendPut(String url, String body, HttpClientContext context,
                                             Map<String, String> headers) throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom().setMaxConnTotal(200).build();
        CloseableHttpResponse response = null;
        try {
            HttpPut request = new HttpPut(url);
            addContentType(request);
            addHeaders(request, headers);
            setTimeout(request);
            StringEntity entity = new StringEntity(body, Charset.forName(HttpConstant.UTF_8));
            entity.setContentType(HttpConstant.APPLICATION_JSON);
            entity.setContentEncoding(HttpConstant.UTF_8);
            request.setEntity(entity);
            if (context == null) {
                response = httpclient.execute(request);
            } else {
                response = httpclient.execute(request, context);
            }
            return new HttpClientResponse(response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
        } catch (Exception e) {
            throw new IOException("Exception while send http reqeust: ", e);
        } finally {
            close(httpclient);
        }
    }

    public static HttpClientResponse sendDelete(String url, HttpClientContext context, Map<String, String> headers)
            throws IOException {
        CloseableHttpClient httpclient = HttpClients.custom().setMaxConnTotal(200).build();
        CloseableHttpResponse response = null;
        try {
            HttpDelete request = new HttpDelete(url);
            addContentType(request);
            addHeaders(request, headers);
            setTimeout(request);
            if (context == null) {
                response = httpclient.execute(request);
            } else {
                response = httpclient.execute(request, context);
            }
            if (response.getEntity() == null) {
                return new HttpClientResponse(response.getStatusLine().getStatusCode());
            } else {
                return new HttpClientResponse(response.getStatusLine().getStatusCode(),
                        EntityUtils.toString(response.getEntity(), Charset.forName("utf-8")));
            }
        } catch (Exception e) {
            throw new IOException("Exception while send http reqeust: ", e);
        } finally {
            close(httpclient);
        }
    }

    private static void setTimeout(HttpRequestBase request) {
        RequestConfig rc = RequestConfig.custom().setConnectTimeout(60000).setConnectionRequestTimeout(10000)
                .setSocketTimeout(60000).build();
        request.setConfig(rc);

    }

    private static void addContentType(HttpRequestBase request) {
        request.addHeader(HttpConstant.CONTENT_TYPE,
                HttpConstant.APPLICATION_JSON + HttpConstant.SEMICOLON + HttpConstant.CHARSET_EQUAL_UTF_8);
        request.addHeader(HttpConstant.ACCEPT, HttpConstant.APPLICATION_JSON);
    }

    private static void addHeaders(HttpRequestBase request, Map<String, String> headers) {
        headers.forEach((k, v) -> {
            request.addHeader(k, v);
        });
    }

    private static void close(CloseableHttpClient closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            logger.error("HTTPClient hit exception: ", e);
        }
    }
}

