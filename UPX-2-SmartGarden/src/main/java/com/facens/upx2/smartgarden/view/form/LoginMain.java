package com.facens.upx2.smartgarden.view.form;

import javax.swing.JFrame;
import com.facens.upx2.smartgarden.view.form.LoginScreen;

public class LoginMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Urban Garden - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new LoginScreen());
        frame.setVisible(true);
    }
}