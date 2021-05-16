package queries;

public class Term {
    public String keyword;     // Termino

    public boolean isRef;  // Es una referencia
    public int priority;   // Numero de relevancia en busqueda vectorial (-1 si no existe)
    public double pctError;    // Numero de aceptacion de error (0.0 si no acepta errores)
    public BooleanSearch typeOfBooleanSearch;   // Marca el tipo de relacion booleana con el siguiente termino
    public int distanceWithTerm;   // Distancia MAX que tiene con el segundo termino (0 si no existe, 1 o mas si existe)

    public Term associatedTerm;     // Puntero al termino (Para las busquedas por distancia o booleanas)

    public Term() {
        this.keyword = "";
        this.isRef = false;
        this.priority = -1;
        this.pctError = 0;
        this.typeOfBooleanSearch = BooleanSearch.NONE;
        this.distanceWithTerm = 0;
        this.associatedTerm = null;
    }

    public Term(String keyword) {
        this.keyword = keyword;
        this.isRef = false;
        this.priority = -1;
        this.pctError = 0;
        this.typeOfBooleanSearch = BooleanSearch.NONE;
        this.distanceWithTerm = 0;
        this.associatedTerm = null;
    }

    public Term(String keyword, boolean isRef, int priority, double pctError, BooleanSearch typeOfBooleanSearch, int distanceWithTerm, Term associatedTerm) {
        this.keyword = keyword;
        this.isRef = isRef;
        this.priority = priority;
        this.pctError = pctError;
        this.typeOfBooleanSearch = typeOfBooleanSearch;
        this.distanceWithTerm = distanceWithTerm;
        this.associatedTerm = associatedTerm;
    }
}
