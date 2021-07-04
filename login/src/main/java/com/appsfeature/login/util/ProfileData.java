package com.appsfeature.login.util;

import java.util.ArrayList;
import java.util.List;

public class ProfileData {
    public static List<String> getGenderList() {
        List<String> list = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        list.add("Trans-Gender");
        return list;
    }

    public static List<String> getBloodGroupList() {
        List<String> list = new ArrayList<>();
        list.add("Not Known");
        list.add("A-");
        list.add("A+");
        list.add("B-");
        list.add("B+");
        list.add("AB-");
        list.add("AB+");
        list.add("O-");
        list.add("O+");
        return list;
    }

    public static List<String> getRelationList() {
        List<String> list = new ArrayList<>();
        list.add("Self");
        list.add("Spouse");
        list.add("Children");
        list.add("Father");
        list.add("Mother");
        list.add("Others");
        return list;
    }
}
