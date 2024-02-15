package com.kaveesha.edu.dao;

import com.kaveesha.edu.dao.custom.impl.*;

public class DaoFactory {

    private DaoFactory daoFactory;
    private DaoFactory(){
    }

    public enum DaoType{
        STUDENT, TRAINER, USER, INCOME, INTAKE, PROGRAM
    }


    public static <T> T getDao(DaoType daoType){
        switch (daoType){
            case STUDENT:
                return (T) new StudentDaoImpl();
            case TRAINER:
                return (T) new TrainerDaoImpl();
            case USER:
                return (T) new UserDaoImpl();
            case INCOME:
                return (T) new IncomeDaoImpl();
            case INTAKE:
                return (T) new IntakeDaoImpl();
            case PROGRAM:
                return (T) new ProgramDaoImpl();
            default:
                return null;
        }
    }

}
