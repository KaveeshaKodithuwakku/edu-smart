package com.kaveesha.edu.bo;

import com.kaveesha.edu.bo.custom.impl.*;
import com.kaveesha.edu.dao.custom.impl.IncomeDaoImpl;

public class BoFactory {

    private BoFactory boFactory;
    private BoFactory(){
    }

    public enum BoType{
        STUDENT, TRAINER, USER, INCOME, PROGRAM, INTAKE
    }


    public static <T> T getBo(BoType boType){
        switch (boType){
            case STUDENT:
                return (T) new StudentBoImpl();
            case TRAINER:
                return (T) new TrainerBoImpl();
            case USER:
                return (T) new UserBoImpl();
            case INCOME:
                return (T) new IncomeBoImpl();
            case PROGRAM:
                return (T) new ProgramBoImpl();
            case INTAKE:
                return (T) new IntakeBoImpl();
            default:
                return null;
        }
    }

}
