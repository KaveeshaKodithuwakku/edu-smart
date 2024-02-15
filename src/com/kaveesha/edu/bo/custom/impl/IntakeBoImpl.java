package com.kaveesha.edu.bo.custom.impl;

import com.kaveesha.edu.bo.custom.IntakeBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.IntakeDao;
import com.kaveesha.edu.dto.IntakeDto;
import com.kaveesha.edu.entity.Intake;


import java.sql.SQLException;
import java.util.ArrayList;

public class IntakeBoImpl implements IntakeBo {

    private IntakeDao intakeDao = DaoFactory.getDao(DaoFactory.DaoType.INTAKE);

    @Override
    public boolean saveIntake(IntakeDto intakeDto) throws SQLException, ClassNotFoundException {
        return intakeDao.save(new Intake(intakeDto.getIntakeId(), intakeDto.getIntakeName(),
               intakeDto.getStartDate(), intakeDto.getProgramId(), intakeDto.getProgramName()));
    }

    @Override
    public boolean updateIntake(IntakeDto intakeDto, long selectedId) throws SQLException, ClassNotFoundException {
        return intakeDao.updateIntake(new Intake(intakeDto.getIntakeId(), intakeDto.getIntakeName(),
                intakeDto.getStartDate(), intakeDto.getProgramId(), intakeDto.getProgramName()),selectedId);
    }

    @Override
    public boolean deleteIntake(long id) throws SQLException, ClassNotFoundException {
        return intakeDao.delete(id);
    }

    @Override
    public ArrayList<IntakeDto> findAllIntakes(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Intake> allIntake = intakeDao.findAllIntakes(searchText);
        ArrayList<IntakeDto> intakeDtoArrayList = new ArrayList<>();

        for (Intake intake:allIntake) {
            intakeDtoArrayList.add(new IntakeDto(intake.getIntakeId(), intake.getIntakeName(),
                    intake.getStartDate(),intake.getProgramId(),intake.getProgramName()));
        }


        return intakeDtoArrayList;
    }
}
