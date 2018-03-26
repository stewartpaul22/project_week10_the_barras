package com.codeclan.controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Advert;
import org.apache.commons.collections.map.HashedMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import com.codeclan.models.Category;

import java.util.List;
import java.util.Map;
import static spark.Spark.get;

public class CategoryController {

    public CategoryController(){
        this.setupEndpoints();
    }

    private void setupEndpoints() {

        get("/bycategory", (req, res) -> {

            //queryParams
            String categoryId = req.queryParams("category");
            List<Category> categories = DBHelper.getAll(Category.class);

            Map<String, Object> model = new HashedMap();
            model.put("template", "templates/categories/index.vtl");
            model.put("categories", categories);

            if(categoryId != null){
                int intCategoryId = Integer.parseInt(req.queryParams("category"));
                Category category = DBHelper.find(intCategoryId, Category.class);
                List<Advert> adverts = DBHelper.getAdvertByCategory(category);
                model.put("adverts", adverts);
                model.put("selected_category", intCategoryId);
            }

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}
