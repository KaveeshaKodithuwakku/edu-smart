package com.kaveesha.edu.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {

    public static String encrypt(String rowPassword){
        return BCrypt.hashpw(rowPassword,BCrypt.gensalt(10));
    }

    public static boolean checkPassword(String rowPassword, String hashPassword){
        return BCrypt.checkpw(rowPassword, hashPassword);
    }

}
