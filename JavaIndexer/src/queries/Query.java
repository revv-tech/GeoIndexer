package queries;

public class Query implements StringManager{
    private Term[] terminos;
    public int size;

    Query() {
        this.size = 0;
        this.terminos = new Term[size];
    }

    public void setTerminos(String query) {
        String[] sentence;
        if (query.contains("\"")) {
            sentence = query.split("\"");
            sentence = StringManager.removeElements(sentence, "");

        }
        else {
            sentence = query.split(" ");
            sentence = StringManager.removeElements(sentence, "");

        }
    }

    public Term[] getTerminos() {
        return terminos;
    }

    private void setTermsWithQuotes(String[] sentence) {
        
    }

    private void setTermsWithKeys(String[] sentence) {

    }
}