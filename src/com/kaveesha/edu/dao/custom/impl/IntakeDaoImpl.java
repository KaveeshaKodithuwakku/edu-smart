package com.kaveesha.edu.dao.custom.impl;

import com.kaveesha.edu.dao.CurdUtil;
import com.kaveesha.edu.dao.custom.IntakeDao;
import com.kaveesha.edu.entity.Intake;
import com.kaveesha.edu.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IntakeDaoImpl implements IntakeDao {
    @Override
    public boolean save(Intake intake) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("INSERT INTO intake(intake_name,start_date,program_program_id) VALUES(?,?,?)",
                intake.getIntakeName(),java.sql.Date.valueOf(intake.getStartDate()),
                intake.getProgramId());
    }

    @Override
    public boolean update(Intake intake) {
        return false;
    }

    @Override
    public boolean delete(Long id) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute( "DELETE FROM intake WHERE intake_id = ?",id);
    }

    @Override
    public Intake find(Long aLong) {
        return null;
    }

    @Override
    public List<Intake> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean updateIntake(Intake intake, long selectedId) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("UPDATE intake SET intake_name =?, start_date =?, program_program_id =? WHERE intake_id =?",
                intake.getIntakeName(),java.sql.Date.valueOf(intake.getStartDate()), intake.getProgramId(), selectedId
        );
    }

    @Override
    public ArrayList<Intake> findAllIntakes(String searchText) throws SQLException, ClassNotFoundException {
        ResultSet rs = CurdUtil.execute("SELECT i.intake_id,i.intake_name, i.start_date, i.program_program_id , p.program_name FROM intake i INNER JOIN" +
                " program p ON i.program_program_id=p.program_id WHERE intake_name LIKE ? ", searchText);

        ArrayList<Intake> intakeList = new ArrayList<>();

        while (rs.next()){
            intakeList.add(new Intake(
                    rs.getLong(1),
                    rs.getString(2),
                    LocalDate.parse(rs.getString(3)),
                  rs.getLong(4),
                    rs.getString(5)
            ));
        }

        return intakeList;
    }
}
