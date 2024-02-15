package com.kaveesha.edu.bo.custom;

import com.kaveesha.edu.dto.StudentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBo {

    public boolean saveStudent(StudentDto studentDto) throws SQLException, ClassNotFoundException;
    public boolean updateStudent(StudentDto student, boolean isActive, int selectedId) throws SQLException, ClassNotFoundException;
    public boolean deleteStudent(int id) throws SQLException, ClassNotFoundException;
    public ArrayList<StudentDto> findAllStudent(String searchText) throws SQLException, ClassNotFoundException;
}
