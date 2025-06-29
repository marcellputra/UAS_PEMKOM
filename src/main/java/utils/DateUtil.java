/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author USER
 */
public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String format(LocalDate date) {
        return date.format(formatter);
    }

    public static LocalDate parse(String dateStr) {
        return LocalDate.parse(dateStr, formatter);
    }
}
