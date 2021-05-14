package main;
import indexer.ArchiveManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String query = "ref:\"fiebre oro\"~2 AND ref:\"rio nilo\"~2";

        String[] terms = query.split("\"", 0);

        for (String term : terms) {
            System.out.println(term);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        query = "ref:fiebre AND ref:oro";

        terms = query.split(" ", 0);

        for (String term : terms) {
            System.out.println(term);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        query = "fiebre^1 amarilla^4";

        terms = query.split(" ", 0);

        for (String term : terms) {
            System.out.println(term);
        }

        /*ArchiveManager index = new ArchiveManager();
        index.index_HTMLS("Asia");
        index.searchQuery();*/
    }


}