package com.kaveesha.edu.bo.custom.impl;

import com.kaveesha.edu.bo.custom.TrainerBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.TrainerDao;
import com.kaveesha.edu.dto.TrainerDto;
import com.kaveesha.edu.entity.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class TrainerBoImpl implements TrainerBo {

    private TrainerDao trainerDao = DaoFactory.getDao(DaoFactory.DaoType.TRAINER);
    @Override
    public boolean saveTrainer(TrainerDto trainerDto) throws SQLException, ClassNotFoundException {
        return trainerDao.save(new Trainer(trainerDto.getTrainerId(), trainerDto.getTrainerName(),trainerDto.getTrainerEmail(),
                trainerDto.getNic(),trainerDto.getAddress(),trainerDto.isStatus()));
    }

    @Override
    public boolean updateTrainer(TrainerDto trainerDto, boolean isActive, int trainerId) throws SQLException, ClassNotFoundException {
        return trainerDao.updateTrainer(new Trainer(trainerDto.getTrainerId(), trainerDto.getTrainerName(),trainerDto.getTrainerEmail(),
                trainerDto.getNic(),trainerDto.getAddress(),trainerDto.isStatus()), isActive, trainerId);
    }

    @Override
    public boolean deleteTrainer(int id) throws SQLException, ClassNotFoundException {
        return trainerDao.delete(id);
    }

    @Override
    public ArrayList<TrainerDto> findAllTrainers(String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<Trainer> trainers = trainerDao.findAllTrainers(searchText);
        ArrayList<TrainerDto> trainerDtos = new ArrayList<>();

        for (Trainer trainer: trainers) {
            trainerDtos.add(new TrainerDto(trainer.getTrainerId(), trainer.getTrainerName(),trainer.getTrainerEmail(),
                    trainer.getNic(),trainer.getAddress(),trainer.isStatus()));
        }

        return trainerDtos;
    }
}
