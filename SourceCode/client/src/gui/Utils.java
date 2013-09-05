/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author chin
 */
public class Utils {
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
                    message,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
    }
}
