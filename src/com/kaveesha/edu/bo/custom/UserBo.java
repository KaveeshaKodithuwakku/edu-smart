package com.kaveesha.edu.bo.custom;

import com.kaveesha.edu.dto.StudentDto;
import com.kaveesha.edu.dto.UserDto;
import com.kaveesha.edu.entity.User;

import java.sql.SQLException;

public interface UserBo {
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException;
    public User findUser(String email) throws SQLException, ClassNotFoundException;
}
