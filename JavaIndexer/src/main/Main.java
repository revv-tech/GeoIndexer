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
                menu();
                break;
            case 2:
                archiveManager.searchQuery();
                menu();
                break;
            case 3:
                archiveManager.mergeIndexes();
                menu();
                break;
            case 4:
                archiveManager.addTmpToGeneral();
                menu();
                break;
            case 0:
                menu();
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