package org.example.databases;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CatRepository {
    @Autowired
    SessionFactory sessions;

    public void put(Cat record) {
        try (Session session = sessions.getCurrentSession()) {
            session.beginTransaction();
            session.save(record);
            session.getTransaction().commit();
        }
    }

    public Cat get(Long id) {
        try (Session session = sessions.getCurrentSession()) {
            session.beginTransaction();
            Cat record = session.get(Cat.class, id);
            session.getTransaction().commit();
            return record;
        }
    }

    public void update(Cat record) {
        try (Session session = sessions.getCurrentSession()){
            session.beginTransaction();
            session.update(record);
            session.getTransaction().commit();
        }
    }

    public void delete(Long id) {
        try (Session session = sessions.getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(Cat.class, id));
            session.getTransaction().commit();
        }
    }

}
