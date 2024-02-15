package com.kaveesha.edu.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
//        String text = "Hello World";
//
//        Pattern pattern = Pattern.compile("Hello");
//        Matcher matcher = pattern.matcher(text);
//
//        if(matcher.find()){
//            System.out.println("Yes");
//        }else {
//            System.out.println("No");
//        }
//
//        System.out.println("---------------------------------------------------");
//
//        if(Pattern.matches("Hello","Hello")){
//            System.out.println("Yes");
//        }else {
//            System.out.println("No");
//        }
//
//        System.out.println("---------------------------------------------------");
//
//        Pattern patter = Pattern.compile("[abch]");
//        Matcher matcher1 = patter.matcher("hello");
//        if (matcher1.find()){
//            System.out.println("YES");
//        }else {
//            System.out.println("NO");
//        }
//
//        System.out.println("----------------------------------------------------");
//
//        if (Pattern.matches("[abch]","b")){
//            System.out.println("YES");
//        }else{
//            System.out.println("NO");
//        }
//
//        // basic char (^,$)
//
//        String regex="^he";
//        String input="hello world";
//        boolean matches = Pattern.matches("", "");
//        System.out.println(matches);
//
//        //------------------------------------------------------------------
//
//        String regex1="^ho";
//
//        Pattern pattern1 = Pattern.compile(regex1);
//
//        String input1 = "hello world";
//        Matcher matcher2 = pattern1.matcher(input1);
//        if (matcher2.find()){
//            System.out.println(true);
//        }else {
//            System.out.println(false);
//        }

        //------------------------------------------------------------------------

//
//        String regex3="ld$";
//
//        Pattern pattern3 = Pattern.compile(regex3);
//
//        String input3="hello world";
//        Matcher matcher3 = pattern3.matcher(input3);
//        if (matcher3.find()){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }

        //------------------------------------------
        // Char Class

        String regex4="[aeiou]";

        Pattern pattern4 = Pattern.compile(regex4);

        String input4="hello world!";
        Matcher matcher4 = pattern4.matcher(input4);
        if (matcher4.find()){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

        //-------------------------------------------
        // Char Class

//        String regex5="[0-9]";
//
//        Pattern pattern5 = Pattern.compile(regex5);
//
//        String input5="123 hello world!";
//        Matcher matcher5 = pattern5.matcher(input5);
//        if (matcher5.find()){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }
//
//        //------------------------------------------
//        // Char Class
//
//        String regex6="[^aeiou]";
//
//        Pattern pattern6 = Pattern.compile(regex6);
//
//        String input6="hello world!";
//        Matcher matcher6 = pattern6.matcher(input6);
//        if (matcher6.find()){
//            System.out.println(true);
//        }else{
//            System.out.println(false);
//        }

        //-------------------------------------------------

        //Quantifiers

        String regex7="\\d{3}";

        Pattern pattern7 = Pattern.compile(regex7);

        String input7="12";
        Matcher matcher7 = pattern7.matcher(input7);
        if (matcher7.find()){
            System.out.println(true);
        }else{
            System.out.println(false);
        }

        //--------------------------------------------------
        String regex8="[a-zA-Z]{2,5}";

        Pattern pattern8 = Pattern.compile(regex8);

        String input8="abcdef";
        Matcher matcher8 = pattern8.matcher(input8);
        if (matcher8.find()){
            System.out.println(true);
        }else{
            System.out.println(false);
        }


    }
}
