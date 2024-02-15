package com.kaveesha.edu.dao.custom;

import com.kaveesha.edu.dao.CurdDao;
import com.kaveesha.edu.entity.Intake;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IntakeDao extends CurdDao<Intake,Long> {
    public boolean updateIntake(Intake intake, long selectedId) throws SQLException, ClassNotFoundException;
    public ArrayList<Intake> findAllIntakes(String searchText) throws SQLException, ClassNotFoundException;

}
