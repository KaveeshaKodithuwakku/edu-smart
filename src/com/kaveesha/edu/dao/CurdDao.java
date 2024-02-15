package com.kaveesha.edu.dao;

import com.kaveesha.edu.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CurdDao<T,ID> {
    public boolean save(T t) throws SQLException, ClassNotFoundException;
    public boolean update(T t);
    public boolean delete(ID id) throws SQLException, ClassNotFoundException;
    public T find(ID id);
    public List<T> findAll() throws SQLException, ClassNotFoundException;
}
