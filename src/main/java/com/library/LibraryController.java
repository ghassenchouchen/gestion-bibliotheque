package com.library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class LibraryController {
    private final BookDAO bookDAO;
    private final MemberDAO memberDAO;
    private final TransactionDAO transactionDAO;

    public LibraryController() {
        this.bookDAO = new BookDAO();
        this.memberDAO = new MemberDAO();
        this.transactionDAO = new TransactionDAO();
    }
    public LibraryController(BookDAO bookDAO, MemberDAO memberDAO, TransactionDAO transactionDAO) {
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
        this.transactionDAO = transactionDAO;
    }

    public void addBook(String title, String author, String identifiant) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIdentifiant(identifiant);
        bookDAO.saveBook(book);
    }

    public String deleteBook(String identifiant) {
        bookDAO.deleteBook(identifiant);
        // Verify if the book still exists
        Book book = bookDAO.findBookByIdentifiant(identifiant);
        if (book == null) {
            return "Livre supprimé avec succès !";
        } else {
            return "Échec de la suppression du livre.";
        }
    }

    public void updateBook(String title, String identifiant) {
        bookDAO.updateBook(title, identifiant);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public String issueBook(String identifiant) {
     
        try {
            transactionDAO.issueBook(identifiant);
            return "Livre emprunté avec succès !";
        } catch (Exception e) {
            return "Erreur lors de l'emprunt : " + e.getMessage();
        }
    }

    public String returnBook(String identifiant, String returnDateStr) {
        try {
            // Définir le format attendu pour la date (jj/mm/aaaa)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate returnDate = LocalDate.parse(returnDateStr, formatter);
            transactionDAO.returnBook(identifiant, returnDate);
            return "Livre retourné avec succès !";
        } catch (DateTimeParseException e) {
            return "Erreur lors du retour : format de date invalide. Utilisez jj/mm/aaaa (ex. 18/05/2025).";
        } catch (Exception e) {
            return "Erreur lors du retour : " + e.getMessage();
        }
    }

    public List<com.library.Transaction> getBookHistory(String identifiant) {
        Book book = bookDAO.findBookByIdentifiant(identifiant);
        if (book == null) {
            return null;
        }
        return transactionDAO.getBookHistory(book);
    }
}