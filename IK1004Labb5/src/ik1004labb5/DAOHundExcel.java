/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author TErik
 */
public class DAOHundExcel implements DAOHund {

    @Override
    public void add(DTOHund dtoHund) {
        XSSFWorkbook workbook = getExcelWorkbook();
        XSSFSheet worksheet = workbook.getSheetAt(0);
        //Gå ner i hierarkun från workbook, till sheet row osv.
        XSSFRow row = worksheet.createRow(worksheet.getLastRowNum() + 1);
        XSSFCell id = row.createCell(0); //skapa celler för varje "instans", namn, ras osv.
        XSSFCell namn = row.createCell(1);
        XSSFCell ras = row.createCell(2);
        XSSFCell bildURL = row.createCell(3);
        //XSSFCell iHundgård = row.createCell(4);

        id.setCellValue(Integer.toString(dtoHund.getId()));
        namn.setCellValue(dtoHund.getNamn());
        ras.setCellValue(dtoHund.getRas());
        bildURL.setCellValue(dtoHund.getBildURL());
        //iHundgård.setCellValue(dtoHund.isiHundgård().get());
        //worksheet.createRow(worksheet.getLastRowNum() + 1);    FÖRBANNELSENS RAD! Visa Elin.

        saveToExcel(workbook);
    }

    @Override
    public void delete(int id) {
        XSSFWorkbook workbook = getExcelWorkbook();
        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter df = new DataFormatter();
        //Loopa igenom nollkolumnen för att försöka hitta en matchning på ID

        for (Row row : worksheet) {
            if (df.formatCellValue(row.getCell(0)).equalsIgnoreCase(Integer.toString(id))) {
                if (row.getRowNum() == worksheet.getLastRowNum()) {
                    worksheet.removeRow(row);
                    break;
                } else {
                    worksheet.shiftRows(row.getRowNum() + 1, worksheet.getLastRowNum(), -1);
                }
                break;
            }
        }
        saveToExcel(workbook);
    }

    @Override
    public void update(DTOHund dtoHund) {
        XSSFWorkbook workbook = getExcelWorkbook();
        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter df = new DataFormatter();
        
        for(Row row : worksheet){
          if (df.formatCellValue(row.getCell(0)).equalsIgnoreCase(Integer.toString(dtoHund.getId()))) {
          row.getCell(0).setCellValue(dtoHund.getId());
          row.getCell(1).setCellValue(dtoHund.getNamn());
          row.getCell(2).setCellValue(dtoHund.getRas());
          row.getCell(3).setCellValue(dtoHund.getBildURL());
          break;
          }
        }
        //loopa, hitta matchning och skriver över med hjälp av dtoHund.getNamn();
        saveToExcel(workbook);
    }

    @Override
    public List<DTOHund> getHundar() { //Läs in från inputstream, hämta workbooken, välj rätt sheet och läs in rader
        List<DTOHund> hundar = new ArrayList<>();
        XSSFWorkbook workbook = getExcelWorkbook();
        XSSFSheet worksheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = worksheet.iterator();
        DataFormatter df = new DataFormatter();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();

            //String id = row.getCell(0).getStringCellValue(); //Problem mot Excelfilen vid lägg till. Kan jag bestämma att cellen ska vara numerisk?
            String id = df.formatCellValue(row.getCell(0));
            String namn = df.formatCellValue(row.getCell(1));
            String ras = df.formatCellValue(row.getCell(2));
            String bildURL = df.formatCellValue(row.getCell(3));
            //String iHundgård = df.formatCellValue(row.getCell(4));

            DTOHund dtoHund = new DTOHund(Integer.parseInt(id), namn, ras, bildURL);
            hundar.add(dtoHund);
        }

        return hundar;
    }

    private XSSFWorkbook getExcelWorkbook() {
        FileInputStream inputstream = null;
        try {
            //Läs in workbook från fil
            inputstream = new FileInputStream(new File("hundar.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
            inputstream.close();
            return workbook;
        } catch (IOException ex) {
            Logger.getLogger(DAOHundExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private void saveToExcel(XSSFWorkbook workbook) {
        try {
            //Skriv över innehållet till filen och stäng strömmen
            FileOutputStream outputstr = new FileOutputStream(new File("hundar.xlsx"));
            workbook.write(outputstr);
            outputstr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAOHundExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DAOHundExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
