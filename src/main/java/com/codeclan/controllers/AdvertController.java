package com.codeclan.controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.db.Seeds;
import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import com.codeclan.models.User;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.text.DecimalFormat;
import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

public class AdvertController {

    public static void main(String[] args) {
        Seeds.seedData();

        staticFileLocation("/public");

        CategoryController categoryController = new CategoryController();
        UserController userController = new UserController();
        LoginController loginController = new LoginController();

        get("/", (req, res) -> {

            List<Advert> adverts = DBHelper.getAll(Advert.class);

            DecimalFormat df = new DecimalFormat("#.00");

            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("adverts", adverts);
            model.put("template", "templates/adverts/index.vtl");
            model.put("df", df);

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        get("/new", (req, res) -> {

            List<Category> categories = DBHelper.getAll(Category.class);

            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("template", "templates/adverts/create.vtl");
            model.put("categories", categories);

            return new ModelAndView(model, "templates/layout.vtl");

        },  new VelocityTemplateEngine());

        post("/", (req, res) -> {

            int categoryId = Integer.parseInt(req.queryParams("category"));

            Category category = DBHelper.find(categoryId, Category.class);

            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);

            String title = req.queryParams("title");
            String description = req.queryParams("description");
            Double askingPrice = Double.parseDouble(req.queryParams("askingPrice"));

            GregorianCalendar today = new GregorianCalendar();

            Advert advert = new Advert(title, description, askingPrice, today, category);

            DBHelper.saveOrUpdate(advert);

            User user = DBHelper.findByUsername(loggedInUser, User.class);

            DBHelper.addAdvertToUser(user, advert);

            res.redirect("/myads");

            return null;

        }, new VelocityTemplateEngine());

        get("/myads", (req, res) -> {

            String loggedInUser = LoginController.getLoggedInUserName(req, res);

            User user = DBHelper.findByUsername(loggedInUser, User.class);

            List<Advert> adverts = DBHelper.getAdvertByUser(user);

            DecimalFormat df = new DecimalFormat("#.00");

            Map<String, Object> model = new HashMap<>();
            model.put("user", loggedInUser);
            model.put("template", "templates/adverts/user_adverts.vtl");
            model.put("adverts", adverts);
            model.put("df", df);

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        ////////////////////////////////////////

        post("/myads/:id/delete", (req, res)->{
            int id = Integer.parseInt(req.params(":id"));
            Advert advertToDelete = DBHelper.find(id,Advert.class);
            DBHelper.delete(advertToDelete);
            res.redirect("/myads");
            return null;
        }, new VelocityTemplateEngine());

        get("/myads/:id/edit", (req, res) ->{
            String strId = req.params(":id");
            Integer intId = Integer.parseInt(strId);

            DecimalFormat df = new DecimalFormat("#.00");

            Advert advert = DBHelper.find(intId, Advert.class);
            List<Category> categories = DBHelper.getAll(Category.class);
            Map<String, Object> model = new HashMap();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("categories", categories);
            model.put("template", "templates/adverts/edit.vtl");
            model.put("advert", advert);
            model.put("df", df);

            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());

        post("/myads/:id", (req, res) -> {
            String strId = req.params(":id");
            Integer intId = Integer.parseInt(strId);
            Advert advert = DBHelper.find(intId, Advert.class);
            int categoryId = Integer.parseInt(req.queryParams("category"));
            Category category = DBHelper.find(categoryId,Category.class);
            String title = req.queryParams("title");
            String description = req.queryParams("description");
            double askingPrice = Double.parseDouble(req.queryParams("askingPrice"));

            advert.setTitle(title);
            advert.setDescription(description);
            advert.setAskingPrice(askingPrice);
            advert.setCategory(category);
            DBHelper.saveOrUpdate(advert);
            res.redirect("/myads");
            return null;

        }, new VelocityTemplateEngine());








    }

}
