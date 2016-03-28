package info.tiefenauer.m101j;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by Daniel on 20.03.2016.
 */
public class HelloWorldSparkStyle {

    public static void main(String[] args) {
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World from Spark";
            }
        });
    }
}
