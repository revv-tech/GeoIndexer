package Query;


public class QueryInfo {
    public String query;
    public SearchIn type;
    public BooleanConnection connection;

    public QueryInfo(){
        query = "";
        type = SearchIn.BODY;
        connection = BooleanConnection.NONE;
    }

    public QueryInfo(String query) {
        this.query = query;
        type = SearchIn.BODY;
        connection = BooleanConnection.NONE;
    }

    public QueryInfo(String query, SearchIn type, BooleanConnection connection) {
        this.query = query;
        this.type = type;
        this.connection = connection;
    }
}