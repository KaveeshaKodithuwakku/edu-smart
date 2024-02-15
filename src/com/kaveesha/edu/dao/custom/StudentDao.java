package com.kaveesha.edu.dao.custom;

import com.kaveesha.edu.dao.CurdDao;
import com.kaveesha.edu.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentDao extends CurdDao<Student,Integer> {
    public boolean updateStudent(Student student, boolean isActive, int selectedId) throws SQLException, ClassNotFoundException;
    public ArrayList<Student> findAllStudent(String searchText) throws SQLException, ClassNotFoundException;

}
