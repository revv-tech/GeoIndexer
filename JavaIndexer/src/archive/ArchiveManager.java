package archive;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.StandardDirectoryReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class ArchiveManager {

    // Directory String
    String directory;
    // File de Directorio
    static File directoryPath;
    // Lista de Subdirectorios
    File listDirectory[];
    // Lista de Files
    File listDirectoryFiles[];
    //
    static ArrayList queue = new ArrayList();
    //
    static IndexWriter writer;
    static StandardAnalyzer analyzer;
    static File stopwords = new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\stopwords.txt");

    public ArchiveManager(String directory) throws IOException {
        this.directory = directory;
        this.directoryPath = new File(directory);
        this.listDirectory = directoryPath.listFiles();
        //Analizador
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_30,stopwords);

    }

    public static void indexHTMLs(String continent) throws IOException {



        //Directorio
        FSDirectory dir = FSDirectory.open(directoryPath);

        //Escritor del Index
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_30, analyzer);
        writer = new IndexWriter(dir,config);

        File directory = new File(directoryPath + "\\" + continent);
        addFiles(directory);

        int numDocs = writer.numDocs();
        for (Object f : queue){
            FileReader fr = null;

            try {
                Document doc = new Document();
                fr = new FileReader((File) f);
                doc.add(new TextField("contents", fr));
                doc.add(new StringField("path", ((File) f).getPath(), Field.Store.YES));
                doc.add(new StringField("filename", ((File) f).getName(), Field.Store.YES));

                writer.addDocument(doc);
                System.out.println("Documento Agregado");
            }catch (Exception e){
                System.out.println("No agregado" + f);
            }finally {

                fr.close();

            }

        }


        int newNumDocs = writer.numDocs();
        System.out.println(newNumDocs - numDocs + " documentos agregados!") ;


    }



    //Agrega el archivo al index
    static public void addFiles(File file){
        if (!file.exists()){
            System.out.println("Non existent");
        }
        if (file.isDirectory()){
            for (File f : file.listFiles())
                addFiles(f);
        }
        else{

            String filename = file.getName().toLowerCase(Locale.ROOT);

            if (filename.endsWith(".htm") || filename.endsWith("html"))
                queue.add(file);

        }
    }

    //
    public void closeIndex() throws  IOException{
        writer.close();
    }

    public void searcherHTML(String continent) throws IOException {

        String indexDir = "C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\IndexConsult";
        IndexReader reader = StandardDirectoryReader.open(FSDirectory.open(new File(indexDir)));
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(5,true);

        String query = "";

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        while (!query.equalsIgnoreCase("a")){

            try {
                System.out.println("Introduzca su consulta (a = salir): ");
                br.readLine();

                if (query.equalsIgnoreCase("a"))
                    break;
                Query q = new QueryParser(Version.LUCENE_30, query, analyzer).parse(query);
                searcher.search(q, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;

                System.out.println("Se encontraron " + "docs!");
                for (int i = 0; i < hits.length; ++i) {
                    int docID = hits[i].doc;
                    Document d = searcher.doc(docID);
                    System.out.println((i + 1) + ". " + d.get("path") + " score = " + hits[i].score);
                }
            }catch (Exception e){
                    System.out.println("Error buscando la consulta!");
                }

        }


    }

}

