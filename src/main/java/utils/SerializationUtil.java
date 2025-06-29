/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.*;
import model.Ulasan;

/**
 *
 * @author USER
 */
public class SerializationUtil {

    public static void serializeUlasan(Ulasan ulasan, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(ulasan);
        }
    }

    public static Ulasan deserializeUlasan(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Ulasan) ois.readObject();
        }
    }
}
