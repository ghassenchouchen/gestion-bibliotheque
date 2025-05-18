package com.library;

public class LibraryManagementApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            BookDAO bookDAO = new BookDAO();
            MemberDAO memberDAO = new MemberDAO();
            TransactionDAO transactionDAO = new TransactionDAO();
            LibraryController controller = new LibraryController(bookDAO, memberDAO, transactionDAO);
            new LibraryView(controller);
        });
    }
}