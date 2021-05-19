package indexer;

import org.apache.lucene.search.ScoreDoc;

import java.util.Comparator;

public class SortByScore implements Comparator<ScoreDoc> {
    public int compare(ScoreDoc a, ScoreDoc b) {
        return Float.compare(b.score, a.score);
    }
}
