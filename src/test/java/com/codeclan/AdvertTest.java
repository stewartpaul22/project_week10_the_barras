package com.codeclan;

import com.codeclan.enums.CategoryType;
import com.codeclan.models.Advert;
import com.codeclan.models.Category;
import com.codeclan.models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class AdvertTest {

    private Advert advert;
    private Advert advert2;
    private Category category;

    @Before
    public void setUp() throws Exception {
        category = new Category(CategoryType.MOTOR_AND_CARS);
        advert = new Advert("Raleigh Striker Bike", "1984 Raleigh Striker for sale. Good condition. Will accept offers", 100.00, new GregorianCalendar(2018, 3, 10), category);
        advert2 = new Advert("Raleigh Striker Bike", "1984 Raleigh Striker for sale. Good condition. Will accept offers", 100.00, new GregorianCalendar(2017, 12, 24), category);
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
        assertEquals(new GregorianCalendar(2018, 3, 10), advert.getStartDate());
    }

    @Test
    public void canGetMonth() {
        assertEquals(3, advert.getStartDate().get(GregorianCalendar.MONTH));
    }

    @Test
    public void canGetStringDate() {
        assertEquals("10-3-2018", advert.returnSimpleDateFormat());
    }

    @Test
    public void canDisplay12thMonth() {
        assertEquals("24-12-2017", advert2.returnSimpleDateFormat());
    }

    @Test
    public void canGetCategoryType() {
        assertEquals(CategoryType.MOTOR_AND_CARS, advert.getCategory().getCategoryType());
    }
}
