package com.kaveesha.edu.dao.custom.impl;


import java.sql.SQLException;

class ProgramDaoImplTest {

    public static void main(String[] args) {
        new ProgramDaoImplTest().findAll();
    }

    private void findAll() {
        try {
            new ProgramDaoImpl().findAll();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}