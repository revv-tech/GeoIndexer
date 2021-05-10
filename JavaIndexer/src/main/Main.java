package main;
import archive.ArchiveManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hola mundo");

        ArchiveManager index = new ArchiveManager();
        index.index_HTMLS("Asia");
        index.searchQuery();
    }

}