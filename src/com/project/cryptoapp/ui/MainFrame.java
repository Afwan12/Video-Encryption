package com.project.cryptoapp.ui;

import com.project.cryptoapp.controller.EncryptionController;
import com.project.cryptoapp.model.EncryptionResult;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {

    private final JTextField fileField;
    private final JComboBox<String> algorithmBox;
    private final JTextArea logArea;
    private final JButton encryptButton;
    private final JButton decryptButton;
    private final JButton browseButton;
    private final JButton playButton;

    // Stats labels
    private final JLabel fileSizeLabel;
    private final JLabel outputSizeLabel;
    private final JLabel timeLabel;
    private final JLabel opLabel;
    private final JLabel algoLabel;

    private final EncryptionController controller;

    public MainFrame() {
        super("Video Encryption Suite");

        controller = new EncryptionController();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(root);

        // ====== TOP: TITLE ======
        JLabel title = new JLabel("Video Encryption & Decryption Tool", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        root.add(title, BorderLayout.NORTH);

        // ====== CENTER: MAIN CONTENT (LEFT: controls, RIGHT: log) ======
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        root.add(centerPanel, BorderLayout.CENTER);

        // ---------- LEFT SIDE: controls + stats ----------
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        centerPanel.add(leftPanel, BorderLayout.WEST);

        // FILE PANEL
        JPanel filePanel = new JPanel(new BorderLayout(5, 5));
        filePanel.setBorder(BorderFactory.createTitledBorder("File"));

        JLabel fileLabel = new JLabel("Path:");
        fileField = new JTextField();
        browseButton = new JButton("Browse");

        filePanel.add(fileLabel, BorderLayout.WEST);
        filePanel.add(fileField, BorderLayout.CENTER);
        filePanel.add(browseButton, BorderLayout.EAST);

        // ALGORITHM PANEL
        JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        algoPanel.setBorder(BorderFactory.createTitledBorder("Algorithm"));
        JLabel algoTextLabel = new JLabel("Select:");
        algorithmBox = new JComboBox<>(new String[]{
                "AES-128", "AES-192", "AES-256",
                "DES", "3DES", "Blowfish",
                "IDEA", "RC5", "BEAR", "LION"
        });
        algoPanel.add(algoTextLabel);
        algoPanel.add(algorithmBox);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        playButton = new JButton("Play");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        buttonPanel.add(playButton);

        // STATS PANEL
        JPanel statsPanel = new JPanel(new GridLayout(5, 2, 5, 3));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        fileSizeLabel = new JLabel("N/A");
        outputSizeLabel = new JLabel("N/A");
        timeLabel = new JLabel("N/A");
        opLabel = new JLabel("N/A");
        algoLabel = new JLabel("N/A");

        statsPanel.add(new JLabel("Input file size:"));
        statsPanel.add(fileSizeLabel);
        statsPanel.add(new JLabel("Output file size:"));
        statsPanel.add(outputSizeLabel);
        statsPanel.add(new JLabel("Time taken:"));
        statsPanel.add(timeLabel);
        statsPanel.add(new JLabel("Operation:"));
        statsPanel.add(opLabel);
        statsPanel.add(new JLabel("Algorithm:"));
        statsPanel.add(algoLabel);

        // Add panels to left side
        leftPanel.add(filePanel);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(algoPanel);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(buttonPanel);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(statsPanel);

        // ---------- RIGHT SIDE: LOG ----------
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane logScroll = new JScrollPane(
                logArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        logScroll.setBorder(BorderFactory.createTitledBorder("Log"));

        centerPanel.add(logScroll, BorderLayout.CENTER);

        // ====== HOOK UP BUTTON ACTIONS ======
        browseButton.addActionListener(e -> onBrowse());
        encryptButton.addActionListener(e -> onEncrypt());
        decryptButton.addActionListener(e -> onDecrypt());
        playButton.addActionListener(e -> onPlay());
    }

    // ===================== ACTIONS =====================

    private void onBrowse() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            fileField.setText(path);
            log("Selected file: " + path);
            updateInputSize(path);
        }
    }

    private void onEncrypt() {
        String path = fileField.getText().trim();
        String algo = (String) algorithmBox.getSelectedItem();

        if (path.isEmpty()) {
            log("Please select a file first.");
            JOptionPane.showMessageDialog(this,
                    "Please select a file before encrypting.",
                    "No file selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateInputSize(path);
        log("Starting encryption using " + algo + "...");
        setButtonsEnabled(false);

        EncryptionResult res = controller.encrypt(path, algo);
        handleResult(res, true, algo);
    }

    private void onDecrypt() {
        String path = fileField.getText().trim();
        String algo = (String) algorithmBox.getSelectedItem();

        if (path.isEmpty()) {
            log("Please select a file first.");
            JOptionPane.showMessageDialog(this,
                    "Please select a file before decrypting.",
                    "No file selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateInputSize(path);
        log("Starting decryption using " + algo + "...");
        setButtonsEnabled(false);

        EncryptionResult res = controller.decrypt(path, algo);
        handleResult(res, false, algo);
    }

    private void onPlay() {
        String path = fileField.getText().trim();

        if (path.isEmpty()) {
            log("No file to play.");
            JOptionPane.showMessageDialog(this,
                    "No file path provided.\nEncrypt/Decrypt or Browse a file first.",
                    "No file",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        File f = new File(path);
        if (!f.exists()) {
            log("Cannot play. File does not exist: " + path);
            JOptionPane.showMessageDialog(this,
                    "File does not exist:\n" + path,
                    "File not found",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            log("Opening file in default player: " + path);
            Desktop.getDesktop().open(f);
        } catch (Exception ex) {
            log("Failed to open file: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Could not open file in default player.\n\n" +
                            "Path: " + path + "\n" +
                            "Reason: " + ex.getMessage(),
                    "Play error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================== HELPERS =====================

    private void handleResult(EncryptionResult res, boolean isEncrypt, String algo) {
        String op = isEncrypt ? "Encryption" : "Decryption";

        if (res.isSuccess()) {
            log(op + " successful.");
            log(res.getMessage());
            log("Time taken: " + res.getTimeMillis() + " ms");

            opLabel.setText(op);
            algoLabel.setText(algo);
            timeLabel.setText(res.getTimeMillis() + " ms");

            if (res.getOutputPath() != null) {
                log("Output file: " + res.getOutputPath());
                fileField.setText(res.getOutputPath());
                updateOutputSize(res.getOutputPath());
            } else {
                outputSizeLabel.setText("N/A");
            }

            JOptionPane.showMessageDialog(this,
                    op + " successful!\n\nOutput file:\n" + res.getOutputPath()
                            + "\nTime: " + res.getTimeMillis() + " ms",
                    op + " Status",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            log(op + " failed.");
            log("Reason: " + res.getMessage());
            timeLabel.setText("N/A");
            outputSizeLabel.setText("N/A");
            opLabel.setText(op);
            algoLabel.setText(algo);

            JOptionPane.showMessageDialog(this,
                    op + " failed.\n\nReason:\n" + res.getMessage(),
                    op + " Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        log("--------------------------------------------------");
        setButtonsEnabled(true);
    }

    private void updateInputSize(String path) {
        File f = new File(path);
        if (f.exists()) {
            fileSizeLabel.setText(formatSize(f.length()));
        } else {
            fileSizeLabel.setText("N/A");
        }
    }

    private void updateOutputSize(String path) {
        File f = new File(path);
        if (f.exists()) {
            outputSizeLabel.setText(formatSize(f.length()));
        } else {
            outputSizeLabel.setText("N/A");
        }
    }

    private String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        double kb = bytes / 1024.0;
        if (kb < 1024) return String.format("%.2f KB", kb);
        double mb = kb / 1024.0;
        if (mb < 1024) return String.format("%.2f MB", mb);
        double gb = mb / 1024.0;
        return String.format("%.2f GB", gb);
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void setButtonsEnabled(boolean enabled) {
        encryptButton.setEnabled(enabled);
        decryptButton.setEnabled(enabled);
        browseButton.setEnabled(enabled);
        playButton.setEnabled(enabled);
    }
}

