package com.kaveesha.edu.dao.custom;

import com.kaveesha.edu.dao.CurdDao;
import com.kaveesha.edu.entity.Trainer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TrainerDao extends CurdDao<Trainer, Integer> {

    public boolean updateTrainer(Trainer trainer, boolean isActive, int trainerId) throws SQLException, ClassNotFoundException;
    public ArrayList<Trainer> findAllTrainers(String searchText) throws SQLException, ClassNotFoundException;

}
