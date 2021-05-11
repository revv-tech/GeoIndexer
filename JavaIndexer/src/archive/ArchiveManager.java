package archive;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;



import java.io.*;
import java.util.Scanner;



public class ArchiveManager {

    StandardAnalyzer analyzer;
    Directory index;
    File indexDirectoryPath = new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\IndexConsult");
    IndexWriterConfig config;
    IndexWriter writer;
    int count = 0;
    static File stopwords = new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\stopwords.txt");

    public void ArchiveManager() throws IOException{
    }
    //Indexa HTMLs de acuerdo al continente dado
    public void index_HTMLS(String continent) throws IOException {
        //Crea analizador NECESARIO PARA LUCENE BUSQUEDAR E INDEXAR
        analyzer = new StandardAnalyzer(Version.LUCENE_36);
        //Lugar en donde se almacena los docs del index
        index = FSDirectory.open(indexDirectoryPath);
        //Escritor del index
        config = new IndexWriterConfig(Version.LUCENE_36,analyzer);
        writer = new IndexWriter(index, analyzer,true, new IndexWriter.MaxFieldLength(25000));
        //Agrega los files al index
        addFiles(new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\Geografia\\" + continent));
        System.out.println("RAM kilobytes used: " + this.writer.ramSizeInBytes()/1000);
        writer.close();
    }



    //Agrega el archivo al index
    public void addFiles(File file) throws IOException {
        //Verifica si es directorio
        if (file.isDirectory()) {
            //Lo indexa
            System.out.println("Indexando " + file.getName() + "...........");
            File[] fileList = file.listFiles();
            for (File f : fileList) {
                addFiles(f);
                System.out.println(f.getName() + " ha sido agregado al index!");
            }
        }
        //Si es html lo indexa
        else if (!file.isHidden() && file.exists() && file.canRead()) {
            //make doc
            Document doc = getDocument(file);
            try {
                //add doc to writer
                writer.addDocument(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Parser
    private Document getDocument(File file) {
        Document doc = new Document();

        try {
            //Parse file and add body and title to doc
            org.jsoup.nodes.Document soupDoc = Jsoup.parse(file, "UTF-8");
            //ETIQUETAS VERIFICAR CUALES
            String body = soupDoc.getElementsByTag("body").text();
            String title = soupDoc.getElementsByTag("title").text();
            String a = soupDoc.getElementsByTag("a").text();
            System.out.println(title);
            //AGREGAR ETIQUETAS
            addDocument(doc, "body", body, 1);
            addDocument(doc, "title", title, 2);
            addDocument(doc, "url", file.getAbsolutePath(), 1);
            addDocument(doc, "a", a, 1.5f);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            return doc;
        }
        return doc;
    }
    //Agrega cada campo del doc. El documento tiene fields (secciones) ahi se guarda cada etiqueta
    private void addDocument(Document doc, String fieldTitle, String field, float boost) {
        Field temp  = new Field(fieldTitle,field,Field.Store.YES,Field.Index.ANALYZED);
        temp.setBoost(boost);
        doc.add(temp);
    }

    //Consulta simple. Hay que buscar como hacer otras consultas
    public void searchQuery(){

        System.out.print("Introduzca su consulta: ");
        Scanner s = new Scanner(System.in);
        String querystr = s.nextLine();
        try {
            //Create Query
            Query q = new MultiFieldQueryParser(Version.LUCENE_36,new String[] {"title", "body", "a"}, analyzer).parse(querystr);
			System.out.println(q.toString());
            //Create Lucene searcher
            Directory indexDirectory = FSDirectory.open(indexDirectoryPath);
            IndexReader r = IndexReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(r);

            //Get top 5 docs from search results

            TopDocs retrievedDocs = searcher.search(q,null,20);

            ScoreDoc[] results = retrievedDocs.scoreDocs;
            System.out.println(results.length);
            System.out.println();
            System.out.println("=================================================");
            System.out.println("Search Results For " + querystr);
            System.out.println("=================================================");
            //Get top 5 docs from results

            for(int i=0; i<results.length; ++i) {

                int docID = results[i].doc;
                Document d = searcher.doc(docID);
                System.out.println((i + 1) + ") " + d.get("title"));
                System.out.println("   " + d.get("url"));

            }
            searcher.close();
            r.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //Check if user wants to search again
        System.out.print("Search again? (y/n) ");
        querystr = s.nextLine();
        if (querystr.trim().toLowerCase().equals("y")) {
            searchQuery();
        }
        s.close();
    }
}




