/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.util.List;

/**
 *
 * @author TErik
 */
public class SyncMainClass {

    /**
     * @param args the command line arguments 
     */
    public static void main(String[] args) {

        DAOHundMySQL tempHund = new DAOHundMySQL();
        List<DTOHund> tempHundar = tempHund.getHundar();

        DAOHund tempTextFile = new DAOHundTextFile();
        List<DTOHund> tempTextFileHundar = tempTextFile.getHundar();
        for (int i = 0; i < tempTextFileHundar.size(); i++) {
            tempTextFile.delete(tempTextFileHundar.get(i).id);
        }
        
        DAOHund tempExcelFile = new DAOHundExcel();
         List<DTOHund> tempExcelFileHundar = tempExcelFile.getHundar();
        for (int i = 0; i < tempExcelFileHundar.size(); i++) {
            tempExcelFile.delete(tempExcelFileHundar.get(i).id);
        }
        
        
        DAOHund tempXMLFile = new DAOHundXML();
          List<DTOHund> tempXMLFileHundar = tempXMLFile.getHundar();
        for (int i = 0; i < tempXMLFileHundar.size(); i++) {
            tempXMLFile.delete(tempXMLFileHundar.get(i).id);
        }
        
        
        for (int i = 0; i < tempHundar.size(); i++) {
            DTOHund dtoHund = new DTOHund(tempHundar.get(i).id, tempHundar.get(i).namn, tempHundar.get(i).ras, tempHundar.get(i).bildURL);
                tempTextFile.add(dtoHund);
                tempExcelFile.add(dtoHund);
                tempXMLFile.add(dtoHund);
            
        }
    }

}
