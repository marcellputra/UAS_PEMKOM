/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 *
 * @author USER
 */
public class KoneksiMongo {

    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "tempat_wisata"; // nama database kamu

    private static MongoClient mongoClient = null;

    public static MongoClient getClient() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
        }
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        return getClient().getDatabase(DB_NAME);
    }
}
