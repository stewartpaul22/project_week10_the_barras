package com.codeclan;

import com.codeclan.models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User("Paul");
    }

    @Test
    public void canGetName() {
        assertEquals("Paul", user.getUsername());
    }
}
