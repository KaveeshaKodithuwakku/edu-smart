package com.kaveesha.edu.validates;

public class SimpleTextValidation {

    public static boolean validateName(String name){
        return name.matches("^[a-zA-Z]{3,45}$");
    }
}
