package com.codeclan.controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.db.Seeds;
import com.codeclan.models.Advert;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class MainController {

    public static void main(String[] args) {

        Seeds.seedData();

        staticFileLocation("/public");

        AdvertController advertController = new AdvertController();
        CategoryController categoryController = new CategoryController();
        UserController userController = new UserController();
        LoginController loginController = new LoginController();

//        get("/", (req, res) -> {
//
//            Map<String, Object> model = new HashMap<>();
//            String loggedInUser = LoginController.getLoggedInUserName(req, res);
//            model.put("user", loggedInUser);
//            model.put("template", "templates/main.vtl");
//
//            return new ModelAndView(model, "templates/layout.vtl");
//        }, new VelocityTemplateEngine());
//

        get("/", (req, res) -> {

            List<Advert> adverts = DBHelper.getAll(Advert.class);

            //GregorianCalendar calendar = new GregorianCalendar();

            Map<String, Object> model = new HashMap<>();
            model.put("adverts", adverts);
            //model.put("calendar", calendar);
            model.put("template", "templates/main.vtl");

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());


    }

}
