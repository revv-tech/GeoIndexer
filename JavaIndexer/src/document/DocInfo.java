package document;

public class DocInfo extends DocHTML{
    String title;
    String[] bodies;
    String[] references;

    public DocInfo() {
        this.title = "";
        this.bodies = new String[0];
        this.references = new String[0];
    }

    public DocInfo(String title, String[] bodies, String[] references) {
        this.title = title;
        this.bodies = bodies;
        this.references = references;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getBodies() {
        return bodies;
    }

    public void setBodies(String[] bodies) {
        this.bodies = bodies;
    }

    public String[] getReferences() {
        return references;
    }

    public void setReferences(String[] references) {
        this.references = references;
    }

    public String[] addString(String[] array, String data) {
        String[] newArray = new String[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = data;

        return newArray;
    }

    public void addBody(String body) {
        String[] newBodies =  addString(this.bodies, body);
        this.bodies = newBodies;
    }

    public void addReference(String reference) {
        String[] newReferences = addString(this.references, reference);
        this.references = newReferences;
    }
}
