/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import model.Pengunjung;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class PengunjungDAO extends GenericMongoDAO<Pengunjung> {

    public PengunjungDAO() {
        super("pengunjung");
    }

    @Override
    protected Document toDocument(Pengunjung p) {
        return new Document("id", p.getId())
                .append("nama", p.getNama())
                .append("email", p.getEmail())
                .append("no_hp", p.getNoHp())
                .append("alamat", p.getAlamat())
                .append("jumlah_pengunjung", p.getJumlahPengunjung());
    }

    @Override
    protected Pengunjung fromDocument(Document doc) {
        return new Pengunjung(
                doc.getInteger("id"),
                doc.getString("nama"),
                doc.getString("email"),
                doc.getString("no_hp"),
                doc.getString("alamat"),
                doc.getInteger("jumlah_pengunjung", 1) // default 1 kalau null
        );
    }

    @Override
    public List<Pengunjung> getAll() {
        List<Pengunjung> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.ascending("id"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public int getMaxId() {
        Document doc = collection.find().sort(Sorts.descending("id")).first();
        if (doc != null && doc.containsKey("id")) {
            return doc.getInteger("id");
        }
        return 0;
    }

    public void update(Pengunjung p) {
        if (p.getId() == 0) {
            return;
        }
        Document updatedDoc = toDocument(p);
        collection.replaceOne(Filters.eq("id", p.getId()), updatedDoc);
    }

    public void delete(Pengunjung p) {
        if (p.getId() == 0) {
            return;
        }
        collection.deleteOne(Filters.eq("id", p.getId()));
    }
}
