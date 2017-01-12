/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ik1004labb5;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*

 */
public class IK1004Labb5 extends Application {

    /**
     * @param args the command line arguments
     */
    @Override
    public void start(Stage primaryStage) {
        //DAOHund daoHund = DAOFactory.getDAOInstance(DAOFactory.DAO_DB_TYPE.TEXTFILE); //Genom argumentet TEXTFILE så blir objektet daoHund i rätt format, tack vare getDAOInstance-metoden
        //DAOHund daoHund = DAOFactory.getDAOInstance(DAOFactory.DAO_DB_TYPE.EXCEL);
        //DAOHund daoHund = DAOFactory.getDAOInstance(DAOFactory.DAO_DB_TYPE.MYSQL);
        DAOHund daoHund = DAOFactory.getDAOInstance(DAOFactory.DAO_DB_TYPE.XML);
        Scene scene = new Scene(new DAOPane(daoHund), 500, 700); //daoHund skickar med vilken typ av datakälla vi har, den har antagit formen av DAOHundTextFile      

        primaryStage.setTitle("HUNDGÅRDEN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }

}
