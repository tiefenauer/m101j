package info.tifenauer.m191j.week06;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static java.util.Arrays.asList;

/**
 * Created by Daniel on 24.04.2016.
 */
public class ReplicaSetFailoverTest {

    public static void main(String[] args) throws InterruptedException {
        MongoClient client = new MongoClient(asList(new ServerAddress("localhost", 27017),
                                                    new ServerAddress("localhost", 27018),
                                                    new ServerAddress("localhost", 27019)));

        MongoCollection<Document> collection = client.getDatabase("course").getCollection("replication");
        collection.drop();

        for (int i=0; i<Integer.MAX_VALUE; i++){
            try{
                collection.insertOne(new Document("_id", new ObjectId()).append("i", i));
                System.out.println("Inserted document: " + i);
            }
            catch (MongoException e){
                System.out.println("Exception inserting document " + i + ": " + e.getMessage());
            }
            Thread.sleep(500);
        }

    }
}
