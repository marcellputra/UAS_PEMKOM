/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageUtil {
    private static ResourceBundle bundle;
    private static Locale currentLocale;
    private static final String BUNDLE_BASE_NAME = "messages";

    static {
        // Default locale, bisa diubah
        currentLocale = new Locale("id", "ID"); // Contoh: Bahasa Indonesia
        loadBundle(currentLocale);
    }

    private static void loadBundle(Locale locale) {
        // Ini sudah benar, mengacu ke folder i18n di classpath
        bundle = ResourceBundle.getBundle("i18n.messages", locale);
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (java.util.MissingResourceException e) {
            System.err.println("Missing resource key: " + key + " for locale " + currentLocale);
            return "!!" + key + "!!"; // Menandai kunci yang hilang
        }
    }
    
    public static void setCurrentLocale(Locale newLocale) {
        if (newLocale != null && !currentLocale.equals(newLocale)) {
            currentLocale = newLocale;
            loadBundle(currentLocale);
        }
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        loadBundle(currentLocale);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}
