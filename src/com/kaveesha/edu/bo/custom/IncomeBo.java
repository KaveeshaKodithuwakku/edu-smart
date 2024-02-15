package com.kaveesha.edu.bo.custom;

import com.kaveesha.edu.dto.IncomeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IncomeBo {
    public ArrayList<IncomeDto> getIncomeDetailsForChart() throws SQLException, ClassNotFoundException;
}
