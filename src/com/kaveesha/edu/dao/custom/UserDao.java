package com.kaveesha.edu.dao.custom;

import com.kaveesha.edu.dao.CurdDao;
import com.kaveesha.edu.entity.User;

import java.sql.SQLException;

public interface UserDao extends CurdDao<User, Integer> {

    public User findUser(String email) throws SQLException, ClassNotFoundException;
}
