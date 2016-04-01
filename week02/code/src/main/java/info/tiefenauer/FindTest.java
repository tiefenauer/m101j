package info.tiefenauer;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

import static info.tiefenauer.Helpers.printJson;

/**
 * Created by Daniel on 29.03.2016.
 */
public class FindTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findTest");

        collection.drop();

        // insert 10 documents
        for (int i=0;i<10;i++){
            collection.insertOne(new Document("x", 1));
        }

        System.out.println("Find ons:");
        Document first = collection.find().first();
        printJson(first);

        System.out.println("Find all with into:");
        List<Document> all = collection.find().into(new ArrayList<Document>());
        for (Document cur : all){
            printJson(cur);
        }

        System.out.println("Find all with iteration:");
        MongoCursor<Document> cursor = collection.find().iterator();
        try{
            while (cursor.hasNext()){
                Document cur = cursor.next();
                printJson(cur);
            }
        }
        finally {
            cursor.close();
        }

        System.out.println("Count:");
        long count = collection.count();
        System.out.println(count);
    }
}
