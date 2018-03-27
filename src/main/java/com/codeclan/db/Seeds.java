package com.codeclan.db;

import com.codeclan.enums.CategoryType;
import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import com.codeclan.models.User;

import java.util.GregorianCalendar;
import java.util.List;

public class Seeds {

    public static void seedData() {

//        DBHelper.deleteAll(Category.class);
//        DBHelper.deleteAll(User.class);
//        DBHelper.deleteAll(Advert.class);

        Category category = new Category(CategoryType.MOTOR_AND_CARS);
        Category category2 = new Category(CategoryType.FASHION);
        Category category3= new Category(CategoryType.TECHNOLOGY_AND_ELECTRONICS);
        Category category4 = new Category(CategoryType.BABY_AND_CHILD);
        Category category5 = new Category(CategoryType.HOME_AND_GARDEN);
        Category category6 = new Category(CategoryType.HEALTH_AND_BEAUTY);
        Category category7 = new Category(CategoryType.OTHER);
        DBHelper.saveOrUpdate(category);
        DBHelper.saveOrUpdate(category2);
        DBHelper.saveOrUpdate(category3);
        DBHelper.saveOrUpdate(category4);
        DBHelper.saveOrUpdate(category5);
        DBHelper.saveOrUpdate(category6);
        DBHelper.saveOrUpdate(category7);


        User user = new User("paul");
        DBHelper.saveOrUpdate(user);
        User user2 = new User("ria");
        DBHelper.saveOrUpdate(user2);

        Advert advert = new Advert("Raleigh Striker Bike", "1984 Raleigh Striker for sale. Good condition. Will accept offers", 100.00, new GregorianCalendar(2018, 3, 10), category);
        DBHelper.saveOrUpdate(advert);

        Advert advert2 = new Advert("Mountain Bike", "For teenagers. Good condition. Will accept offers", 25.00, new GregorianCalendar(2018, 3, 24), category);
        DBHelper.saveOrUpdate(advert2);

        Advert advert3 = new Advert("Mountain Bike", "For teenagers. Good condition. Will accept offers", 25.00, new GregorianCalendar(2017, 11, 24), category);
        DBHelper.saveOrUpdate(advert3);

        Advert advert4 = new Advert("Pram", "For babies. Good condition. Will accept offers", 250.00, new GregorianCalendar(2017, 10, 28), category4);
        DBHelper.saveOrUpdate(advert4);

        DBHelper.addAdvertToUser(user, advert);
        DBHelper.addAdvertToUser(user, advert2);
        DBHelper.addAdvertToUser(user2, advert3);
        DBHelper.addAdvertToUser(user2, advert4);

        List<User> userList = DBHelper.getAll(User.class);

        List<Advert> adverts = DBHelper.getAll(Advert.class);

        List<Advert> advertsByCategory = DBHelper.getAdvertByCategory(category);

        User user3 = DBHelper.findByUsername("paul", User.class);

        List<Advert> listAds = DBHelper.getAdvertByUser(user2);




    }

}
