package com.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;

public class BookDAO {
    private final SessionFactory sessionFactory;

    public BookDAO() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(com.library.Transaction.class)
                .buildSessionFactory();
    }

    public void saveBook(Book book) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction hibernateTx = session.beginTransaction();
            session.persist(book);
            hibernateTx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book", Book.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteBook(String identifiant) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction hibernateTx = session.beginTransaction();
            // Check if book exists before deletion
            Book book = findBookByIdentifiant(identifiant);
            if (book == null) {
                System.out.println("Book with identifiant " + identifiant + " not found.");
                return;
            }
            Query query = session.createQuery("DELETE FROM Book b WHERE b.identifiant = :identifiant");
            query.setParameter("identifiant", identifiant);
            int rowsAffected = query.executeUpdate();
            System.out.println("Rows affected by deletion: " + rowsAffected);
            hibernateTx.commit();
            // Verify deletion
            book = findBookByIdentifiant(identifiant);
            if (book == null) {
                System.out.println("Book with identifiant " + identifiant + " successfully deleted.");
            } else {
                System.out.println("Failed to delete book with identifiant " + identifiant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateBook(String title, String identifiant) {
        try (Session session = sessionFactory.openSession()) {
            org.hibernate.Transaction hibernateTx = session.beginTransaction();
            Query<Book> query = session.createQuery("UPDATE Book b SET b.title = :title WHERE b.identifiant = :identifiant", Book.class);
            query.setParameter("title", title);
            query.setParameter("identifiant", identifiant);
            query.executeUpdate();
            hibernateTx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Book findBookByIdentifiant(String identifiant) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Book b WHERE b.identifiant = :identifiant", Book.class)
                    .setParameter("identifiant", identifiant)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}