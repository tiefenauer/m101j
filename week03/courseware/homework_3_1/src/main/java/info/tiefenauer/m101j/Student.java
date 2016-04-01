package info.tiefenauer.m101j;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 01.04.2016.
 */
@Entity(value = "students")
public class Student extends Document {

    @Id
    public ObjectId id;

    public String name;

    public List<Score> scores = new ArrayList<Score>();
}
