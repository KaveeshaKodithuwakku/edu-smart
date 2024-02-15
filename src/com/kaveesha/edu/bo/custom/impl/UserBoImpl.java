package com.kaveesha.edu.bo.custom.impl;

import com.kaveesha.edu.bo.custom.UserBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.UserDao;
import com.kaveesha.edu.dto.UserDto;
import com.kaveesha.edu.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBoImpl implements UserBo {

    private UserDao userDao = DaoFactory.getDao(DaoFactory.DaoType.USER);

    @Override
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDao.save(new User(userDto.getFirst_name(),userDto.getLast_name(),
                userDto.getEmail(),userDto.getPassword(),userDto.isActive()));
    }

    @Override
    public User findUser(String email) throws SQLException, ClassNotFoundException {
       return userDao.findUser(email);
    }
}
