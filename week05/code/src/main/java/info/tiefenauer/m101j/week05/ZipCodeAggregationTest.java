package info.tiefenauer.m101j.week05;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.gte;
import static java.util.Arrays.asList;

/**
 * Created by Daniel on 17.04.2016.
 */
public class ZipCodeAggregationTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("aggr");
        MongoCollection<Document> collection = db.getCollection("zips");

        List<Document> results = collection.find().into(new ArrayList<Document>());

        printDocuments(results);

        //  aggregation example
        List<Document> pipeline = asList(
                // { $group: { _id: "$state", totalPop: { $sum: "$pop" } } }
                new Document("$group", new Document("_id", "$state")
                        .append("totalPop", new Document("$sum", "$pop"))),
                // { $match: { totalPop: { $gte: 10*1000*1000 } } }
                new Document("$match", new Document("totalPop", new Document("$gte", 10 * 1000 * 1000)))
        );
        results = collection.aggregate(pipeline).into(new ArrayList<Document>());
        printDocuments(results);

        // aggregation becomes even easier with builders!
        List<Bson> pipelineWithBuilders = asList(
                // { $group: { _id: "$state", totalPop: { $sum: "$pop" } } }
                group("$state", Accumulators.sum("totalPop", "$pop")),
                // { $match: { totalPop: { $gte: 10*1000*1000 } } }
                match(gte("totalPop", 10*1000*1000))
        );
        results = collection.aggregate(pipelineWithBuilders).into(new ArrayList<Document>());
        printDocuments(results);

        // Shell syntax can even be parsed! How cool is that?
        List<Document> pipelineWithParsedShellSyntax = asList(
                Document.parse("{ $group: { _id: \"$state\", totalPop: { $sum: \"$pop\" } } }"),
                Document.parse("{ $match: { totalPop: { $gte: 10000000 } } }")
        );
        results = collection.aggregate(pipelineWithParsedShellSyntax).into(new ArrayList<Document>());
        printDocuments(results);
    }

    private static void printDocuments(List<Document> results) {
        for (Document cur : results) {
            System.out.println(cur.toJson());
        }
    }
}
