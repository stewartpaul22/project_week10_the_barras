package com.codeclan;

import com.codeclan.enums.CategoryType;
import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class AdvertTest {

    private Advert advert;
    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category(CategoryType.MOTOR_AND_CARS);
        advert = new Advert("Raleigh Striker Bike", "1984 Raleigh Striker for sale. Good condition. Will accept offers", 100.00, new GregorianCalendar(2018, 03, 10), category);
    }

    @Test
    public void canGetTitle() {
        assertEquals("Raleigh Striker Bike", advert.getTitle());
    }

    @Test
    public void canGetDescription() {
        assertEquals("1984 Raleigh Striker for sale. Good condition. Will accept offers", advert.getDescription());
    }

    @Test
    public void canGetAskingPrice() {
        assertEquals(100.00, advert.getAskingPrice(), 0.01);
    }

    @Test
    public void canGetStartDate() {
        assertEquals(new GregorianCalendar(2018, 03, 10), advert.getStartDate());
    }

    @Test
    public void canGetCategoryType() {
        assertEquals(CategoryType.MOTOR_AND_CARS, advert.getCategory().getCategoryType());
    }
}
