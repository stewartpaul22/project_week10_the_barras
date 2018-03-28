package com.codeclan.controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.User;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;

public class LoginController {

    public LoginController(){
        this.setupEndPoints();
    }

    private void setupEndPoints() {
        get("/login", (req,res)-> {
            String status = "hidden";
            String status1 = "hidden";
            Map<String, Object> model = new HashMap<>();
            model.put("status", status);
            model.put("status1", status1);
            return new ModelAndView(model, "templates/login.vtl");
        }, new VelocityTemplateEngine());


        post("/login" , (req,res)->{
            String status = "hidden";
            String status1 = "hidden";
            String message = "";

            Map<String, Object> model = new HashMap<>();
            model.put("status", status);
            model.put("status1", status1);

            String username = (req.queryParams("username")).toLowerCase();
            req.session().attribute("username", username);

            User user = new User(username);
            List<User> userList = DBHelper.getAll(User.class);
            List<User> registeredUserList = new ArrayList<>();
            for(User userInList : userList) {
                if (username.equals(userInList.getUsername())) {
                    registeredUserList.add(userInList);
                }
            }

            if(registeredUserList.size() == 1){
                registeredUserList.clear();
                res.redirect("/");
            }else{
                status = "";
                model.put("status", status);
                message = "You need to register first";
                model.put("message", message);
                return new ModelAndView(model,"templates/login.vtl");
            }

            return null;
        }, new VelocityTemplateEngine());


        post("/login/addUser" , (req,res)->{
            String status1 = "hidden";
            String status = "hidden";
            String message1 = "";

            String newUsername = (req.queryParams("newUsername")).toLowerCase();
            req.session().attribute("newUsername", newUsername);
            User user = new User(newUsername);

            Map<String, Object> model = new HashMap<>();
            model.put("status1", status1);
            model.put("status", status);

            List<User> userList = DBHelper.getAll(User.class);
            List<User> registeredUserList = new ArrayList<>();
            for(User userInList : userList) {
                if (newUsername.equals(userInList.getUsername())) {
                    registeredUserList.add(userInList);
                }
            }

            if(registeredUserList.size() == 1){
                registeredUserList.clear();
                //User already registered
                status1 = "";
                model.put("status1", status1);
                message1 = "Username already taken";
                model.put("message1", message1);
                status = "hidden";
                model.put("status", status);
            }else{
                DBHelper.saveOrUpdate(user);
                status1 = "";
                model.put("status1", status1);
                message1 = "You can now login";
                model.put("message1", message1);
                status = "hidden";
                model.put("status", status);
            }

            return new ModelAndView(model,"templates/login.vtl");
        }, new VelocityTemplateEngine());

        get("/logout", (req, res) -> {
            req.session().removeAttribute("username");
            res.redirect("/");
            return null;
        }, new VelocityTemplateEngine());
    }

    public static String getLoggedInUserName(Request req, Response res){
        String username = req.session().attribute("username");
        if(username == null || username.isEmpty()){
            res.redirect("/login");
        }
        return username;
    }
}
