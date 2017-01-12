/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

/**
 *
 * @author TErik
 */
public class DAOFactory {
    
    static enum DAO_DB_TYPE{TEXTFILE, EXCEL, MYSQL, XML};
    public static DAOHund getDAOInstance(DAO_DB_TYPE daoDBType){
    DAOHund daoHund = null;
    switch(daoDBType){
        case TEXTFILE:
            daoHund = new DAOHundTextFile();
            break;
        case EXCEL:
            daoHund = new DAOHundExcel();
            break;
        case MYSQL:
            daoHund = new DAOHundMySQL();
            break;
        case XML:
            daoHund = new DAOHundXML();
            break;
    }
    return daoHund;
    }
}
