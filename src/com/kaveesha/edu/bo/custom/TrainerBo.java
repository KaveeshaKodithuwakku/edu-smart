package com.kaveesha.edu.bo.custom;

import com.kaveesha.edu.dto.StudentDto;
import com.kaveesha.edu.dto.TrainerDto;
import com.kaveesha.edu.entity.Trainer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TrainerBo {
    public boolean saveTrainer(TrainerDto trainerDto) throws SQLException, ClassNotFoundException;
    public boolean updateTrainer(TrainerDto trainerDto, boolean isActive, int trainerId) throws SQLException, ClassNotFoundException;
    public boolean deleteTrainer(int id) throws SQLException, ClassNotFoundException;
    public ArrayList<TrainerDto> findAllTrainers(String searchText) throws SQLException, ClassNotFoundException;

}
