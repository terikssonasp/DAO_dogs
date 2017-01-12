/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.FileChooser;

/**
 *
 * @author TErik
 */
public class DAOHundTextFile implements DAOHund { //Alt + enter kan användas för att läsa in alla abstrakta metoder

    @Override
    public void add(DTOHund dtoHund) {
        try {

            BufferedWriter writer = null;

            writer = Files.newBufferedWriter(Paths.get("hundar.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            writer.write(dtoHund.getId() + ";" + dtoHund.getNamn() + ";" + dtoHund.getRas() + ";" + dtoHund.getBildURL());
            writer.newLine();
            writer.close();

            //Gör en writer!! 
        } catch (IOException ex) {
            Logger.getLogger(DAOHundTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int delID) {

        try {
            BufferedWriter writer = null;
            List<DTOHund> tempList = getHundar();

            for (int i = 0; i < tempList.size(); i++) {
                if (delID == tempList.get(i).getId()) {
                    tempList.remove(i);
                    break;
                };
            }

            writer = Files.newBufferedWriter(Paths.get("hundar.txt"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            for (DTOHund hund : tempList) {
                String hunddata = hund.getId() + ";" + hund.getNamn() + ";" + hund.getRas() + ";" + hund.getBildURL();
                writer.write(hunddata);
                writer.newLine();
            }
            writer.close();

        } catch (IOException ex) {
            Logger.getLogger(DAOHundTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(DTOHund dtoHund) {
        try {
            BufferedWriter writer = null;

            List<DTOHund> tempList = getHundar();

            for (int i = 0; i < tempList.size(); i++) {
                if (tempList.get(i).getId() == dtoHund.getId()) {
                    tempList.get(i).setNamn(dtoHund.getNamn());
                    tempList.get(i).setRas(dtoHund.getRas());
                    tempList.get(i).setBildURL(dtoHund.getBildURL());
                    //tempList.get(i).setiHundgård(dtoHund.isiHundgård());
                    break;
                }
            }

            writer = Files.newBufferedWriter(Paths.get("hundar.txt"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            for (DTOHund hund : tempList) {
                String hunddata = hund.getId() + ";" + hund.getNamn() + ";" + hund.getRas() + ";" + hund.getBildURL();
                writer.write(hunddata);
                writer.newLine();
            }
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(DAOHundTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<DTOHund> getHundar() {

        BufferedReader reader = null;
        try {
            List<DTOHund> hundar = new ArrayList();
            reader = getReader();
            //Loopa igenom txtfilen rad för rad, splitta upp dess data till en array, skapa sedan upp DTOParfym-objekt och lägg dem i en lista
            String rad;
            while ((rad = reader.readLine()) != null) {
                String[] array = rad.split(";");
                DTOHund dtoHund = new DTOHund(Integer.parseInt(array[0]), array[1], array[2], array[3]); //Typkonvertera om du har andra datatyper i din konstruktor
                hundar.add(dtoHund);
                //System.out.println(dtoHund.getId() + " + " + dtoHund.getNamn() + " + " + dtoHund.getRas() + " + " + dtoHund.getBildURL());
            }
            reader.close();
            return hundar;

        } catch (IOException ex) {
            Logger.getLogger(DAOHundTextFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(DAOHundTextFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public BufferedReader getReader() {
        try {
            BufferedReader reader = null;
            reader = Files.newBufferedReader(Paths.get("hundar.txt"), StandardCharsets.UTF_8);
            return reader;
        } catch (IOException ex) {
            Logger.getLogger(DAOHundTextFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public File setFile(){ //frivillig!!
     
            FileChooser fChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files","*.txt");
            fChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fChooser.showOpenDialog(null);
            //File selectedFile = new File("hundar.txt"); <Denna är OK
            
            return selectedFile;
    }
    
    

}
