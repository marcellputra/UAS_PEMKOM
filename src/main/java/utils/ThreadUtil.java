/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import javax.swing.SwingUtilities;

/**
 *
 * @author USER
 */
public class ThreadUtil {

    public static void runAsync(Runnable task) {
        new Thread(task).start();
    }
}
