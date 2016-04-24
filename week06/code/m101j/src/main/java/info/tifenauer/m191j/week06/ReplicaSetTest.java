package info.tifenauer.m191j.week06;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static java.util.Arrays.asList;

/**
 * Created by Daniel on 23.04.2016.
 */
public class ReplicaSetTest {

    public static void main(String[] args) throws InterruptedException {
        MongoClient client = new MongoClient(asList(new ServerAddress("localhost", 27017),
                                                    new ServerAddress("localhost", 27018),
                                                    new ServerAddress("localhost", 27019)),
                                            MongoClientOptions.builder()
                                                    .requiredReplicaSetName("replSet")
                                                    .build());
        MongoCollection<Document> collection = client.getDatabase("course").getCollection("replication");
        collection.drop();

        for (int i=0; i< Integer.MAX_VALUE; i++){
            collection.insertOne(new Document("_id", 1));
            System.out.println("inserted document: " + i);
            Thread.sleep(500);
        }
    }
}
