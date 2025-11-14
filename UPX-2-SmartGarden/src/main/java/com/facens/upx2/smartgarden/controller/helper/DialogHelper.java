/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class DialogHelper{
    public static void showMessage(String message, Component screen) {
        JOptionPane.showMessageDialog(screen, message);
    }

    public static boolean showConfirm(String message, String title, Component screen) {
        int result = JOptionPane.showConfirmDialog(screen, message, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
