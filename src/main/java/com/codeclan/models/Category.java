package com.codeclan.models;

import com.codeclan.enums.CategoryType;

public class Category {

    private int id;
    private CategoryType categoryType;

    public Category() {
    }

    public Category(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}
