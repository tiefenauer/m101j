package info.tiefenauer;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import static java.util.Arrays.asList;

/**
 * Created by Daniel on 29.03.2016.
 */
public class App {

    public static void main(String[] args) {
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(50).build();

        // localhost:27017
        MongoClient client = new MongoClient();
        MongoClient localClient = new MongoClient("localhost", 27017);
        MongoClient serverClient = new MongoClient(new ServerAddress("localhost", 27017));
        MongoClient clusterClient = new MongoClient(asList(new ServerAddress("localhost", 27017)));
        MongoClient uriClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoClient optionsClient = new MongoClient(new ServerAddress(), options);

        MongoDatabase db = client.getDatabase("test").withReadPreference(ReadPreference.secondary());
        MongoCollection<Document> coll = db.getCollection("test");
        MongoCollection<BsonDocument> bsonColl = db.getCollection("test", BsonDocument.class);
    }
}
