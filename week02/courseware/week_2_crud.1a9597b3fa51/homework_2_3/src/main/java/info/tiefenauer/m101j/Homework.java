package info.tiefenauer.m101j;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;
import static info.tiefenauer.m101j.Helpers.printJson;

/**
 * Created by Daniel on 01.04.2016.
 */
public class Homework {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> collection = db.getCollection("grades");

        List<Document> all = collection.find(eq("type", "homework"))
                .sort(orderBy(ascending("student_id"), ascending("score")))
                .into(new ArrayList<Document>());

        System.out.println("found " + all.size() + " documents");

        Integer student_id = null;
        int count = 0;
        for (Document doc : all) {
            if (!doc.getInteger("student_id").equals(student_id)){
                student_id = doc.getInteger("student_id");
                printJson(doc);
                collection.deleteOne(doc);
                count++;
            }
        }
        System.out.println("deleted " + count + " documents");
    }
}
