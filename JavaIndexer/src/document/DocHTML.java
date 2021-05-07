package document;

public class DocHTML {
    private String data;
    private String url;

    public DocHTML() {
        this.data = "";
        this.url = "";
    }

    public DocHTML(String data, String url) {
        this.data = data;
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
