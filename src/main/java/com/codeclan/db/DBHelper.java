package com.codeclan.db;

import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import com.codeclan.models.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.transform.Transformers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
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

    public static void saveOrUpdateAdvert(Advert advert) {

        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();

            advert.setProductImage(BlobProxy.generateProxy(getImage()));

            session.saveOrUpdate(advert);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static byte[] getImage() {
        File file =new File("/Users/paulstewart/Desktop/cube.jpg");
        if(file.exists()){
            try {
                BufferedImage bufferedImage= ImageIO.read(file);
                ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", byteOutStream);
                return byteOutStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Advert loadAdvert(Advert advert) throws SQLException {
        session = HibernateUtil.getSessionFactory().openSession();
        Advert results = null;
        try {
            transaction = session.beginTransaction();

            advert = (Advert) session.get(Advert.class, 1);
            System.out.println("Product Name: " + advert.getTitle());

            InputStream imgStream = advert.getProductImage().getBinaryStream();
            saveImage(imgStream);

            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static String saveImage(InputStream stream) {
        File file = new File("/Users/paulstewart/Desktop/cube_copy.jpg");
        String filepath;
        try(FileOutputStream outputStream = new FileOutputStream(file)) {
            BufferedImage bufferedImage = ImageIO.read(stream);
            ImageIO.write(bufferedImage, "jpg", outputStream);
            System.out.println("Image file location: " + file.getCanonicalPath());
            filepath = file.getCanonicalPath();
            return filepath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> getAll(Class classType){
        session = HibernateUtil.getSessionFactory().openSession();
        List<T> results = null;
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
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

        int userId = user.getId();

        List<Advert> adverts = null;

        String hql = "select adverts.* from adverts join advert_user on adverts.id = advert_user.advert_id join users on users.id = advert_user.user_id where users.id = :userId";
        SQLQuery query = session.createSQLQuery(hql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        query.setInteger("userId", userId);

        adverts = query.list();

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