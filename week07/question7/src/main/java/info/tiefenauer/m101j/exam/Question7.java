package info.tiefenauer.m101j.exam;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Daniel on 01.05.2016.
 */
public class Question7 {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase db = client.getDatabase("photo");
        MongoCollection<Document> albumColl = db.getCollection("albums");
        MongoCollection<Document> imageColl = db.getCollection("images");

        ArrayList<Document> images = imageColl.find().into(new ArrayList<Document>());
        ArrayList<Document> sunriseImagesBefore = imageColl.find(eq("tags", "sunrises")).into(new ArrayList<Document>());

        long totalImages = imageColl.count();
        long orphanImages = 0;
        long nonOrphanImages = 0;

        for (Document image : images) {
            ArrayList<Document> exists  = albumColl.find(eq("images", image.get("_id"))).into(new ArrayList<Document>());
            if (exists.size() > 0){
                System.out.println("Image exists in " + exists.size() + " albums");
                nonOrphanImages++;
            }
            else{
                System.out.println("Orphan Image");
                orphanImages++;
                imageColl.deleteOne(image);
            }
        }

        ArrayList<Document> sunriseImages = imageColl.find(eq("tags", "sunrises")).into(new ArrayList<Document>());

        System.out.println("Total images in collection: " + totalImages);
        System.out.println("orphan images: " + orphanImages);
        System.out.println("non-orphan images: " + nonOrphanImages);
        System.out.println("Total: " + (orphanImages + nonOrphanImages));
        System.out.println("Success: " + ((orphanImages + nonOrphanImages) == totalImages));

        System.out.println("Number of images deleted: " + orphanImages);
        System.out.println("Number of images with tag 'sunrise' before: " + sunriseImagesBefore.size());
        System.out.println("Number of images with tag 'sunrise' after: " + sunriseImages.size());
    }
}
