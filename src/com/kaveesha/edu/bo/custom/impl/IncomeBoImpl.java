package com.kaveesha.edu.bo.custom.impl;

import com.kaveesha.edu.bo.custom.IncomeBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.IncomeDao;
import com.kaveesha.edu.dto.IncomeDto;
import com.kaveesha.edu.entity.Income;


import java.sql.SQLException;
import java.util.ArrayList;

public class IncomeBoImpl implements IncomeBo {

    private IncomeDao incomeDao = DaoFactory.getDao(DaoFactory.DaoType.INCOME);

    @Override
    public ArrayList<IncomeDto> getIncomeDetailsForChart() throws SQLException, ClassNotFoundException {

        ArrayList<Income> incomes = incomeDao.getIncomeDetailsForChart();
        ArrayList<IncomeDto> incomeDtoArrayList = new ArrayList<>();

        for (Income income: incomes) {
            incomeDtoArrayList.add(new IncomeDto(income.getDate(),income.getAmount()));
        }

        return incomeDtoArrayList;
    }
}
