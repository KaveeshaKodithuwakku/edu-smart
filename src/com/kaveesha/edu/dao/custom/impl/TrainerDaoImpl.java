package com.kaveesha.edu.dao.custom.impl;

import com.kaveesha.edu.dao.CurdUtil;
import com.kaveesha.edu.dao.custom.TrainerDao;
import com.kaveesha.edu.entity.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainerDaoImpl implements TrainerDao {
        @Override
        public boolean save(Trainer trainer) throws SQLException, ClassNotFoundException {
            return CurdUtil.execute("INSERT INTO trainer(trainer_name,trainer_email,nic,address,trainer_status)" +
                            " VALUES (?,?,?,?,?)", trainer.getTrainerName(),trainer.getTrainerEmail(),
                    trainer.getNic(), trainer.getAddress(), trainer.isStatus());
        }

        @Override
        public boolean update(Trainer trainer) {
            return false;
        }

        @Override
        public boolean delete(Integer id) throws SQLException, ClassNotFoundException {
            return CurdUtil.execute("DELETE FROM trainer WHERE trainer_id = ?",id);
        }

        @Override
        public Trainer find(Integer integer) {
            return null;
        }

        @Override
        public List<Trainer> findAll() {
            return null;
        }

        @Override
        public boolean updateTrainer(Trainer trainer, boolean isActive, int trainerId) throws SQLException, ClassNotFoundException {
            return CurdUtil.execute( "UPDATE trainer SET trainer_name =?, trainer_email=?, nic=?, address=?, trainer_status=? WHERE trainer_id=?",
                    trainer.getTrainerName(),trainer.getTrainerEmail(), trainer.getNic(), trainer.getAddress(), isActive, trainerId);
        }

        @Override
        public ArrayList<Trainer> findAllTrainers(String searchText) throws SQLException, ClassNotFoundException {

            ResultSet rs = CurdUtil.execute("SELECT * FROM trainer WHERE trainer_name LIKE ? OR trainer_email LIKE ?",
                    searchText, searchText);

            ArrayList<Trainer> trainerList = new ArrayList<>();

            while (rs.next()){
                trainerList.add(new Trainer(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getBoolean(6)));

            }

            return trainerList;
        }
}
