/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author USER
 */
public abstract class GenericMongoDAO<T> {
    protected final MongoCollection<Document> collection;

    public GenericMongoDAO(String collectionName) {
        this.collection = KoneksiMongo.getDatabase().getCollection(collectionName);
    }

    // Konversi dari model → Document
    protected abstract Document toDocument(T entity);

    // Konversi dari Document → model
    protected abstract T fromDocument(Document doc);

    public void insert(T entity) {
        collection.insertOne(toDocument(entity));
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                list.add(fromDocument(cursor.next()));
            }
        }
        return list;
    }

    public void deleteById(int id) {
        collection.deleteOne(new Document("id", id));
    }

    public void updateById(int id, T entity) {
        Document updated = new Document("$set", toDocument(entity));
        collection.updateOne(new Document("id", id), updated);
    }

    public T findById(int id) {
        Document doc = collection.find(new Document("id", id)).first();
        return (doc != null) ? fromDocument(doc) : null;
    }
    
}
