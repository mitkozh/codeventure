package com.mycompany.irr00_group_project.utils;

public class StringUtils {

    public static boolean isNullOrEmpty(String s){
        if(s==null || s.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNullOrWhiteSpace(String s){
        return s == null || s.trim().isEmpty();
    }
}
