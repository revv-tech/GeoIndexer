package main;
import indexer.ArchiveManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static ArchiveManager archiveManager;

    static {
        try {
            archiveManager = new ArchiveManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main() throws IOException { }

    public static void menu() throws IOException {


        Scanner scnInt = new Scanner(System.in);
        System.out.println("*****************************************************************************************");
        System.out.println("*\t1. Indexar Carpeta");
        System.out.println("*\t2. Crear Consulta");
        System.out.println("*\t3. Combinar Indices");
        System.out.println("*\t4. Actualizar Index General");
        System.out.println("*\t0. Salir");
        System.out.println("*****************************************************************************************");
        System.out.println("***\tIngrese una opcion\t***");
        int respuesta = scnInt.nextInt();
        switch (respuesta) {
            case 1:
                Scanner scnNameIdx = new Scanner(System.in);
                System.out.println("***\tIntroduzca el nombre de la carpeta a indexar\t***");
                String name = scnNameIdx.nextLine();
                archiveManager.index_Files(name);
            case 2:
                archiveManager.searchQuery();
            case 3:
                archiveManager.mergeIndexes();
            case 4:
                archiveManager.addTmpToGeneral();
            case 0:
                break;
            default:
                menu();
        }
    }

    public static void main(String[] args) throws IOException {
        archiveManager.creator_();
        menu();
    }


}