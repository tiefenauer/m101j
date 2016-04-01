package info.tiefenauer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static info.tiefenauer.Helpers.printJson;

/**
 * Created by Daniel on 29.03.2016.
 */
public class FindWithProjectionTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();

        // insert 10 documents
        for (int i=0;i<10;i++){
            collection.insertOne(new Document()
                    .append("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
                    .append("i", i)
            );
        }

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

        /*
        Bson projection = new Document("x", 1)
                            .append("y", 0)
                            .append("_id", 0);
                            */

        Bson exclude = exclude("x", "_id");
        Bson include = include("y", "i");
        Bson projection = fields(exclude, include);

        List<Document> all = collection.find(filter)
                            .projection(projection)
                            .into(new ArrayList<Document>());
        for (Document cur : all){
            printJson(cur);
        }
    }
}
