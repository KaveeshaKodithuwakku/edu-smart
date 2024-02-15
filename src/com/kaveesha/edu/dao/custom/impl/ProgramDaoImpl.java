package com.kaveesha.edu.dao.custom.impl;

import com.kaveesha.edu.dao.CurdUtil;
import com.kaveesha.edu.dao.custom.ProgramDao;
import com.kaveesha.edu.entity.Program;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao {
    @Override
    public boolean save(Program program) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("INSERT INTO program(program_name,hours,amount,user_email,trainer_trainer_id)" +
                        " VALUES (?,?,?,?,?)",
                program.getProgramName(), program.getHours(), program.getAmount(),
               program.getUserEmail(),program.getTrainerId());
    }

    @Override
    public boolean update(Program program) {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws SQLException, ClassNotFoundException {
        return CurdUtil.execute("DELETE FROM program WHERE program_id = ?",id);
    }

    @Override
    public Program find(Integer integer) {
        return null;
    }

    @Override
    public List<Program> findAll() throws SQLException, ClassNotFoundException {
        ResultSet rs = CurdUtil.execute("SELECT p.program_id, p.program_name, p.hours, p.amount, p.user_email, p.trainer_trainer_id, \n" +
                "       GROUP_CONCAT(pc.header) as content FROM program p INNER JOIN program_content pc on p.program_id = pc.program_program_id GROUP BY p.program_id");
        List<Program> programList = new ArrayList<>();

        while (rs.next()){
            programList.add(new Program(rs.getInt(1),rs.getString(2),rs.getInt(3),
                    rs.getDouble(4),rs.getString(5),rs.getInt(6),
                    Arrays.asList(rs.getString(7).split(","))));
        }
        System.out.println(programList);
        return null;
    }
}
