package com.kaveesha.edu.dao.custom.impl;

import com.kaveesha.edu.dao.CurdUtil;
import com.kaveesha.edu.dao.custom.IncomeDao;
import com.kaveesha.edu.entity.Income;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeDaoImpl implements IncomeDao {
    @Override
    public boolean save(Income income) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Income income) {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Income find(Integer integer) {
        return null;
    }

    @Override
    public List<Income> findAll() {
        return null;
    }

    @Override
    public ArrayList<Income> getIncomeDetailsForChart() throws SQLException, ClassNotFoundException {

        ResultSet rs = CurdUtil.execute("SELECT date as iDate,SUM(amount) as income FROM payment WHERE is_verified = true GROUP BY date");

        ArrayList<Income> incomeList = new ArrayList<>();

        while (rs.next()){
            incomeList.add(new Income(LocalDate.parse(rs.getString("iDate")),rs.getDouble("income")));
        }

        return incomeList;
    }


}
