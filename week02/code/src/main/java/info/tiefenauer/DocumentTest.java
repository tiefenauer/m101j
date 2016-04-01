package info.tiefenauer;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;

import static info.tiefenauer.Helpers.printJson;

/**
 * Created by Daniel on 29.03.2016.
 */
public class DocumentTest {

    public static void main(String[] args) {
        Document document = new Document()
                            .append("str", "MongoDB, Hello")
                            .append("int", 42)
                            .append("l", 1l)
                            .append("double", 1.1)
                            .append("b", false)
                            .append("date", new Date())
                            .append("objectID", new ObjectId())
                            .append("null", null)
                            .append("embeddedDoc", new Document("x", 0))
                            .append("list", Arrays.asList(1, 2, 3));


        String str = document.getString("str");
        int i = document.getInteger("int");
        printJson(document);

        BsonDocument bsonDocument = new BsonDocument("str", new BsonString("MongoDB, Hello"));
    }
}
