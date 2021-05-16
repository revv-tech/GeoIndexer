package main;
import indexer.ArchiveManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        String word = "cuanacaste~0.8";
        int sep = word.indexOf('~');

        System.out.println(word.substring(sep + 1));

        /*ArchiveManager index = new ArchiveManager();
        index.index_HTMLS("Asia");
        index.searchQuery();*/
    }


}