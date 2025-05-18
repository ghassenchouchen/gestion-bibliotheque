package com.library;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

public class MemberDAO {
    private final SessionFactory sessionFactory;

    public MemberDAO() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Member.class)
                .addAnnotatedClass(com.library.Transaction.class)
                .buildSessionFactory();
    }

    public void saveMember(Member member) {
        try (Session session = sessionFactory.openSession()) {
            Transaction hibernateTx = session.beginTransaction();
            session.persist(member);
            hibernateTx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Member getMemberById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Member m WHERE m.id = :id", Member.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}