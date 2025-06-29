/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.model.Filters;
import model.Reservasi;
import org.bson.Document;
import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class ReservasiDAO extends GenericMongoDAO<Reservasi> {

    public ReservasiDAO() {
        super("reservasi");
    }

    @Override
    protected Document toDocument(Reservasi r) {
        return new Document("id", r.getId())
                .append("tempat", r.getTempat())
                .append("tanggal", r.getTanggal().toString()) // LocalDate ke String
                .append("jumlah_orang", r.getJumlahOrang())
                .append("total_biaya", r.getTotalBiaya())
                .append("status_pembayaran", r.getStatusPembayaran())
                .append("metode_pembayaran", r.getMetodePembayaran());
    }

    @Override
    protected Reservasi fromDocument(Document doc) {
        return new Reservasi(
                doc.getInteger("id"),
                doc.getString("tempat"),
                LocalDate.parse(doc.getString("tanggal")),
                doc.getInteger("jumlah_orang"),
                doc.getDouble("total_biaya"),
                doc.getString("status_pembayaran"),
                doc.getString("metode_pembayaran")
        );
    }

    public void update(Reservasi r) {
        if (r.getId() == 0) return;
        collection.replaceOne(Filters.eq("id", r.getId()), toDocument(r));
    }

    public void delete(Reservasi r) {
        if (r.getId() == 0) return;
        collection.deleteOne(Filters.eq("id", r.getId()));
    }

    public int getMaxId() {
        return collection.find()
                .map(d -> d.getInteger("id", 0))
                .into(new java.util.ArrayList<>())
                .stream()
                .max(Integer::compare)
                .orElse(0);
    }
}
