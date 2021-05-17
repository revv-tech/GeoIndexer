package main;
import indexer.ArchiveManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
<<<<<<< Updated upstream
=======
        /*
        String query = "ref:\"fiebre oro\"~2 AND ref:\"rio nilo\"~2";

        String[] terms = query.split("\"", 0);

        for (String term : terms) {
            System.out.println(term);
        }

>>>>>>> Stashed changes
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        String word = "cuanacaste~0.8";
        int sep = word.indexOf('~');

<<<<<<< Updated upstream
        System.out.println(word.substring(sep + 1));

        /*ArchiveManager index = new ArchiveManager();
        index.index_HTMLS("Asia");
        index.searchQuery();*/
=======
        for (String term : terms) {
            System.out.println(term);
        }
*/
        ArchiveManager index = new ArchiveManager();
        index.creator_();
        index.index_Files("África");
        index.searchQuery();
        index.index_Files("Asia");
        index.searchQuery();
        index.index_Files("América");
        index.index_Files("Europa");
>>>>>>> Stashed changes
    }


}