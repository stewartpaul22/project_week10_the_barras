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
        DBHelper.saveOrUpdate(category);

        User user = new User("Paul");
        DBHelper.saveOrUpdate(user);

        Advert advert = new Advert("Raleigh Striker Bike", "1984 Raleigh Striker for sale. Good condition. Will accept offers", 100.00, new GregorianCalendar(2018, 3, 10), category);
        DBHelper.saveOrUpdate(advert);

        Advert advert2 = new Advert("Mountain Bike", "For teenagers. Good condition. Will accept offers", 25.00, new GregorianCalendar(2018, 3, 24), category);
        DBHelper.saveOrUpdate(advert2);

        DBHelper.addAdvertToUser(user, advert);

        List<Advert> adverts = DBHelper.getAll(Advert.class);

        List<Advert> advertsByCategory = DBHelper.getAdvertByCategory(category);




    }

}
