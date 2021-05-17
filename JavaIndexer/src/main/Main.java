package main;
import indexer.ArchiveManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ArchiveManager index = new ArchiveManager();
        index.creator_();
        index.index_Files("África");
        index.searchQuery();


    }


}