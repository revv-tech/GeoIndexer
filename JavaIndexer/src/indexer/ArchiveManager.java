package indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;


import java.io.*;
import java.text.Normalizer;
import java.util.*;


public class ArchiveManager {
    // H:\\Programming\\Java\\Code\\GeoIndexer\\JavaIndexer\\IndexConsult
    // H:\\Programming\\Java\\Code\\GeoIndexer\\JavaIndexer\\stopwords.txt

    // C:\Users\Marco\Desktop\Documentos TEC\GeoIndexer\JavaIndexer\IndexConsult
    // C:\Users\Marco\Desktop\Documentos TEC\GeoIndexer\JavaIndexer\stopwords.txt
    Analyzer analyzer;
    Directory index;
    File indexDirectoryPath = new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\IndexConsult");
    IndexWriterConfig config;
    IndexWriter writer;
    String stopwords[] = {
            "a",
            "acá",
            "ahí",
            "ajena",
            "ajenas",
            "ajeno",
            "ajenos",
            "al",
            "algo",
            "algún",
            "alguna",
            "algunas",
            "alguno",
            "algunos",
            "allá",
            "alli",
            "allí",
            "ambos",
            "ampleamos",
            "ante",
            "antes",
            "aquel",
            "aquella",
            "aquellas",
            "aquello",
            "aquellos",
            "aqui",
            "aquí",
            "arriba",
            "asi",
            "atras",
            "aun",
            "aunque",
            "bajo",
            "bastante",
            "bien",
            "cabe",
            "cada",
            "casi",
            "cierta",
            "ciertas",
            "cierto",
            "ciertos",
            "como",
            "cómo",
            "con",
            "conmigo",
            "conseguimos",
            "conseguir",
            "consigo",
            "consigue",
            "consiguen",
            "consigues",
            "contigo",
            "contra",
            "cual",
            "cuales",
            "cualquier",
            "cualquiera",
            "cualquieras",
            "cuan",
            "cuán",
            "cuando",
            "cuanta",
            "cuánta",
            "cuantas",
            "cuántas",
            "cuanto",
            "cuánto",
            "cuantos",
            "cuántos",
            "de",
            "dejar",
            "del",
            "demás",
            "demas",
            "demasiada",
            "demasiadas",
            "demasiado",
            "demasiados",
            "dentro",
            "desde",
            "donde",
            "dos",
            "el",
            "él",
            "ella",
            "ellas",
            "ello",
            "ellos",
            "empleais",
            "emplean",
            "emplear",
            "empleas",
            "empleo",
            "en",
            "encima",
            "entonces",
            "entre",
            "era",
            "eramos",
            "eran",
            "eras",
            "eres",
            "es",
            "esa",
            "esas",
            "ese",
            "eso",
            "esos",
            "esta",
            "estaba",
            "estado",
            "estais",
            "estamos",
            "estan",
            "estar",
            "estas",
            "este",
            "esto",
            "estos",
            "estoy",
            "etc",
            "fin",
            "fue",
            "fueron",
            "fui",
            "fuimos",
            "gueno",
            "ha",
            "hace",
            "haceis",
            "hacemos",
            "hacen",
            "hacer",
            "haces",
            "hacia",
            "hago",
            "hasta",
            "incluso",
            "intenta",
            "intentais",
            "intentamos",
            "intentan",
            "intentar",
            "intentas",
            "intento",
            "ir",
            "jamás",
            "junto",
            "juntos",
            "la",
            "largo",
            "las",
            "lo",
            "los",
            "mas",
            "más",
            "me",
            "menos",
            "mi",
            "mía",
            "mia",
            "mias",
            "mientras",
            "mio",
            "mío",
            "mios",
            "mis",
            "misma",
            "mismas",
            "mismo",
            "mismos",
            "modo",
            "mucha",
            "muchas",
            "muchísima",
            "muchísimas",
            "muchísimo",
            "muchísimos",
            "mucho",
            "muchos",
            "muy",
            "nada",
            "ni",
            "ningun",
            "ninguna",
            "ningunas",
            "ninguno",
            "ningunos",
            "no",
            "nos",
            "nosotras",
            "nosotros",
            "nuestra",
            "nuestras",
            "nuestro",
            "nuestros",
            "nunca",
            "os",
            "otra",
            "otras",
            "otro",
            "otros",
            "para",
            "parecer",
            "pero",
            "poca",
            "pocas",
            "poco",
            "pocos",
            "podeis",
            "podemos",
            "poder",
            "podria",
            "podriais",
            "podriamos",
            "podrian",
            "podrias",
            "por",
            "por qué",
            "porque",
            "primero",
            "puede",
            "pueden",
            "puedo",
            "pues",
            "que",
            "qué",
            "querer",
            "quien",
            "quién",
            "quienes",
            "quienesquiera",
            "quienquiera",
            "quiza",
            "quizas",
            "sabe",
            "sabeis",
            "sabemos",
            "saben",
            "saber",
            "sabes",
            "se",
            "segun",
            "ser",
            "si",
            "sí",
            "siempre",
            "siendo",
            "sin",
            "sín",
            "sino",
            "so",
            "sobre",
            "sois",
            "solamente",
            "solo",
            "somos",
            "soy",
            "sr",
            "sra",
            "sres",
            "sta",
            "su",
            "sus",
            "suya",
            "suyas",
            "suyo",
            "suyos",
            "tal",
            "tales",
            "también",
            "tambien",
            "tampoco",
            "tan",
            "tanta",
            "tantas",
            "tanto",
            "tantos",
            "te",
            "teneis",
            "tenemos",
            "tener",
            "tengo",
            "ti",
            "tiempo",
            "tiene",
            "tienen",
            "toda",
            "todas",
            "todo",
            "todos",
            "tomar",
            "trabaja",
            "trabajais",
            "trabajamos",
            "trabajan",
            "trabajar",
            "trabajas",
            "trabajo",
            "tras",
            "tú",
            "tu",
            "tus",
            "tuya",
            "tuyo",
            "tuyos",
            "ultimo",
            "un",
            "una",
            "unas",
            "uno",
            "unos",
            "usa",
            "usais",
            "usamos",
            "usan",
            "usar",
            "usas",
            "uso",
            "usted",
            "ustedes",
            "va",
            "vais",
            "valor",
            "vamos",
            "van",
            "varias",
            "varios",
            "vaya",
            "verdad",
            "verdadera",
            "vosotras",
            "vosotros",
            "voy",
            "vuestra",
            "vuestras",
            "vuestro",
            "vuestros",
            "y",
            "ya",
            "yo"
    };
    ArrayList<String> directoriosAgregados = new ArrayList<String>();

    public ArchiveManager() throws IOException{}

    //Indexa HTMLs de acuerdo al continente dado
    public void creator_() throws IOException {
        //Crea analizador NECESARIO PARA LUCENE BUSQUEDAR E INDEXAR con stopwords

        analyzer = new MyAnalyzer();
        //Lugar en donde se almacena los docs del index
        index = FSDirectory.open(indexDirectoryPath);
        //Escritor del index
        config = new IndexWriterConfig(Version.LUCENE_36,analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writer = new IndexWriter(index, analyzer,true, new IndexWriter.MaxFieldLength(25000));

    }


    public void index_Files(String continent) throws IOException {

        if (!directoriosAgregados.contains(continent)) {
            directoriosAgregados.add(continent);
            // Agrega los files al index
            addFiles(new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\Geografia\\" + continent),writer);

        }
        else{
            IndexWriter newIndex = new IndexWriter(index, writer.getConfig());
            addFiles(new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\Geografia\\" + continent),newIndex);

        }
        for (int i = 0; i < directoriosAgregados.size(); ++i)
            System.out.println(directoriosAgregados.get(i) + " ha sido indexado!");

    }


    //Agrega el archivo al index
    public void addFiles(File file, IndexWriter writerP) throws IOException {

        //Verifica si es directorio

        if (file.isDirectory()) {

            //Lo indexa
            System.out.println("Indexando " + file.getName() + "...........");
            File[] fileList = file.listFiles();

            for (File f : fileList) {
                addFiles(f,writerP);
                System.out.println(f.getName() + " ha sido agregado al index!");
            }

        }
        //Si es html lo indexa
        else if (!file.isHidden() && file.exists() && file.canRead()) {

            //make doc
            Document doc = getDocument(file);
            try {
                //add doc to writer
                writerP.addDocument(doc);

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

            //Titulo
            String title = soupDoc.getElementsByTag("title").text().toLowerCase();
            //Resumen
            String summary = soupDoc.getElementsByTag("p").text().substring(0,200);
            //Texto
            String texto = soupDoc.getElementsByTag("p").text().toLowerCase();
            //Normalizacion
            texto = Normalizer.normalize(texto,Normalizer.Form.NFKD);
            texto = texto.replaceAll("[^\\p{ASCII}]", "");
            texto = removeStopWordsAndStem(texto);
            //System.out.println(texto);
            //Ref
            String a = soupDoc.getElementsByTag("a").text().toLowerCase().toLowerCase();
            //Control de vocales en Ref
            a = a.replaceAll("[\b?á\b?]", "");
            a = a.replaceAll("[\b?í\b?]", "");
            a = a.replaceAll("[\b?ó\b?]", "");
            a = a.replaceAll("[\b?ú\b?]", "");
            a = a.replaceAll("[\b?é\b?]", "");
            //System.out.println(a);
            //AGREGAR ETIQUETAS
            addDocument(doc, "title", title, 2);
            addDocument(doc, "resumen", summary, 1);
            addDocument(doc, "url", file.getAbsolutePath(), 1);
            addDocument(doc, "ref", a, 1.5f);
            addDocument(doc,"texto",texto,1);

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
            Query q = new MultiFieldQueryParser(Version.LUCENE_36,new String[] {"texto"}, analyzer).parse(querystr);
			System.out.println(q.toString());
            //Create Lucene searcher
            Directory indexDirectory = FSDirectory.open(indexDirectoryPath);
            IndexReader r = IndexReader.open(indexDirectory);
            IndexSearcher searcher = new IndexSearcher(r);

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

    }

    public void closeWriter() throws IOException {
        writer.close();
    }

    //Stemming
    public String removeStopWordsAndStem(String input) throws IOException {
        TokenStream tokenStream = new StandardTokenizer(
                Version.LUCENE_36, new StringReader(input));
        tokenStream = new StopFilter(true, tokenStream, Collections.singleton(stopwords));
        tokenStream = new PorterStemFilter(tokenStream);
        StringBuilder sb = new StringBuilder();
        TermAttribute termAttr = tokenStream.getAttribute(TermAttribute.class);
        while (tokenStream.incrementToken()) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(termAttr.term());
        }
        return sb.toString();
    }
}




