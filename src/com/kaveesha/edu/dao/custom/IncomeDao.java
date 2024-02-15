package com.kaveesha.edu.dao.custom;

import com.kaveesha.edu.dao.CurdDao;
import com.kaveesha.edu.entity.Income;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeDao extends CurdDao<Income, Integer> {

    public ArrayList<Income> getIncomeDetailsForChart() throws SQLException, ClassNotFoundException;

}
