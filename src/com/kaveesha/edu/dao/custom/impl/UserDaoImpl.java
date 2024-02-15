package com.kaveesha.edu.dao.custom.impl;

import com.kaveesha.edu.dao.CurdUtil;
import com.kaveesha.edu.dao.custom.UserDao;
import com.kaveesha.edu.entity.User;
import com.kaveesha.edu.util.security.PasswordManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("INSERT INTO user VALUES (?,?,?,?,?)", user.getEmail(), user.getFirst_name(), user.getLast_name(),
                PasswordManager.encrypt(user.getPassword()));
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public User find(Integer integer) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findUser(String email) throws SQLException, ClassNotFoundException {

        ResultSet rs = CurdUtil.execute("SELECT email,password FROM user WHERE email =?",email);
        User user = new User();

        while (rs.next()){
            user.setEmail(rs.getString(1));
            user.setPassword(rs.getString(2));
        }

        return user;
    }
}
