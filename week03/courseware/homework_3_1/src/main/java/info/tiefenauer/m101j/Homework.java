package info.tiefenauer.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 01.04.2016.
 */
public class Homework {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("school");
        MongoCollection<Document> collection = db.getCollection("students");

        List<Document> all = collection.find().into(new ArrayList<Document>());
        for (Document student : all){
            List<Document> scores = (List<Document>) student.get("scores");

            double min = Double.MAX_VALUE;
            Document minScore = null;
            System.out.println(student);
            //System.out.println(scores);
            for (Document score : scores){
                double scoreValue = score.getDouble("score");
                if(score.getString("type").equals("homework") && scoreValue < min ){
                    min = scoreValue;
                    minScore = score;
                }
            }
            //System.out.println(minScore);
            scores.remove(minScore);
            //System.out.println(scores);
            //student.remove("_id");
            student.append("scores", scores);
            System.out.println(student);
            collection.findOneAndReplace(new Document("_id", student.get("_id")), student);
        }
    }
}
