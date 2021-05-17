package queries;



import java.util.Arrays;

public class Query implements StringManager {
    public int size;
    private Term[] terminos;

    Query() {
        this.size = -1;
        this.terminos = new Term[0];
    }

    public Term[] getTerminos() {
        return terminos;
    }

    public void setTerminos(String query) {
        String[] sentence;
        if (query.contains("\"")) {
            sentence = query.split("\"");
            sentence = StringManager.removeElements(sentence, "");
            setTermsWithQuotes(sentence);
        } else {
            sentence = query.split(" ");
            sentence = StringManager.removeElements(sentence, "");
            setTermsWithKeys(sentence);
        }
    }

    private void setTermsWithQuotes(String[] sentence) {
        Term term = new Term();
        for (String s : sentence) {
            String[] words = s.split(" ", 0);
            boolean hasTerm = false;
            for (String word : words) {
                if (word.equals("ref:"))
                    term.isRef = true;
                else if (word.contains("~"))
                    term.distanceWithTerm = Integer.parseInt(word.substring(word.length() - 1));
                else if (!hasTerm) {
                    term.keyword = word;
                    term.distanceWithTerm = 1;
                    hasTerm = true;
                    appendTerm(term);
                } else {
                    term.associatedTerm = new Term(word,
                            term.isRef,
                            term.priority,
                            term.pctError,
                            term.typeOfBooleanSearch,
                            term.distanceWithTerm,
                            term);
                    appendTerm(term.associatedTerm);
                }

            }
        }
    }

    private void setTermsWithKeys(String[] sentence) {
        for (String word : sentence) {
            int sep = 0;
            Term term = new Term();
            boolean haveToAppend = true;

            if (word.contains("ref:")) {
                sep = word.indexOf(':');
                term.keyword = word.substring(sep + 1);
                term.isRef = true;
            } else if (word.contains("^")) {
                sep = word.indexOf('^');
                term.keyword = word.substring(0, sep);
                term.priority = Integer.parseInt(word.substring(sep + 1));
            } else if (word.contains("~")) {
                sep = word.indexOf('~');
                term.keyword = word.substring(0, sep);
                term.pctError = Double.parseDouble(word.substring(sep + 1));
            } else if (word.equals("AND") && this.size > -1) {
                this.terminos[size - 1].typeOfBooleanSearch = BooleanSearch.AND;
                haveToAppend = false;
            } else if (word.equals("OR") && this.size > -1) {
                this.terminos[size - 1].typeOfBooleanSearch = BooleanSearch.OR;
                haveToAppend = false;
            } else
                term.keyword = word;

            if (haveToAppend)
                appendTerm(term);
        }
    }

    private void appendTerm(Term term) {
        this.terminos = Arrays.copyOf(this.terminos, ++size);
        this.terminos[size] = term;
    }
}