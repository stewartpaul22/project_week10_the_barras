package com.codeclan.enums;

public enum CategoryType {

    FASHION("Fashion"),
    TECHNOLOGY_AND_ELECTRONICS("Technology & Electronics"),
    BABY_AND_CHILD("Baby & Child"),
    HOME_AND_GARDEN("Home & Garden"),
    MOTOR_AND_CARS("Motor & Cars"),
    HEALTH_AND_BEAUTY("Health & Beauty"),
    OTHER("Other");

    private final String decriptionType;

    CategoryType(String decriptionType) {
        this.decriptionType = decriptionType;
    }

    public String getDecriptionType() {
        return this.decriptionType;
    }

}
