package com.kaveesha.edu.dao.custom.impl;

import com.kaveesha.edu.dao.CurdUtil;
import com.kaveesha.edu.dao.custom.StudentDao;

import com.kaveesha.edu.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    @Override
    public boolean save(Student student) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("INSERT INTO student(student_name,email,dob,address,status,user_email)" +
                " VALUES (?,?,?,?,?,?)",
                student.getStudentName(), student.getEmail(), java.sql.Date.valueOf(student.getDate()),
                student.getAddress(),student.isStatus(), student.getUserEmail());
    }

    @Override
    public boolean update(Student student) {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("DELETE FROM student WHERE student_id=?",id);
    }

    @Override
    public Student find(Integer integer) {
        return null;
    }

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public boolean updateStudent(Student student, boolean isActive, int selectedId) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("UPDATE student SET student_name =?, email =?, dob =?, address =?, status=? WHERE student_id =?",
                student.getStudentName(),student.getEmail(), student.getDate(), student.getAddress(), isActive, selectedId
                );
    }

    @Override
    public ArrayList<Student> findAllStudent(String searchText) throws SQLException, ClassNotFoundException {

        ResultSet rs = CurdUtil.execute("SELECT * FROM student WHERE student_name LIKE ? OR email LIKE ?", searchText, searchText);

        ArrayList<Student> studentList = new ArrayList<>();

        while (rs.next()){
            studentList.add(new Student(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    LocalDate.parse(rs.getString(4)),
                    rs.getString(5),
                    rs.getBoolean(6),
                    rs.getString(7)
            ));
        }

        return studentList;
    }
}
