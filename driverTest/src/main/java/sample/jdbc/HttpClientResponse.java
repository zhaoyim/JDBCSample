package sample.jdbc;

public class HttpClientResponse {
    private int status;
    private String content;

    public int getStatus() {
        return status;
    }

    public HttpClientResponse(int status, String content) {
        super();
        this.status = status;
        this.content = content;
    }
    public HttpClientResponse(int status) {
        super();
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpClientResponse [status=" + status + ", content=" + content + "]";
    }

}

