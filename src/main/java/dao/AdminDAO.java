/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.model.Filters;
import model.Admin;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author USER
 */
public class AdminDAO extends GenericMongoDAO<Admin> {

    public AdminDAO() {
        super("admin");
    }

    @Override
    protected Document toDocument(Admin admin) {
        Document doc = new Document()
                .append("username", admin.getUsername())
                .append("password", admin.getPassword());

        if (admin.getId() != 0) {
            doc.append("id", admin.getId());
        }

        return doc;
    }

    @Override
    protected Admin fromDocument(Document doc) {
        Admin admin = new Admin();
        admin.setUsername(doc.getString("username"));
        admin.setPassword(doc.getString("password"));

        if (doc.containsKey("id")) {
            admin.setId(doc.getInteger("id"));
        }

        return admin;
    }

    // Metode cari admin berdasarkan username
    public Admin findByUsername(String username) {
        Bson filter = Filters.eq("username", username);
        Document doc = collection.find(filter).first();
        if (doc != null) {
            return fromDocument(doc);
        }
        return null;
    }
}
