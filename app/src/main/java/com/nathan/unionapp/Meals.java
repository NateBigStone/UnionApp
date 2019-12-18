package com.nathan.unionapp;

public class Meals {

    public String mealOne;
    public String mealTwo;
    public String mealThree;

    @Override
    public String toString() {
        return "{" +
                "\"mealOne\":\"" + mealOne +"\"," +
                "\"mealTwo\":\"" + mealTwo +"\"," +
                "\"mealThree\":\"" + mealThree +"\"" +
                "}";
    }
}