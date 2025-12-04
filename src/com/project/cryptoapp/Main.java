package com.project.cryptoapp;

import com.project.cryptoapp.ui.MainFrame;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class Main {
    public static void main(String[] args) {
        // register BouncyCastle provider
        Security.addProvider(new BouncyCastleProvider());

        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

