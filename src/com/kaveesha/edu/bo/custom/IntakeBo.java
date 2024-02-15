package com.kaveesha.edu.bo.custom;

import com.kaveesha.edu.dto.IntakeDto;
import com.kaveesha.edu.entity.Intake;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IntakeBo {
    public boolean saveIntake(IntakeDto intakeDto) throws SQLException, ClassNotFoundException;
    public boolean updateIntake(IntakeDto intake, long selectedId) throws SQLException, ClassNotFoundException;
    public boolean deleteIntake(long id) throws SQLException, ClassNotFoundException;
    public ArrayList<IntakeDto> findAllIntakes(String searchText) throws SQLException, ClassNotFoundException;
}
