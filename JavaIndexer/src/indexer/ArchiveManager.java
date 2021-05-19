package indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import Query.QueryInfo;
import Query.*;


public class ArchiveManager {
    // H:\\Programming\\Java\\Code\\GeoIndexer\\JavaIndexer\\IndexConsult
    // H:\\Programming\\Java\\Code\\GeoIndexer\\JavaIndexer\\stopwords.txt

    // C:\Users\Marco\Desktop\Documentos TEC\GeoIndexer\JavaIndexer\IndexConsult
    // C:\Users\Marco\Desktop\Documentos TEC\GeoIndexer\JavaIndexer\stopwords.txt
    Analyzer analyzer;
    Directory index;
    IndexWriterConfig config;
    IndexWriter writerGeneral;
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
        analyzer = new StandardAnalyzer(Version.LUCENE_36);
        //Lugar en donde se almacena los docs del index
        index = FSDirectory.open(new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\IndexConsult\\General"));

        //Escritor del index
        config = new IndexWriterConfig(Version.LUCENE_36,analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        writerGeneral = new IndexWriter(index,analyzer,true, IndexWriter.MaxFieldLength.UNLIMITED);

    }

    public void index_Files(String continent) throws IOException {


        if (!directoriosAgregados.contains(continent)) {

            File indexDirectoryPath = new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\IndexConsult\\"+continent);
            //Crea analizador NECESARIO PARA LUCENE BUSQUEDAR E INDEXAR con stopwords
            analyzer = new StandardAnalyzer(Version.LUCENE_36);
            //Lugar en donde se almacena los docs del index
            index = FSDirectory.open(indexDirectoryPath);
            //Escritor del index
            config = new IndexWriterConfig(Version.LUCENE_36,analyzer).setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter writerTmp = new IndexWriter(index, config);
            directoriosAgregados.add(continent);
            // Agrega los files al index
            addFiles(new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\Geografia\\" + continent), writerTmp);

            writerTmp.close();
            //Lo agrega a General
            writerGeneral.addIndexes(index);
            writerGeneral.optimize();


        }
        else{
            System.out.println("Ya ha sido indexado el directorio " + continent+ "!");

        }
        System.out.println("-------------------------------------------------------------------------------------");
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
                //System.out.println(f.getName() + " ha sido agregado al index!");
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
            texto = removeStopWordsAndStem(texto);
            //Normalizacion
            texto = Normalizer.normalize(texto,Normalizer.Form.NFKD);
            texto = texto.replaceAll("[^\\p{ASCII}]", "");
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
    private QueryInfo[] addQuery(QueryInfo[] array, QueryInfo newQuery){
        QueryInfo[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = newQuery;
        return newArray;
    }

    private ScoreDoc[] addScoreDoc(ScoreDoc[] array, ScoreDoc scoreDoc) {
        ScoreDoc[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = scoreDoc;
        return newArray;
    }

    public ScoreDoc[] getIntersection(ScoreDoc[] results1, ScoreDoc[] results2) {   // AND
        ScoreDoc[] intersection = {};
        for (ScoreDoc first : results1) {
            boolean isEqual = false;
            float secondScore = 0;
            for (ScoreDoc second : results2) {
                if (first.doc == second.doc) {
                    isEqual = true;
                    secondScore = second.score;
                    break;
                }
            }
            if (isEqual) {
                first.score += secondScore;
                intersection = addScoreDoc(intersection, first);
            }
        }
        return intersection;
    }

    public ScoreDoc[] getUnion(ScoreDoc[] results1, ScoreDoc[] results2) {  // OR - NONE
        ScoreDoc[] union = {};
        for (ScoreDoc first : results1)
            union = addScoreDoc(union, first);
        for (ScoreDoc second : results2) {
            boolean isEqual = false;
            int u;
            for (u = 0; u < union.length; u++) {
                if (second.doc == union[u].doc) {
                    isEqual = true;
                    break;
                }
            }
            if (isEqual)
                union[u].score += second.score;
            else
                union = addScoreDoc(union, second);
        }
        return union;
    }
    public QueryInfo[] queryAnalizer(String query) {
        QueryInfo[] result = {new QueryInfo(query)};
        if (query.contains(":")){
            result = new QueryInfo[]{};
            SearchIn type;
            int idx;

            if (query.contains("\"")) {
                idx = query.indexOf(':');
                if (query.contains("ref:"))
                    type = SearchIn.REFERENCE;
                else
                    type = SearchIn.BODY;
                result = addQuery(result, new QueryInfo(query.substring(idx + 1),
                        type,
                        BooleanConnection.NONE));
            } else {
                String[] terms = query.split(" ", 0);
                for (String term : terms) {
                    if (term.contains("ref:")) {
                        idx = term.indexOf(':');
                        result = addQuery(result, new QueryInfo(term.substring(idx + 1),
                                SearchIn.REFERENCE,
                                BooleanConnection.NONE));
                    } else if (term.contains("texto:")) {
                        idx = term.indexOf(':');
                        result = addQuery(result, new QueryInfo(term.substring(idx + 1),
                                SearchIn.BODY,
                                BooleanConnection.NONE));
                    } else if (term.equals("AND"))
                        result[result.length - 1].connection = BooleanConnection.AND;
                    else if (term.equals("OR"))
                        result[result.length - 1].connection = BooleanConnection.OR;
                    else
                        result = addQuery(result, new QueryInfo(term));
                }
            }
        }
        return result;
    }
    public void searchQuery() throws IOException {
        System.out.print("Introduzca el index que desea buscar: ");
        Scanner x = new Scanner(System.in);
        String dir = x.nextLine();
        File indexDirectoryPath = new File("C:\\Users\\Marco\\Desktop\\Documentos TEC\\GeoIndexer\\JavaIndexer\\IndexConsult\\" + dir);
        System.out.print("Introduzca su consulta: ");
        Scanner s = new Scanner(System.in);
        String querystr = s.nextLine();

        QueryInfo[] queries = queryAnalizer(querystr);
        ScoreDoc[] results = new ScoreDoc[0];
        IndexSearcher searcher = null;
        IndexReader r = null;
        int cont = 0;
        try {
            for (int i = 0; i < queries.length; i++) {
                Query q;

                if (queries[i].type == SearchIn.BODY)
                    q = new MultiFieldQueryParser(Version.LUCENE_36, new String[]{"texto"}, analyzer).parse(queries[i].query);
                else
                    q = new MultiFieldQueryParser(Version.LUCENE_36, new String[]{"a"}, analyzer).parse(queries[i].query);

                System.out.println(q.toString());
                //Create Lucene searcher
                Directory indexDirectory = FSDirectory.open(indexDirectoryPath);
                r = IndexReader.open(indexDirectory);
                searcher = new IndexSearcher(r);
                TopDocs retrievedDocs = searcher.search(q, null, 1000);
                cont += retrievedDocs.totalHits;
                ScoreDoc[] newResults = retrievedDocs.scoreDocs;

                if (i > 0 && queries[i - 1].connection == BooleanConnection.AND)
                    results = getIntersection(results, newResults);
                else
                    results = getUnion(results, newResults);
            }
            System.out.println(results.length);

            System.out.println("Encontro " + Integer.toString(cont) + " resultados");
            System.out.println();
            System.out.println("=================================================");
            System.out.println("Search Results For " + querystr);
            System.out.println("=================================================");

            // Otra funcion
            boolean nextPage = false;
            int k = 0;

            do {
                for (int i = 0; i < 20 && k < results.length; ++i, ++k) {

                    int docID = results[k].doc;
                    Document d = searcher.doc(docID);
                    System.out.println(results[k].score);
                    System.out.println((k + 1) + ") " + d.get("title"));
                    System.out.println("   " + d.get("url"));

                }
                System.out.print("Search again? (y/n) ");
                querystr = s.nextLine();
                if (querystr.trim().equalsIgnoreCase("y") && k < results.length)
                    nextPage = true;
                else if (querystr.trim().equalsIgnoreCase("n") || k >= results.length)
                    nextPage = false;
            } while (nextPage);

            searcher.close();
            r.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        s.close();

    }

        //Stemming ---------------------------------------------------------------------
    public String removeStopWordsAndStem(String input) throws IOException {
        TokenStream tokenStream = new StandardTokenizer(
                Version.LUCENE_36, new StringReader(input));
        tokenStream = new StopFilter(Version.LUCENE_36, tokenStream, StopFilter.makeStopSet(Version.LUCENE_36,stopwords));
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




