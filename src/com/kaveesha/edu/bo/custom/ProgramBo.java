package com.kaveesha.edu.bo.custom;



import com.kaveesha.edu.dto.ProgramDto;


import java.sql.SQLException;
import java.util.List;

public interface ProgramBo {
    public List<ProgramDto> loadPrograms() throws SQLException, ClassNotFoundException;
}
