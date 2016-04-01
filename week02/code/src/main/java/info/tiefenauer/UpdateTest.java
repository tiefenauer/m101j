package info.tiefenauer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import static info.tiefenauer.Helpers.printJson;

/**
 * Created by Daniel on 01.04.2016.
 */
public class UpdateTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("test");

        collection.drop();

        // insert 8 documents with both _id and x set to the value of the loop variable
        // and y set to true
        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document()
                    .append("_id", i)
                    .append("x", i)
                    .append("y", true));
        }

        collection.replaceOne(eq("x", 5), new Document("x", 20).append("updated", true));
        collection.updateOne(eq("x", 5), new Document("$set", new Document("x", 20)
                                                                    .append("updated", true)));
        collection.updateOne(eq("x", 5), combine(set("x", 20), set("updated", true)));
        collection.updateOne(eq("_id", 9), combine(set("x", 20), set("updated", true)), new UpdateOptions().upsert(true));
        collection.updateMany(gte("x", 5), inc("x", 1));

        for (Document cur : collection.find().into(new ArrayList<Document>())) {
            printJson(cur);
        }
    }

}
