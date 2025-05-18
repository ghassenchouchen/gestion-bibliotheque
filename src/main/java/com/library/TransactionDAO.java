package com.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;

public class TransactionDAO {
    private final SessionFactory sessionFactory;

    public TransactionDAO() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(com.library.Transaction.class)
                .buildSessionFactory();
    }

    public void issueBook(int bookId, int memberId) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            Transaction hibernateTx = session.beginTransaction();

            // Fetch book and member
            Book book = session.get(Book.class, bookId);
            Member member = session.get(Member.class, memberId);

            // Validate book and member existence
            if (book == null) {
                throw new Exception("Livre non trouvé avec l'ID : " + bookId);
            }
            if (member == null) {
                throw new Exception("Membre non trouvé avec l'ID : " + memberId);
            }

            // Check if the book is already issued
            String bookHql = "FROM Transaction t WHERE t.book.id = :bookId AND t.returnDate IS NULL";
            Query<com.library.Transaction> bookQuery = session.createQuery(bookHql, com.library.Transaction.class);
            bookQuery.setParameter("bookId", bookId);
            if (!bookQuery.getResultList().isEmpty()) {
                throw new Exception("Le livre est déjà emprunté.");
            }

            // Check if the member has reached the borrowing limit (max 3 books)
            String memberHql = "FROM Transaction t WHERE t.member.id = :memberId AND t.returnDate IS NULL";
            Query<com.library.Transaction> memberQuery = session.createQuery(memberHql, com.library.Transaction.class);
            memberQuery.setParameter("memberId", memberId);
            if (memberQuery.getResultList().size() >= 3) {
                throw new Exception("Le membre a atteint la limite d'emprunt (3 livres maximum).");
            }

            // Create and save the transaction
            com.library.Transaction transaction = new com.library.Transaction();
            transaction.setBook(book);
            transaction.setMember(member);
            transaction.setIssueDate(LocalDate.now());
            session.persist(transaction);

            hibernateTx.commit();
        } catch (Exception e) {
            throw e; // Re-throw the exception to be handled by the caller
        }
    }

    public void returnBook(int transactionId) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            Transaction hibernateTx = session.beginTransaction();

            com.library.Transaction transaction = session.get(com.library.Transaction.class, transactionId);
            if (transaction == null) {
                throw new Exception("Transaction non trouvée avec l'ID : " + transactionId);
            }
            if (transaction.getReturnDate() != null) {
                throw new Exception("Le livre a déjà été retourné.");
            }

            transaction.setReturnDate(LocalDate.now());
            session.merge(transaction);

            hibernateTx.commit();
        } catch (Exception e) {
            throw e;
        }
    }
}