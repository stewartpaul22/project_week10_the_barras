package com.codeclan.db;

import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import com.codeclan.models.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBHelper {
    private static Transaction transaction;
    private static Session session;

    public static void saveOrUpdate(Object object) {

        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static <T> List<T> getAll(Class classType){
        session = HibernateUtil.getSessionFactory().openSession();
        List<T> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            results = cr.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static <T> void deleteAll(Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            List<T> results = cr.list();
            for (T result : results) {
                session.delete(result);
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void delete(Object object){
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void addAdvertToUser(User user, Advert advert) {
        user.addAdvert(advert);
        saveOrUpdate(user);
    }

    public static <T> T find(int id, Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(classType);
        cr.add(Restrictions.eq("id", id));
        return getUnique(cr);
    }

    public static <T> T findByUsername(String username, Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(classType);
        cr.add(Restrictions.eq("username", username));
        return getUnique(cr);
    }

    public static List<Advert> getAdvertByCategory(Category category){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Advert> adverts = null;
        Criteria cr= session.createCriteria(Advert.class);
        cr.add(Restrictions.eq("category", category));
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        adverts = getList(cr);
        return adverts;
    }

    public static List<Advert> getAdvertByUser(User user){
        session = HibernateUtil.getSessionFactory().openSession();
        List<Advert> adverts = null;
        Criteria cr= session.createCriteria(Advert.class);
        cr.add(Restrictions.eq("users", user));
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        adverts = getList(cr);
        return adverts;
    }

    public static <T> List<T> getList(Criteria cr) {
        List<T> results = null;
        try {
            transaction = session.beginTransaction();
            results = cr.list();
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static <T> T getUnique(Criteria criteria) {
        T result = null;
        try {
            transaction = session.beginTransaction();
            result = (T) criteria.uniqueResult();
            ;
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }


}