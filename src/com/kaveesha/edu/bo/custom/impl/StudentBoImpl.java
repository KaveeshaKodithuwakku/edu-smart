package com.kaveesha.edu.bo.custom.impl;

import com.kaveesha.edu.bo.custom.StudentBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.StudentDao;
import com.kaveesha.edu.dto.StudentDto;
import com.kaveesha.edu.entity.Student;
import com.kaveesha.edu.util.GlobalVar;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBoImpl implements StudentBo {

    private StudentDao studentDao = DaoFactory.getDao(DaoFactory.DaoType.STUDENT);

    @Override
    public boolean saveStudent(StudentDto studentDto) throws SQLException, ClassNotFoundException {
      return studentDao.save(new Student(studentDto.getStudentId(), studentDto.getStudentName(),
                studentDto.getEmail(), studentDto.getDate(), studentDto.getAddress(), studentDto.isStatus(), GlobalVar.userEmail));
    }

    @Override
    public boolean updateStudent(StudentDto studentDto, boolean isActive, int selectedId) throws SQLException, ClassNotFoundException {
        return studentDao.updateStudent(new Student(studentDto.getStudentId(), studentDto.getStudentName(),
                studentDto.getEmail(), studentDto.getDate(), studentDto.getAddress(), studentDto.isStatus(), GlobalVar.userEmail), isActive, selectedId);
    }

    @Override
    public boolean deleteStudent(int id) throws SQLException, ClassNotFoundException {
        return studentDao.delete(id);
    }

    @Override
    public ArrayList<StudentDto> findAllStudent(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Student> allStudent = studentDao.findAllStudent(searchText);
        ArrayList<StudentDto> studentDtoArrayList = new ArrayList<>();

        for (Student student:allStudent) {
            studentDtoArrayList.add(new StudentDto(student.getStudentId(), student.getStudentName(), student.getEmail(),
                    student.getDate(),student.getAddress(),student.isStatus()));
        }

        
        return studentDtoArrayList;
    }
}
