/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Ulasan;  // nanti buat model Ulasan untuk representasi data
import org.bson.Document;


/**
 *
 * @author USER
 */
public class UlasanDAO extends GenericMongoDAO<Ulasan> {

    public UlasanDAO() {
        super("ulasan");
    }

    @Override
    protected Document toDocument(Ulasan u) {
        return new Document("id", u.getId())
                .append("nama", u.getNama())
                .append("tanggal_kunjungan", u.getTanggalKunjungan().toString())
                .append("isi_ulasan", u.getIsiUlasan())
                .append("rating", u.getRating());
    }

    @Override
    protected Ulasan fromDocument(Document doc) {
        Ulasan u = new Ulasan();
        u.setId(doc.getInteger("id", 0));
        u.setNama(doc.getString("nama"));
        u.setTanggalKunjungan(LocalDate.parse(doc.getString("tanggal_kunjungan")));
        u.setIsiUlasan(doc.getString("isi_ulasan"));
        u.setRating(doc.getInteger("rating", 0));
        return u;
    }

    @Override
    public List<Ulasan> getAll() {
        List<Ulasan> list = new ArrayList<>();
        for (Document doc : collection.find().sort(Sorts.ascending("id"))) {
            list.add(fromDocument(doc));
        }
        return list;
    }

    public int getMaxId() {
        Document doc = collection.find().sort(Sorts.descending("id")).first();
        return doc != null && doc.containsKey("id") ? doc.getInteger("id") : 0;
    }

    public void update(Ulasan u) {
        collection.replaceOne(Filters.eq("id", u.getId()), toDocument(u));
    }

    public void delete(Ulasan u) {
        collection.deleteOne(Filters.eq("id", u.getId()));
    }
}

