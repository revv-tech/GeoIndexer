package archive;

import org.jsoup.Jsoup;


import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class ArchiveManager {

    //Directory String
    String directory;
    //File de Directorio
    File directoryPath;
    //Lista de Subdirectorios
    File listDirectory[];
    //Lista de Files
    File listDirectoryFiles[];


    public ArchiveManager(String directory) {
        this.directory = directory;
        this.directoryPath = new File(directory);
        this.listDirectory = directoryPath.listFiles();

    }

    public void prueba() throws IOException {

        System.out.println("List of files and directories in the specified directory:");
        for(File file : listDirectory) {

            System.out.println("File name: "+file.getName());
            System.out.println("File path: "+file.getAbsolutePath());
            System.out.println("List of Files: ");
            getFilesAux(file);
            System.out.println(" ");

        }

    }

    private void getFiles(File subDir) throws IOException {
        File[] filesList = subDir.listFiles();
        for (File f: filesList) {
            if (f.isDirectory()) {
                System.out.println("prueba");
                getFilesAux(f);
            }
            if (f.isFile()) {
                System.out.println(f.getName());
            }
        }
    }

    private void getFilesAux(File f) throws IOException {
        File[] filesList = f.listFiles();
        for (File html: filesList){
            Document newDoc = Jsoup.parse(html,"utf-8");
            Element divTag = newDoc.getElementById("mydiv");
            System.out.println(divTag.text());
        }
    }
}