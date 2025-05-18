package com.library;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LibraryView {
    private final JFrame frame;
    private final LibraryController controller;

    public LibraryView(LibraryController controller) {
        this.controller = controller;
        frame = new JFrame("Système de Gestion de Bibliothèque");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the frame to add top text

        // Font for labels, buttons, and the top title
        Font baseFont = new Font("SansSerif", Font.PLAIN, 16);
        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
        Font titleFont = new Font("SansSerif", Font.BOLD, 24);

        // Add "Gestion de bibliothèque" text at the top center
        JLabel titleLabel = new JLabel("Gestion de bibliothèque", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding
        frame.add(titleLabel, BorderLayout.NORTH);

        // Create a container panel for all sections with BoxLayout for vertical stacking
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(new Color(245, 245, 245));

        // insérer livre
        JPanel insertPanel = new JPanel(new GridBagLayout());
        insertPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), "Insérer", 0, 0, baseFont));
        insertPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        JTextField insertTitleField = new JTextField(20);
        JTextField insertIdentifiantField = new JTextField(20);
        JTextField insertAuthorField = new JTextField(20);
        insertTitleField.setFont(labelFont);
        insertIdentifiantField.setFont(labelFont);
        insertAuthorField.setFont(labelFont);
        JButton insertButton = new JButton("Ajouter");
        styleButton(insertButton, 120, 40);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        insertPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridy = 1;
        insertPanel.add(new JLabel("Identifiant :"), gbc);
        gbc.gridy = 2;
        insertPanel.add(new JLabel("Auteur :"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        insertPanel.add(insertTitleField, gbc);
        gbc.gridy = 1;
        insertPanel.add(insertIdentifiantField, gbc);
        gbc.gridy = 2;
        insertPanel.add(insertAuthorField, gbc);
        gbc.gridx = 2; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        insertPanel.add(insertButton, gbc);

        // supprimer
        JPanel deletePanel = new JPanel(new GridBagLayout());
        deletePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), "Supprimer", 0, 0, baseFont));
        deletePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        JTextField deleteIdentifiantField = new JTextField(20);
        deleteIdentifiantField.setFont(labelFont);
        JButton deleteButton = new JButton("Effacer");
        styleButton(deleteButton, 120, 40);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        deletePanel.add(new JLabel("Identifiant :"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        deletePanel.add(deleteIdentifiantField, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        deletePanel.add(deleteButton, gbc);

        // maj livres
        JPanel updatePanel = new JPanel(new GridBagLayout());
        updatePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), "Mettre à jour", 0, 0, baseFont));
        updatePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        JTextField updateTitleField = new JTextField(20);
        JTextField updateIdentifiantField = new JTextField(20);
        updateTitleField.setFont(labelFont);
        updateIdentifiantField.setFont(labelFont);
        JButton updateButton = new JButton("Modifier");
        styleButton(updateButton, 120, 40);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        updatePanel.add(new JLabel("Nouveau Nom :"), gbc);
        gbc.gridy = 1;
        updatePanel.add(new JLabel("Identifiant  :"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        updatePanel.add(updateTitleField, gbc);
        gbc.gridy = 1;
        updatePanel.add(updateIdentifiantField, gbc);
        gbc.gridx = 2; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        updatePanel.add(updateButton, gbc);

        // Afficher livres
        JPanel viewPanel = new JPanel(new GridBagLayout());
        viewPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), "Consulter les livres disponibles", 0, 0, baseFont));
        viewPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JButton viewButton = new JButton("Consulter");
        styleButton(viewButton, 120, 40);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        viewPanel.add(viewButton, gbc);

        // Retourner livres
        JPanel issueReturnPanel = new JPanel(new GridBagLayout());
        issueReturnPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), "Emprunter/Retourner", 0, 0, baseFont));
        issueReturnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        JTextField issueIdentifiantField = new JTextField(20);
        JTextField returnDateField = new JTextField(20);
        issueIdentifiantField.setFont(labelFont);
        returnDateField.setFont(labelFont);
        returnDateField.setToolTipText("Entrez la date au format jj/mm/aaaa (ex. 18/05/2025)");

        JButton issueButton = new JButton("Emprunter");
        JButton returnButton = new JButton("Retourner");
        styleButton(issueButton, 120, 40);
        styleButton(returnButton, 120, 40);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        issueReturnPanel.add(new JLabel("Identifiant :"), gbc);
        gbc.gridy = 1;
        issueReturnPanel.add(new JLabel("Date Retour :"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        issueReturnPanel.add(issueIdentifiantField, gbc);
        gbc.gridy = 1;
        issueReturnPanel.add(returnDateField, gbc);
        gbc.gridx = 2; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JPanel buttonSubPanel = new JPanel(new FlowLayout());
        buttonSubPanel.add(issueButton);
        buttonSubPanel.add(returnButton);
        issueReturnPanel.add(buttonSubPanel, gbc);

        // Historique 
        JPanel historyPanel = new JPanel(new GridBagLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1), "Historique", 0, 0, baseFont));
        historyPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        JButton historyButton = new JButton("Historique");
        styleButton(historyButton, 120, 40);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        historyPanel.add(historyButton, gbc);

        containerPanel.add(Box.createVerticalGlue());
        containerPanel.add(insertPanel);
        containerPanel.add(deletePanel);
        containerPanel.add(updatePanel);
        containerPanel.add(viewPanel);
        containerPanel.add(issueReturnPanel);
        containerPanel.add(historyPanel);
        containerPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(containerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Action Listeners
        insertButton.addActionListener(e -> {
            String title = insertTitleField.getText().trim();
            String identifiant = insertIdentifiantField.getText().trim();
            String author = insertAuthorField.getText().trim();
            if (!title.isEmpty() && !identifiant.isEmpty() && !author.isEmpty()) {
                try {
                    controller.addBook(title, author, identifiant);
                    JOptionPane.showMessageDialog(frame, "Livre ajouté avec succès !");
                    insertTitleField.setText("");
                    insertIdentifiantField.setText("");
                    insertAuthorField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout du livre : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            String identifiant = deleteIdentifiantField.getText().trim();
            if (!identifiant.isEmpty()) {
                try {
                    String result = controller.deleteBook(identifiant);
                    JOptionPane.showMessageDialog(frame, result);
                    deleteIdentifiantField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un identifiant.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> {
            String title = updateTitleField.getText().trim();
            String identifiant = updateIdentifiantField.getText().trim();
            if (!identifiant.isEmpty()) {
                try {
                    controller.updateBook(title, identifiant);
                    JOptionPane.showMessageDialog(frame, "Livre mis à jour avec succès !");
                    updateTitleField.setText("");
                    updateIdentifiantField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de la mise à jour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "L'identifiant est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewButton.addActionListener(e -> showViewBooksDialog());

        issueButton.addActionListener(e -> {
            String identifiant = issueIdentifiantField.getText().trim();
            if (!identifiant.isEmpty()) {
                try {
                    String result = controller.issueBook(identifiant);
                    JOptionPane.showMessageDialog(frame, "result");
                    issueIdentifiantField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'emprunt : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un identifiant.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        returnButton.addActionListener(e -> {
            String identifiant = issueIdentifiantField.getText().trim();
            String returnDate = returnDateField.getText().trim();
            if (!identifiant.isEmpty() && !returnDate.isEmpty()) {
                try {
                    String result = controller.returnBook(identifiant, returnDate);
                    JOptionPane.showMessageDialog(frame, result);
                    issueIdentifiantField.setText("");
                    returnDateField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors du retour : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un identifiant et une date de retour.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        historyButton.addActionListener(e -> showBookHistoryDialog());

        frame.setVisible(true);
    }

    private void showViewBooksDialog() {
        JDialog dialog = new JDialog(frame, "Liste des Livres", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(600, 400);

        List<Book> books = controller.getAllBooks();
        String[] bookList = (books == null || books.isEmpty())
                ? new String[]{"Aucun livre trouvé."}
                : books.stream()
                .map(b -> "ID: " + b.getId() + " - " + b.getTitle() + " (Identifiant: " + b.getIdentifiant() + ")")
                .toArray(String[]::new);
        JList<String> list = new JList<>(bookList);
        list.setBackground(new Color(245, 245, 245));
        list.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(list);

        JButton closeButton = new JButton("Fermer");
        styleButton(closeButton, 120, 40);
        closeButton.addActionListener(e -> dialog.dispose());

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showBookHistoryDialog() {
        JDialog dialog = new JDialog(frame, "Historique", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(600, 400);

        JTextField identifiantField = new JTextField(20);
        identifiantField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton submitButton = new JButton("Afficher");
        styleButton(submitButton, 120, 40);
        JTable table = new JTable(new javax.swing.table.DefaultTableModel(new String[][]{{"Entrez un identifiant"}}, new String[]{"Message"}));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Identifiant :"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1;
        inputPanel.add(identifiantField, gbc);
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        inputPanel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String identifiant = identifiantField.getText().trim();
            if (!identifiant.isEmpty()) {
                List<com.library.Transaction> history = controller.getBookHistory(identifiant);
                if (history != null && !history.isEmpty()) {
                    String[] columns = {"Nom", "ID", "Emprunt", "Retour"};
                    String[][] data = history.stream().map(t -> new String[]{
                            t.getBook().getTitle(),
                            String.valueOf(t.getBook().getId()),
                            t.getIssueDate().toString(),
                            t.getReturnDate() != null ? t.getReturnDate().toString() : "Non retourné"
                    }).toArray(String[][]::new);
                    table.setModel(new javax.swing.table.DefaultTableModel(data, columns));
                } else {
                    table.setModel(new javax.swing.table.DefaultTableModel(new String[][]{{"Aucun historique trouvé"}}, new String[]{"Message"}));
                }
            } else {
                table.setModel(new javax.swing.table.DefaultTableModel(new String[][]{{"Entrez un identifiant"}}, new String[]{"Message"}));
            }
        });

        dialog.add(inputPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        dialog.setVisible(true);
    }

    private void styleButton(JButton button, int width, int height) {
        button.setBackground(new Color(75, 0, 130));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }
}