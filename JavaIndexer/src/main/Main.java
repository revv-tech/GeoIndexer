package main;
import archive.ArchiveManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hola mundo");

        ArchiveManager test = new ArchiveManager("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\Geografia\\África\\Dependencias\\Mayotte.htm");
        test.prueba();
        String fileName = "C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\Geografia\\África\\Dependencias\\Mayotte.htm";

    }
}