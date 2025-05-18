package com.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;

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

    public void issueBook(String identifiant) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction hibernateTx = session.beginTransaction();
            // Récupérer le livre à partir de l'identifiant
            Book book = session.createQuery("FROM Book b WHERE b.identifiant = :identifiant", Book.class)
                    .setParameter("identifiant", identifiant)
                    .uniqueResult();
            if (book == null) {
                throw new RuntimeException("Livre non trouvé avec l'identifiant : " + identifiant);
            }
            com.library.Transaction transaction = new com.library.Transaction();
            transaction.setBook(book);
      
            transaction.setIssueDate(LocalDate.now());
            session.persist(transaction);
            hibernateTx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Échec de l'emprunt du livre : " + e.getMessage());
        }
    }

    public void returnBook(String identifiant, LocalDate returnDate) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction hibernateTx = session.beginTransaction();
            // Récupérer le livre à partir de l'identifiant
            Book book = session.createQuery("FROM Book b WHERE b.identifiant = :identifiant", Book.class)
                    .setParameter("identifiant", identifiant)
                    .uniqueResult();
            if (book == null) {
                throw new RuntimeException("Livre non trouvé avec l'identifiant : " + identifiant);
            }
            com.library.Transaction transaction = session.createQuery(
                    "FROM Transaction t WHERE t.book = :book AND t.returnDate IS NULL", com.library.Transaction.class)
                    .setParameter("book", book)
                    .uniqueResult();
            if (transaction != null) {
                transaction.setReturnDate(returnDate);
                session.merge(transaction);
                hibernateTx.commit();
            } else {
                throw new RuntimeException("Aucune transaction active trouvée pour ce livre.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Échec du retour du livre : " + e.getMessage());
        }
    }
    public List<com.library.Transaction> getBookHistory(Book book) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Transaction t WHERE t.book = :book", com.library.Transaction.class)
                    .setParameter("book", book)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}