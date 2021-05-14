package queries;

public class Term {
    public String term;     // Termino

    public boolean isRef;  // Es una referencia
    public int priority;   // Numero de relevancia en busqueda vectorial (-1 si no existe)
    public double pctError;    // Numero de aceptacion de error (0.0 si no acepta errores)
    public int distanceWithTerm;   // Distancia MAX que tiene con el segundo termino (0 si no existe, 1 o mas si existe)

    public Term associatedTerm;     // Puntero al termino (Si distanceWithTerm es 0, este campo es nulo)

    public Term() {
        this.term = "";
        this.isRef = false;
        this.priority = -1;
        this.pctError = 0;
        this.distanceWithTerm = 0;
        this.associatedTerm = null;
    }

    public Term(String term) {
        this.term = term;
        this.isRef = false;
        this.priority = -1;
        this.pctError = 0;
        this.distanceWithTerm = 0;
        this.associatedTerm = null;
    }

    public Term(String term, boolean isRef, int priority, double pctError, int distanceWithTerm, Term associatedTerm) {
        this.term = term;
        this.isRef = isRef;
        this.priority = priority;
        this.pctError = pctError;
        this.distanceWithTerm = distanceWithTerm;
        this.associatedTerm = associatedTerm;
    }
}
