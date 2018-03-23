package com.codeclan;

import com.codeclan.enums.CategoryType;
import com.codeclan.models.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category(CategoryType.MOTOR_AND_CARS);
    }

    @Test
    public void canGetCategoryType() {
        assertEquals(CategoryType.MOTOR_AND_CARS, category.getCategoryType());
    }

    @Test
    public void canGetCategoryType__Constant() {
        assertEquals("MOTOR_AND_CARS", category.getCategoryType().toString());
    }

}
