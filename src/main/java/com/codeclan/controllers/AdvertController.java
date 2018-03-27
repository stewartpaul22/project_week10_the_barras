package com.codeclan.controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.db.Seeds;
import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import com.codeclan.models.User;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

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
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("adverts", adverts);
            model.put("template", "templates/adverts/index.vtl");

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
            //model.put("user", loggedInUser);

            String title = req.queryParams("title");
            String description = req.queryParams("description");
            Double askingPrice = Double.parseDouble(req.queryParams("askingPrice"));

            GregorianCalendar today = new GregorianCalendar();

            Advert advert = new Advert(title, description, askingPrice, today, category);

            DBHelper.saveOrUpdate(advert);

            User user = DBHelper.findByUsername(loggedInUser, User.class);

            DBHelper.addAdvertToUser(user, advert);

            res.redirect("/");

            return null;

        }, new VelocityTemplateEngine());


//        post("/engineers", (req, res) -> {
//
//            int departmentId = Integer.parseInt(req.queryParams("department"));
//
//            Department department = DBHelper.find(departmentId, Department.class);
//
//            String firstName = req.queryParams("firstName");
//            String lastName = req.queryParams("lastName");
//            int salary = Integer.parseInt(req.queryParams("salary"));
//
//            Engineer engineer = new Engineer(firstName, lastName, salary, department);
//
//            DBHelper.save(engineer);
//
//            res.redirect("/engineers");
//
//            return null;
//
//        }, new VelocityTemplateEngine());

    }





}
